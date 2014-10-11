package com.personal.oyl.newffms.web;

import java.sql.SQLException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.personal.oyl.newffms.constants.IncomingType;
import com.personal.oyl.newffms.pojo.BaseObject;
import com.personal.oyl.newffms.pojo.Incoming;
import com.personal.oyl.newffms.pojo.JqGridJsonRlt;
import com.personal.oyl.newffms.pojo.validator.IncomingValidator;
import com.personal.oyl.newffms.util.SessionUtil;

@Controller
@RequestMapping("/incoming")
public class IncomingController extends BaseController{
    private static final String SESSION_KEY_SEARCH_PARAM_INCOMING = "SESSION_KEY_SEARCH_PARAM_INCOMING";
    
    @Autowired
    private IncomingValidator incomingValidator;
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(incomingValidator);
    }
    
    @RequestMapping("summary")
    public String summary(HttpServletRequest request, HttpSession session) throws SQLException {
        this.clearSearchParameter(request, session, SESSION_KEY_SEARCH_PARAM_INCOMING);
        
        //初始化查询条件。
        
        //设置默认查询条件值，并放入session中
        
        Incoming searchParam = (Incoming) session.getAttribute(SESSION_KEY_SEARCH_PARAM_INCOMING);
        
        if (null == searchParam) {
            searchParam = new Incoming();
            
            searchParam.setRequestPage(1);
            searchParam.setSizePerPage(10);
            searchParam.setSortField("INCOMING_DATE");
            searchParam.setSortDir("desc");
            
            session.setAttribute(SESSION_KEY_SEARCH_PARAM_INCOMING, searchParam);
        }

        return "incoming/summary";
    }
    
    @RequestMapping("search")
    @ResponseBody
    public String search(HttpSession session) {
        //从页面接受查询参数，并放入session中。
        Incoming searchParam = new Incoming();
        
        session.setAttribute(SESSION_KEY_SEARCH_PARAM_INCOMING, searchParam);
        
        return "OK";
    }
    
    @RequestMapping("/listOfSummary")
    @ResponseBody
    public JqGridJsonRlt<Incoming> listOfSummary(@RequestParam(value = "requestPage", required = true) int requestPage,
            @RequestParam(value = "sizePerPage", required = true) int sizePerPage,
            @RequestParam(value = "sortField", required = true) String sortField,
            @RequestParam(value = "sortDir", required = true) String sortDir, HttpSession session) throws SQLException {
        
        //从session中取出查询对象并查询

        Incoming searchParam = (Incoming) session.getAttribute(SESSION_KEY_SEARCH_PARAM_INCOMING);
        
        searchParam.setStart( (requestPage - 1) * sizePerPage );
        searchParam.setSizePerPage(sizePerPage);
        searchParam.setRequestPage(requestPage);
        
        if (sortField != null && !sortField.trim().isEmpty()) {
            searchParam.setSortField(sortField);
            searchParam.setSortDir(sortDir);
        }
        
        session.setAttribute(SESSION_KEY_SEARCH_PARAM_INCOMING, searchParam);
        
        return this.initPaging(incomingService, searchParam);
    }
    
    @RequestMapping("/initAdd")
    public String initAdd(Model model, HttpSession session) throws SQLException {
        
        Incoming form = null;
        
        if (null != session.getAttribute("incomingForm")) {
            form = (Incoming) session.getAttribute("incomingForm");
        }
        else {
            form = new Incoming();
        }
        
        model.addAttribute("incomingForm", form);
        model.addAttribute("incomingTypes", IncomingType.toMapValue());
        model.addAttribute("users", userProfileService.selectAllUsers());
        
        return "incoming/add";
    }
    
    
    @RequestMapping("/confirmAdd")
    public String confirmAdd(@Valid @ModelAttribute("incomingForm") Incoming form, BindingResult result, Model model, HttpSession session) throws SQLException {
        if (result.hasErrors()) {
            model.addAttribute("incomingTypes", IncomingType.toMapValue());
            model.addAttribute("users", userProfileService.selectAllUsers());
            model.addAttribute("validation", false);
            
            return "incoming/add";
        }
        
        form.setOwner(userProfileService.selectByKey(form.getOwnerOid()).getUserName());
        
        session.setAttribute("incomingForm", form);
        
        return "incoming/confirmAdd";
    }
    
    
    @RequestMapping("/saveAdd")
    public String saveAdd(Model model, HttpSession session) throws SQLException {
        Incoming form = (Incoming) session.getAttribute("incomingForm");
        
        form.setConfirmed(false);
        BaseObject base = new BaseObject();
        base.setCreateTime(new Date());
        base.setCreateBy(SessionUtil.getInstance().getLoginUser(session).getUserName());
        
        form.setBaseObject(base);
        
        transactionService.createIncoming(form);
        
        session.removeAttribute("incomingForm");
        
        return "incoming/summary";
    }
}
