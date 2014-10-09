package com.personal.oyl.newffms.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.pojo.Category;
import com.personal.oyl.newffms.pojo.UserProfile;
import com.personal.oyl.newffms.report.CategoryConsumption;
import com.personal.oyl.newffms.report.PersonalConsumption;
import com.personal.oyl.newffms.service.CategoryService;
import com.personal.oyl.newffms.service.ConsumptionService;
import com.personal.oyl.newffms.service.ReportService;
import com.personal.oyl.newffms.service.UserProfileService;

public class ReportServiceImpl implements ReportService {
    @Autowired
    private ConsumptionService consumptionService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserProfileService userProfileService;

    public List<CategoryConsumption> queryCategoryConsumptions(Date start, Date end,
            Set<BigDecimal> excludingRootCategoryOids) throws SQLException {

        List<PersonalConsumption> personalConsumptions = consumptionService.queryPersonalConsumption(start, end);
        List<UserProfile> allUsers = userProfileService.selectAllUsers();
        List<Category> allCategories = null;
        
        if (null == excludingRootCategoryOids) {
            allCategories = categoryService.selectAllCategories();
        } else {
            allCategories = categoryService.selectAllCategoriesWithExclusion(excludingRootCategoryOids);
        }
        
        List<CategoryConsumption> rlt = new ArrayList<CategoryConsumption>();
        
        Map<BigDecimal, UserProfile> userMap = new HashMap<BigDecimal, UserProfile>();
        Map<BigDecimal, Category> catMap = new HashMap<BigDecimal, Category>();
        Map<String, CategoryConsumption> categoryConsumptionsMap = new HashMap<String, CategoryConsumption>();
        
        for (Category category : allCategories) {
            catMap.put(category.getCategoryOid(), category);
            
            CategoryConsumption item = CategoryConsumption.init(category);
            categoryConsumptionsMap.put(category.getCategoryOid() + "_-1", item);
            rlt.add(item);
            
            for (UserProfile user : allUsers) {
                item = CategoryConsumption.init(category, user);
                categoryConsumptionsMap.put(category.getCategoryOid() + "_" + user.getUserOid(), item);
                rlt.add(item);
            }
        }
        
        for (UserProfile user : allUsers) {
            userMap.put(user.getUserOid(), user);
        }
        
        //开始干活。
        
        for (PersonalConsumption personalConsumption : personalConsumptions) {
            BigDecimal key = personalConsumption.getCategoryOid();
            
            if (!catMap.containsKey(key)) {
                //对应的category已经被排除。
                continue;
            }
            
            personalConsumption.setUserName(userMap.get(personalConsumption.getUserOid()).getUserName());//需要优化
            
            do {
                Category category = catMap.get(key);
                CategoryConsumption categoryConsumption = categoryConsumptionsMap.get(key + "_-1");
                categoryConsumption.setTotal(categoryConsumption.getTotal().add(personalConsumption.getTotal()));
                
                categoryConsumption = categoryConsumptionsMap.get(key + "_" + personalConsumption.getUserOid());
                categoryConsumption.setTotal(categoryConsumption.getTotal().add(personalConsumption.getTotal()));
                
                key = category.getParentOid();
                
            }while (key != null);
            
        }
        
        return rlt;
    }

}
