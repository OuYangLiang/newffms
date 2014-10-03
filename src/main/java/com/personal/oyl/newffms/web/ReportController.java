package com.personal.oyl.newffms.web;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.personal.oyl.newffms.pojo.Category;
import com.personal.oyl.newffms.pojo.UserProfile;
import com.personal.oyl.newffms.report.CategoryConsumption;
import com.personal.oyl.newffms.report.HightChartResult;
import com.personal.oyl.newffms.report.HightChartSeries;
import com.personal.oyl.newffms.service.CategoryService;
import com.personal.oyl.newffms.service.ReportService;
import com.personal.oyl.newffms.service.UserProfileService;

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
    public HightChartResult consumptionDataSource() throws SQLException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        List<CategoryConsumption> categoryConsumptions = reportService.queryCategoryConsumptions(sdf.parse("2009-09-01 00:00:00"), sdf.parse("2014-09-30 23:59:59"));
        List<Category> allCategories = categoryService.select(null);
        List<UserProfile> allUsers = userProfileService.select(null);
        
        //List转Map，key为categoryOid + "_" + userOid，userOid为-1表示所有人
        Map<String, CategoryConsumption> categoryConsumptionsMap = new HashMap<String, CategoryConsumption>();
        for (CategoryConsumption item : categoryConsumptions) {
            categoryConsumptionsMap.put(item.getCategoryOid() + "_" + item.getUserOid(), item);
        }
        
        //初始化返回对象
        HightChartResult rlt = new HightChartResult();
        List<HightChartSeries> seriesList = new ArrayList<HightChartSeries>();
        List<HightChartSeries> drilldownList = new ArrayList<HightChartSeries>();
        rlt.setSeries(seriesList);
        rlt.setDrilldown(drilldownList);
        
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
