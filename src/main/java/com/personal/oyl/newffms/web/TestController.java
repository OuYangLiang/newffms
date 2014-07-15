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
    
    
    @RequestMapping("/visitNew")
    public String visitNew(ModelMap model) {
        
        UserProfile user = new UserProfile();
        user.setUserOid(BigDecimal.ONE);
        user.setUserName("欧阳亮亮");
        user.setUserAlias("OYL");
        user.setGender(Gender.Male);
        user.setPhone("18652022500");
        user.setEmail("OuYangLiang@Gmail.com");
        user.setLoginId("oyl822");
        user.setLoginPwd("password");
        user.setUserTypeOid(BigDecimal.ONE);
        
        BaseObject baseObject = new BaseObject();
        baseObject.setCreateBy("System");
        baseObject.setCreateTime(new Date());
        baseObject.setSeqNo(1);
        
        user.setBaseObject(baseObject);
        
        dao.insert(user);
        
        user = dao.select(new UserProfile()).get(0);
        user.setUserName("猪");
        dao.updateByKey(user);
        
        model.addAttribute("message", user.getUserName());
        
        return "rlt";
    }
    
    
    @RequestMapping("/viewUser")
    @ResponseBody
    public UserProfile viewUser() {
        return dao.select(new UserProfile()).get(0);
    }
    
}
