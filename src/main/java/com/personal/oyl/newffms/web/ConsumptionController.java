package com.personal.oyl.newffms.web;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.personal.oyl.newffms.service.UserProfileService;


@Controller
@RequestMapping("/consumption")
public class ConsumptionController {
    
    @Autowired
    private UserProfileService userProfileService;
    
//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        dateFormat.setLenient(false);
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
//    }
    
    
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
        
        return "consumption-add";
    }
    
    
    @RequestMapping("/saveAdd")
    public String saveAdd(@ModelAttribute("cpnForm") ConsumptionForm form, Model model) {
        
        form.getConsumption().calculateCpnTime();
        
        model.addAttribute("cpnTypes", ConsumptionType.toMapValue());
        
        return "consumption-add";
    }
    
}
