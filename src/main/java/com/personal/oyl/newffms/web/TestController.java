package com.personal.oyl.newffms.web;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.personal.oyl.newffms.constants.Gender;
import com.personal.oyl.newffms.dao.UserProfileDao;
import com.personal.oyl.newffms.pojo.UserProfile;

@Controller
@RequestMapping("/test")
public class TestController {
    
    @Autowired
    private UserProfileDao dao;
    
    @RequestMapping("/visit")
    public String visit(ModelMap model) {
        
        model.addAttribute("message", "This is the message!");
        
        return "rlt";
    }
    
    
    @RequestMapping("/visitNew")
    public String visitNew(ModelMap model) {
        
//        UserProfile user = new UserProfile();
//        user.setUserOid(BigDecimal.ONE);
//        user.setUserName("欧阳亮亮");
//        user.setUserAlias("OYL");
//        user.setGender(Gender.Male);
//        user.setPhone("18652022500");
//        user.setEmail("OuYangLiang@Gmail.com");
//        user.setLoginId("oyl822");
//        user.setLoginPwd("password");
//        user.setUserTypeOid(BigDecimal.ONE);
//        
//        dao.insert(user);
        
        
        
        model.addAttribute("message", dao.select(new UserProfile()).get(0).getUserName());
        
        return "rlt";
    }
    
}
