package com.personal.oyl.newffms.web;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.SessionAttributes;

import com.personal.oyl.newffms.constants.ConsumptionType;
import com.personal.oyl.newffms.pojo.Account;
import com.personal.oyl.newffms.pojo.BaseObject;
import com.personal.oyl.newffms.pojo.Consumption;
import com.personal.oyl.newffms.pojo.ConsumptionForm;
import com.personal.oyl.newffms.pojo.ConsumptionItem;
import com.personal.oyl.newffms.pojo.UserProfile;
import com.personal.oyl.newffms.pojo.validator.ConsumptionFormValidator;
import com.personal.oyl.newffms.service.CategoryService;
import com.personal.oyl.newffms.service.TransactionService;
import com.personal.oyl.newffms.service.UserProfileService;


@Controller
@RequestMapping("/consumption")
@SessionAttributes("cpnForm")
public class ConsumptionController {
    
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private CategoryService categoryService;
    
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
    
    
    @RequestMapping("/confirmAdd")
    public String confirmAdd(@Valid @ModelAttribute("cpnForm") ConsumptionForm form, BindingResult result, Model model) throws SQLException {
        if (result.hasErrors()) {
            List<UserProfile> users = userProfileService.select(null);
            
            model.addAttribute("cpnTypes", ConsumptionType.toMapValue());
            model.addAttribute("users", users);
            model.addAttribute("validation", false);
            
            return "consumption/add";
        }
        
        form.getConsumption().calculateCpnTime();
        form.getConsumption().setCpnTypeDesc(form.getConsumption().getCpnType().getDesc());
        
        for ( ConsumptionItem item : form.getCpnItems() ) {
            item.setUserName(userProfileService.selectByKey(item.getOwnerOid()).getUserName());
            item.setCategoryFullDesc(categoryService.selectFullDescByKey(item.getCategoryOid()));
        }
        
        return "consumption/confirmAdd";
    }
    
    
    @RequestMapping("/saveAdd")
    public String saveAdd(Model model) throws SQLException {
        ConsumptionForm form = (ConsumptionForm) model.asMap().get("cpnForm");
        
        form.getConsumption().setAmount(form.getTotalItemAmount());
        form.getConsumption().setConfirmed(true);
        if (null == form.getConsumption().getBaseObject()) {
            BaseObject base = new BaseObject();
            base.setCreateTime(new Date());
            base.setCreateBy("System");
            
            form.getConsumption().setBaseObject(base);
        }
        
        transactionService.createConsumption(form);
        
        return "consumption/add";
    }
    
}
