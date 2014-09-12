package com.personal.oyl.newffms.web;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.personal.oyl.newffms.constants.ConsumptionType;
import com.personal.oyl.newffms.pojo.Account;
import com.personal.oyl.newffms.pojo.Consumption;
import com.personal.oyl.newffms.pojo.ConsumptionForm;
import com.personal.oyl.newffms.pojo.ConsumptionItem;
import com.personal.oyl.newffms.pojo.UserProfile;
import com.personal.oyl.newffms.pojo.validator.ConsumptionFormValidator;
import com.personal.oyl.newffms.service.UserProfileService;


@Controller
@RequestMapping("/consumption")
public class ConsumptionController {
    
    @Autowired
    private UserProfileService userProfileService;
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new ConsumptionFormValidator());
    }
    
    @RequestMapping("/initAdd")
    public String initAdd(Model model) throws SQLException {
        
        List<UserProfile> users = userProfileService.select(null);
        
        ConsumptionForm form = new ConsumptionForm();
        form.setConsumption(new Consumption());
        form.getConsumption().setCpnTimeSlider(1);
        form.setCpnItems(new ArrayList<ConsumptionItem>());
        form.setAccounts(new ArrayList<Account>());
        form.getCpnItems().add(new ConsumptionItem());
        form.getAccounts().add(new Account());
        
        model.addAttribute("cpnForm", form);
        model.addAttribute("cpnTypes", ConsumptionType.toMapValue());
        model.addAttribute("users", users);
        
        return "consumption/add";
    }
    
    
    @RequestMapping("/saveAdd")
    public String saveAdd(@Valid @ModelAttribute("cpnForm") ConsumptionForm form, BindingResult result, Model model) throws SQLException {
        
        if (result.hasErrors()) {
            List<UserProfile> users = userProfileService.select(null);
            
            model.addAttribute("cpnTypes", ConsumptionType.toMapValue());
            model.addAttribute("users", users);
            model.addAttribute("validation", false);
            //model.addAttribute("errorMsg", result.getAllErrors());
            
            return "consumption/add";
        }
        
        form.getConsumption().calculateCpnTime();
        
        model.addAttribute("cpnTypes", ConsumptionType.toMapValue());
        
        return "consumption/add";
    }
    
}
