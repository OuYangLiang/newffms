package com.personal.oyl.newffms.web;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.personal.oyl.newffms.pojo.Category;
import com.personal.oyl.newffms.pojo.JqGridJsonRlt;
import com.personal.oyl.newffms.service.CategoryService;

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    
    @RequestMapping("/ajaxGetAllCategories")
    @ResponseBody
    public JqGridJsonRlt<Category> alaxGetAllAccounts() {
        
        JqGridJsonRlt<Category> rlt = new JqGridJsonRlt<Category>();
        
        try {
            List<Category> list =  categoryService.select(null);
            
            rlt.setRows(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return rlt;
    }
}
