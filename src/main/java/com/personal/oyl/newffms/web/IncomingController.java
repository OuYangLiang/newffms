package com.personal.oyl.newffms.web;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.personal.oyl.newffms.pojo.Incoming;
import com.personal.oyl.newffms.pojo.JqGridJsonRlt;

@Controller
@RequestMapping("/incoming")
public class IncomingController extends BaseController{
    private static final String SESSION_KEY_SEARCH_PARAM_INCOMING = "SESSION_KEY_SEARCH_PARAM_INCOMING";
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        
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
}
