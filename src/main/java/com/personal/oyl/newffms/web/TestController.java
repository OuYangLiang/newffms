package com.personal.oyl.newffms.web;


import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.personal.oyl.newffms.constants.Gender;
import com.personal.oyl.newffms.dao.UserProfileDao;
import com.personal.oyl.newffms.pojo.BaseObject;
import com.personal.oyl.newffms.pojo.UserProfile;

@Controller
@RequestMapping("/test")
public class TestController {
    private static final Logger log = LoggerFactory.getLogger(TestController.class);
    
    @Autowired
    private UserProfileDao dao;
    
    @RequestMapping("/visit")
    public String visit(ModelMap model) {
        
        log.info("logged message");
        model.addAttribute("message", "This is the message!");
        
        return "rlt";
    }
    
    
    @RequestMapping("/viewUser")
    @ResponseBody
    public UserProfile viewUser() {
        return dao.select(new UserProfile()).get(0);
    }
    
}
