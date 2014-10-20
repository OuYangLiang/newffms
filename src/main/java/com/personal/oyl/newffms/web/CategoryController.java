package com.personal.oyl.newffms.web;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

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

import com.personal.oyl.newffms.pojo.BaseObject;
import com.personal.oyl.newffms.pojo.Category;
import com.personal.oyl.newffms.pojo.JqGridJsonRlt;
import com.personal.oyl.newffms.pojo.validator.CategoryValidator;
import com.personal.oyl.newffms.service.CategoryService;
import com.personal.oyl.newffms.util.SessionUtil;

@Controller
@RequestMapping("/category")
public class CategoryController extends BaseController{
    
	@Autowired
    private CategoryService categoryService;
    
    @Autowired 
    private CategoryValidator categoryValidator;
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(categoryValidator);
    }
    
    @RequestMapping("summary")
    public String summary() throws SQLException {
        return "category/summary";
    }
    
    @RequestMapping("/initAdd")
    public String initAdd(Model model, HttpSession session) throws SQLException {
        
        Category form = null;
        
        if (null != session.getAttribute("catForm")) {
            form = (Category) session.getAttribute("catForm");
        }
        else {
            form = new Category();
        }
        
        model.addAttribute("catForm", form);
        
        return "category/add";
    }
    
    
    @RequestMapping("/confirmAdd")
    public String confirmAdd(@Valid @ModelAttribute("catForm") Category form, BindingResult result, Model model, HttpSession session) throws SQLException {
        if (result.hasErrors()) {
            model.addAttribute("validation", false);
            
            return "category/add";
        }
        
        if (null != form.getParentOid()) {
        	form.setParentCategoryFullDesc(categoryService.selectFullDescByKey(form.getParentOid()));
        }
        
        session.setAttribute("catForm", form);
        
        return "category/confirmAdd";
    }
    
    
    @RequestMapping("/saveAdd")
    public String saveAdd(Model model, HttpSession session) throws SQLException {
    	Category form = (Category) session.getAttribute("catForm");
        
        BaseObject base = new BaseObject();
        base.setCreateTime(new Date());
        base.setCreateBy(SessionUtil.getInstance().getLoginUser(session).getUserName());
        
        form.setBaseObject(base);
        
        transactionService.createCategory(form);
        
        session.removeAttribute("catForm");
        
        return "category/summary";
    }
    
    @RequestMapping("/view")
    public String view(@RequestParam("categoryOid") BigDecimal categoryOid, Model model) throws SQLException {
    	
    	Category form = categoryService.selectByKey(categoryOid);
    	form.setCategoryDesc(categoryService.selectFullDescByKey(categoryOid));
        
        model.addAttribute("catForm", form);
        model.addAttribute("removable", !categoryService.isCategoryUsedByIncoming(categoryOid));
        
        return "category/view";
    }
    
    @RequestMapping("/initEdit")
    public String initEdit(@RequestParam(value="categoryOid", required=false) BigDecimal categoryOid, Model model, HttpSession session) throws SQLException {
        
    	Category form = null;
        
        if (null != session.getAttribute("catForm")) {
            form = (Category) session.getAttribute("catForm");
        }
        else {
            form = categoryService.selectByKey(categoryOid);
            if (null != form.getParentOid()) {
            	form.setParentCategoryFullDesc(categoryService.selectFullDescByKey(form.getParentOid()));
            }
        }
        
        model.addAttribute("catForm", form);
        return "category/edit";
    }
    
    @RequestMapping("/confirmEdit")
    public String confirmEdit(@Valid @ModelAttribute("catForm") Category form, BindingResult result, Model model, HttpSession session) throws SQLException {
        if (result.hasErrors()) {
            model.addAttribute("validation", false);
            return "category/edit";
        }
        
        if (null != form.getParentOid()) {
        	form.setParentCategoryFullDesc(categoryService.selectFullDescByKey(form.getParentOid()));
        }
        
        session.setAttribute("catForm", form);
        
        return "category/confirmEdit";
    }
    
    @RequestMapping("/saveEdit")
    public String saveEdit(Model model, HttpSession session) throws SQLException {
    	Category form = (Category) session.getAttribute("catForm");
        
    	Category oldObj = categoryService.selectByKey(form.getCategoryOid());
        form.setBaseObject(new BaseObject());
        form.getBaseObject().setSeqNo(oldObj.getBaseObject().getSeqNo());
        form.getBaseObject().setUpdateBy(SessionUtil.getInstance().getLoginUser(session).getUserName());
        form.getBaseObject().setUpdateTime(new Date());
        
        if (!oldObj.getIsLeaf()) {
        	form.setMonthlyBudget(null);
        }
        form.setCategoryLevel(null);
        form.setIsLeaf(null);
        
        transactionService.updateCategory(form);
        
        session.removeAttribute("catForm");
        
        return "category/summary";
    }
    
    @RequestMapping("/delete")
    public String delete(@RequestParam("categoryOid") BigDecimal categoryOid, Model model, HttpSession session) throws SQLException {
        transactionService.deleteCategory(categoryOid, SessionUtil.getInstance().getLoginUser(session).getUserName());
        
        return "redirect:/category/summary";
    }
    
    @RequestMapping("/ajaxGetAllCategories")
    @ResponseBody
    public JqGridJsonRlt<Category> alaxGetAllAccounts() {
        
        JqGridJsonRlt<Category> rlt = new JqGridJsonRlt<Category>();
        
        try {
            List<Category> list =  categoryService.selectAllCategories();
            
            rlt.setRows(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return rlt;
    }
}
