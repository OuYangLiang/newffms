package com.personal.oyl.newffms.web;

import java.sql.SQLException;
import java.util.Date;

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

import com.personal.oyl.newffms.constants.Constants;
import com.personal.oyl.newffms.constants.Gender;
import com.personal.oyl.newffms.pojo.BaseObject;
import com.personal.oyl.newffms.pojo.UserProfile;
import com.personal.oyl.newffms.pojo.key.UserProfileKey;
import com.personal.oyl.newffms.pojo.validator.ProfileValidator;
import com.personal.oyl.newffms.security.PwdEncoder;
import com.personal.oyl.newffms.util.SessionUtil;

@Controller
@RequestMapping("/profile")
public class MyProfileController extends BaseController {
	
	@Autowired
	private PwdEncoder pwdEncoder;
	@Autowired
	private ProfileValidator profileValidator;
	
	@InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(profileValidator);
    }
	
	@RequestMapping("/initEdit")
    public String initEdit(@RequestParam(value = "back", required = false) Boolean back, Model model, HttpSession session) throws SQLException {
		
		
		UserProfile form = null;
        
        if (null != back && back && null != session.getAttribute("userForm")) {
            form = (UserProfile) session.getAttribute("userForm");
        }
        else {
            form = (UserProfile) session.getAttribute(Constants.SESSION_USER_KEY);
        }
        
        model.addAttribute("userForm", form);
        model.addAttribute("genders", Gender.toMapValue());
        
        return "profile/edit";
    }
    
    @RequestMapping("/confirmEdit")
    public String confirmEdit(@Valid @ModelAttribute("userForm") UserProfile form, BindingResult result, Model model, HttpSession session) throws SQLException {
        if (result.hasErrors()) {
        	model.addAttribute("genders", Gender.toMapValue());
            model.addAttribute("validation", false);
            
            return "profile/edit";
        }
        
        session.setAttribute("userForm", form);
        
        return "profile/confirmEdit";
    }
    
    @RequestMapping("/saveEdit")
    public String saveEdit(Model model, HttpSession session) throws SQLException {
    	UserProfile form = (UserProfile) session.getAttribute("userForm");
    	UserProfile oldObj = (UserProfile) session.getAttribute(Constants.SESSION_USER_KEY);
        
        UserProfile newObj = new UserProfile();
        newObj.setBaseObject(new BaseObject());
        newObj.getBaseObject().setSeqNo(oldObj.getBaseObject().getSeqNo());
        newObj.getBaseObject().setUpdateBy(SessionUtil.getInstance().getLoginUser(session).getUserName());
        newObj.getBaseObject().setUpdateTime(new Date());
        
        newObj.setUserOid(oldObj.getUserOid());
        newObj.setUserName(form.getUserName());
        newObj.setGender(form.getGender());
        newObj.setPhone(form.getPhone());
        newObj.setEmail(form.getEmail());
        newObj.setLoginId(form.getLoginId());
        
        if (null != form.getChangePwd() && form.getChangePwd()) {
        	newObj.setLoginPwd(pwdEncoder.encode(form.getLoginPwdNew()));
        }
        
        transactionService.updateMyProfile(newObj);
        
        session.removeAttribute("userForm");
        session.removeAttribute(Constants.SESSION_USER_KEY);
        
        oldObj = userProfileService.selectByKey(new UserProfileKey(oldObj.getUserOid()));
        session.setAttribute(Constants.SESSION_USER_KEY, oldObj);
        
        return "redirect:/profile/initEdit";
    }
}
