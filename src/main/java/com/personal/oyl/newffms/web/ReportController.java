package com.personal.oyl.newffms.web;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.personal.oyl.newffms.pojo.Category;
import com.personal.oyl.newffms.pojo.UserProfile;
import com.personal.oyl.newffms.report.CategoryConsumption;
import com.personal.oyl.newffms.report.HighChartGraphResult;
import com.personal.oyl.newffms.report.HighChartResult;
import com.personal.oyl.newffms.report.HightChartSeries;
import com.personal.oyl.newffms.service.CategoryService;
import com.personal.oyl.newffms.service.ReportService;
import com.personal.oyl.newffms.service.UserProfileService;
import com.personal.oyl.newffms.util.DateUtil;

@Controller
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportService reportService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserProfileService userProfileService;
    
    @RequestMapping("/consumption")
    public String consumption(Model model) {
        return "/report/consumption";
    }
    
    @RequestMapping("/consumptionDataSource")
    @ResponseBody
    public HighChartResult consumptionDataSource(
            @RequestParam(value = "queryMethod", required = false) Integer queryMethod,
            @RequestParam(value = "start", required = false) Date start,
            @RequestParam(value = "end", required = false) Date end) throws SQLException, ParseException {
        
        Date startParam = null;
        Date endParam   = null;
        
        if (null == queryMethod || 1 == queryMethod) {
            startParam = DateUtil.getInstance().getFirstTimeOfCurrentMonth();
            endParam   = DateUtil.getInstance().getLastTimeOfCurrentMonth();
        } else if (2 == queryMethod) {
            startParam = DateUtil.getInstance().getFirstTimeOfLastMonth();
            endParam   = DateUtil.getInstance().getLastTimeOfLastMonth();
        } else if (3 == queryMethod) {
            startParam = DateUtil.getInstance().getBeginTime(start);
            endParam   = DateUtil.getInstance().getEndTime(end);
        }
        
        List<CategoryConsumption> categoryConsumptions = reportService.queryCategoryConsumptions(startParam, endParam);
        List<Category> allCategories = categoryService.select(null);
        List<UserProfile> allUsers = userProfileService.select(null);
        
        //List转Map，key为categoryOid + "_" + userOid，userOid为-1表示所有人
        Map<String, CategoryConsumption> categoryConsumptionsMap = new HashMap<String, CategoryConsumption>();
        for (CategoryConsumption item : categoryConsumptions) {
            categoryConsumptionsMap.put(item.getCategoryOid() + "_" + item.getUserOid(), item);
        }
        
        //初始化返回对象
        HighChartResult rlt = new HighChartResult();
        HighChartGraphResult colRlt = new HighChartGraphResult();
        List<HightChartSeries> seriesList = new ArrayList<HightChartSeries>();
        List<HightChartSeries> drilldownList = new ArrayList<HightChartSeries>();
        colRlt.setSeries(seriesList);
        colRlt.setDrilldown(drilldownList);
        rlt.setColRlt(colRlt);
        
        
        //处理series
        HightChartSeries series = new HightChartSeries();
        series.setName("全部");
        series.setData(new ArrayList<HightChartSeries>());
        for (Category category : allCategories) {
            if (category.getCategoryLevel() == 0) {
                CategoryConsumption categoryConsumption = categoryConsumptionsMap.get(category.getCategoryOid() + "_-1");
                HightChartSeries innerSeries = new HightChartSeries();
                innerSeries.setName(categoryConsumption.getCategoryDesc());
                innerSeries.setY(categoryConsumption.getTotal());
                innerSeries.setDrilldown(category.getCategoryOid() + "_-1");
                
                series.getData().add(innerSeries);
            }
        }
        seriesList.add(series);
        
        for (UserProfile user : allUsers) {
            series = new HightChartSeries();
            series.setName(user.getUserName());
            series.setData(new ArrayList<HightChartSeries>());
            
            for (Category category : allCategories) {
                if (category.getCategoryLevel() == 0) {
                    CategoryConsumption categoryConsumption = categoryConsumptionsMap.get(category.getCategoryOid() + "_" + user.getUserOid());
                    HightChartSeries innerSeries = new HightChartSeries();
                    innerSeries.setName(categoryConsumption.getCategoryDesc());
                    innerSeries.setY(categoryConsumption.getTotal());
                    innerSeries.setDrilldown(category.getCategoryOid() + "_" + user.getUserOid());
                    
                    series.getData().add(innerSeries);
                }
            }
            seriesList.add(series);
        }
        
        //处理drilldown
        for (Category category : allCategories) {
            if (!category.getIsLeaf()) {
                //先处理所有人的情况
                series = new HightChartSeries();
                series.setId(category.getCategoryOid() + "_-1");
                series.setName(category.getCategoryDesc());
                series.setData(new ArrayList<HightChartSeries>());
                
                for (CategoryConsumption item : categoryConsumptions) {
                    if (category.getCategoryOid().equals(item.getParentOid()) && BigDecimal.valueOf(-1).equals(item.getUserOid())) {
                        HightChartSeries innerSeries = new HightChartSeries();
                        innerSeries.setName(item.getCategoryDesc());
                        innerSeries.setY(item.getTotal());
                        if (!item.getIsLeaf()) {
                            innerSeries.setDrilldown(item.getCategoryOid() + "_-1");
                        }
                        
                        series.getData().add(innerSeries);
                    }
                }
                
                drilldownList.add(series);
                
                for (UserProfile user : allUsers) {
                    //再处理每个人的情况
                    series = new HightChartSeries();
                    series.setId(category.getCategoryOid() + "_" + user.getUserOid());
                    series.setName(user.getUserName());
                    series.setData(new ArrayList<HightChartSeries>());
                    
                    for (CategoryConsumption item : categoryConsumptions) {
                        if (category.getCategoryOid().equals(item.getParentOid()) && user.getUserOid().equals(item.getUserOid())) {
                            HightChartSeries innerSeries = new HightChartSeries();
                            innerSeries.setName(item.getCategoryDesc());
                            innerSeries.setY(item.getTotal());
                            if (!item.getIsLeaf()) {
                                innerSeries.setDrilldown(item.getCategoryOid() + "_" + user.getUserOid());
                            }
                            
                            series.getData().add(innerSeries);
                        }
                    }
                    
                    drilldownList.add(series);
                }
            }
        }
        
        return rlt;
        
    }
}
