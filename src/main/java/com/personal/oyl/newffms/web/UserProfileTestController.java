package com.personal.oyl.newffms.web;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.personal.oyl.newffms.dao.UserProfileDao;
import com.personal.oyl.newffms.pojo.BaseObject;
import com.personal.oyl.newffms.pojo.UserProfile;

@Controller
@RequestMapping("/user")
public class UserProfileTestController {
    
    private static final Logger log = LoggerFactory.getLogger(UserProfileTestController.class);
    
    @Autowired
    private UserProfileDao dao;
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {

        binder.addValidators(new Validator(){

            public boolean supports(Class<?> clazz) {
                return UserProfile.class.equals(clazz);
            }

            public void validate(Object target, Errors errors) {
                
            }
            
        });
    }
    
    @RequestMapping("/add")
    public String initAdd(Model model) {
        log.info("Shit");
        model.addAttribute("user", new UserProfile());
        return "user-profile-add";
    }
    
    @RequestMapping("/saveAdd")
    public String saveAdd(@Valid @ModelAttribute("user") UserProfile user, BindingResult result) throws Exception {
        if (null != user) {
            user.trimAllString();
            user.setAllEmptyStringToNull();
        }
        
        if (result.hasErrors())
            return "user-profile-add";
        
        BaseObject base = new BaseObject();
        base.setCreateTime(new Date());
        base.setCreateBy("OYL");
        base.setSeqNo(1);
        user.setBaseObject(base);
        
        user.setUserTypeOid(BigDecimal.ONE);
        
        dao.insert(user);
        
        return "rlt";
    }
}
