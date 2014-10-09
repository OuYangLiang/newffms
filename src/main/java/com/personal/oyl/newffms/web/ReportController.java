package com.personal.oyl.newffms.web;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.personal.oyl.newffms.constants.Constants;
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
    public String consumption(Model model) throws SQLException {
        List<Category> rootCategories = categoryService.selectByLevel(Constants.CATEGORY_LEVEL_ROOT);
        model.addAttribute("rootCategories", rootCategories);
        
        return "/report/consumption";
    }
    
    
    @RequestMapping("/consumptionDataSource")
    @ResponseBody
    public HighChartResult consumptionDataSource(
            @RequestParam(value = "queryMethod", required = false) Integer queryMethod,
            @RequestParam(value = "start", required = false) Date start,
            @RequestParam(value = "end", required = false) Date end,
            @RequestParam(value = "excludeCategories", required = false) String excludeCategories) throws SQLException, ParseException {
        
        Date startParam = null;
        Date endParam   = null;
        String title = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        
        if (null == queryMethod || 1 == queryMethod) {
            startParam = DateUtil.getInstance().getFirstTimeOfCurrentMonth();
            endParam   = DateUtil.getInstance().getLastTimeOfCurrentMonth();
            title = sdf.format(startParam);
            
        } else if (2 == queryMethod) {
            startParam = DateUtil.getInstance().getFirstTimeOfLastMonth();
            endParam   = DateUtil.getInstance().getLastTimeOfLastMonth();
            title = sdf.format(startParam);
            
        } else if (3 == queryMethod) {
            startParam = DateUtil.getInstance().getBeginTime(start);
            endParam   = DateUtil.getInstance().getEndTime(end);
            
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            title = sdf.format(startParam) + " ~ " + sdf.format(endParam);
        }
        
        List<UserProfile> allUsers = userProfileService.selectAllUsers();
        List<Category> allCategories = null;
        List<CategoryConsumption> categoryConsumptions = null;
        
        if (null == excludeCategories) {
            allCategories = categoryService.selectAllCategories();
            categoryConsumptions = reportService.queryCategoryConsumptions(startParam, endParam, null);
        } else {
            String[] excludeCategoryOidStrs = excludeCategories.split("\\|");
            Set<BigDecimal> excludeCategoryOids = new HashSet<BigDecimal>();
            for (String excludeCategoryOidStr : excludeCategoryOidStrs) {
                excludeCategoryOids.add(new BigDecimal(excludeCategoryOidStr));
            }
            
            allCategories = categoryService.selectAllCategoriesWithExclusion(excludeCategoryOids);
            categoryConsumptions = reportService.queryCategoryConsumptions(startParam, endParam, excludeCategoryOids);
        }
        
        HighChartResult rlt = new HighChartResult();
        HighChartGraphResult colRlt = this.columnResult(categoryConsumptions, allCategories, allUsers);
        HighChartGraphResult pieRltOfAll = this.pieResultOfAll(categoryConsumptions, allCategories);
        HighChartGraphResult pieRltOfUser = this.pieResultOfUser(categoryConsumptions);
        
        colRlt.setTitle(title);
        pieRltOfAll.setTitle(title);
        pieRltOfUser.setTitle(title);
        
        rlt.setColRlt(colRlt);
        rlt.setPieRltOfAll(pieRltOfAll);
        rlt.setPieRltOfUser(pieRltOfUser);
        
        return rlt;
    }
    
    
    private HighChartGraphResult pieResultOfUser(List<CategoryConsumption> categoryConsumptions) {
        BigDecimal total = BigDecimal.ZERO;
        Map<String, BigDecimal> userSumMap = new HashMap<String, BigDecimal>();
        
        for (CategoryConsumption item : categoryConsumptions) {
            if (item.getCategoryLevel() == 0 && !BigDecimal.valueOf(-1).equals(item.getUserOid())) {
                total = total.add(item.getTotal());
                
                if (userSumMap.containsKey(item.getUserName())) {
                    BigDecimal oldValue = userSumMap.get(item.getUserName());
                    userSumMap.put(item.getUserName(), oldValue.add(item.getTotal()));
                } else {
                    userSumMap.put(item.getUserName(), item.getTotal());
                }
            }
        }
        
        //初始化返回对象
        HighChartGraphResult rlt = new HighChartGraphResult();
        List<HightChartSeries> seriesList = new ArrayList<HightChartSeries>();
        rlt.setSeries(seriesList);
        HightChartSeries series = new HightChartSeries();
        series.setName("消费比");
        series.setType("pie");
        series.setData(new ArrayList<HightChartSeries>());
        seriesList.add(series);
        
        for (Map.Entry<String, BigDecimal> entry : userSumMap.entrySet() ) {
            BigDecimal userTotal = entry.getValue();
            
            HightChartSeries innerSeries = new HightChartSeries();
            innerSeries.setName(entry.getKey());
            innerSeries.setType("pie");
            innerSeries.setY(userTotal.divide(total, 4, RoundingMode.HALF_UP));
            
            series.getData().add(innerSeries);
        }
        
        return rlt;
    }
    
    
    private HighChartGraphResult pieResultOfAll(List<CategoryConsumption> categoryConsumptions, List<Category> allCategories) {
        Map<String, CategoryConsumption> categoryConsumptionsMap = new HashMap<String, CategoryConsumption>();
        Map<BigDecimal, BigDecimal> sumMap = new HashMap<BigDecimal, BigDecimal>();
        for (CategoryConsumption item : categoryConsumptions) {
            categoryConsumptionsMap.put(item.getCategoryOid() + "_" + item.getUserOid(), item);
            
            if (BigDecimal.valueOf(-1).equals(item.getUserOid())) {
                BigDecimal key = null;
                if (item.getCategoryLevel() == 0) {
                    key = BigDecimal.valueOf(-1);
                }else {
                    key = item.getParentOid();
                }
                
                if (sumMap.containsKey(key)) {
                    BigDecimal oldSum = sumMap.get(key);
                    oldSum = oldSum.add(item.getTotal());
                    sumMap.put(key, oldSum);
                } else {
                    sumMap.put(key, item.getTotal());
                }
            }
        }
        
        //初始化返回对象
        HighChartGraphResult rlt = new HighChartGraphResult();
        List<HightChartSeries> seriesList = new ArrayList<HightChartSeries>();
        List<HightChartSeries> drilldownList = new ArrayList<HightChartSeries>();
        rlt.setSeries(seriesList);
        rlt.setDrilldown(drilldownList);
        
        HightChartSeries series = new HightChartSeries();
        series.setName("比例");
        series.setType("pie");
        series.setData(new ArrayList<HightChartSeries>());
        seriesList.add(series);
        
        
        for (Category category : allCategories) {
            //处理series
            if (category.getCategoryLevel() == 0) {
                BigDecimal usedAmt = categoryConsumptionsMap.get(category.getCategoryOid() + "_-1").getTotal();
                if (usedAmt.compareTo(BigDecimal.ZERO) == 0) {
                    //金额为0的不需要在pie图呈现。
                    continue;
                }
                
                BigDecimal totalAmt = sumMap.get(BigDecimal.valueOf(-1));
                BigDecimal percent  = usedAmt.divide(totalAmt, 4, RoundingMode.HALF_UP);
                
                HightChartSeries innerSeries = new HightChartSeries();
                innerSeries.setName(category.getCategoryDesc());
                innerSeries.setType("pie");
                innerSeries.setY(percent);
                if (!category.getIsLeaf()) {
                    innerSeries.setDrilldown(category.getCategoryOid().toString());
                }
                
                series.getData().add(innerSeries);
            }
            
            //处理drilldown
            if (!category.getIsLeaf()) {
                if (categoryConsumptionsMap.get(category.getCategoryOid() + "_-1").getTotal().compareTo(BigDecimal.ZERO) == 0) {
                    continue;
                }
                
                HightChartSeries drillDownSeries = new HightChartSeries();
                drillDownSeries.setId(category.getCategoryOid().toString());
                drillDownSeries.setType("pie");
                drillDownSeries.setName(category.getCategoryDesc());
                drillDownSeries.setData(new ArrayList<HightChartSeries>());
                
                for (CategoryConsumption item : categoryConsumptions) {
                    if (category.getCategoryOid().equals(item.getParentOid()) && BigDecimal.valueOf(-1).equals(item.getUserOid())) {
                        BigDecimal usedAmt = item.getTotal();
                        if (usedAmt.compareTo(BigDecimal.ZERO) == 0) {
                            //金额为0的不需要在pie图呈现。
                            continue;
                        }
                        
                        BigDecimal totalAmt = sumMap.get((item.getParentOid() == null ? BigDecimal.valueOf(-1) : item.getParentOid()));
                        BigDecimal percent  = usedAmt.divide(totalAmt, 4, RoundingMode.HALF_UP);
                        
                        HightChartSeries innerSeries = new HightChartSeries();
                        innerSeries.setName(item.getCategoryDesc());
                        innerSeries.setY(percent);
                        if (!item.getIsLeaf()) {
                            innerSeries.setDrilldown(item.getCategoryOid().toString());
                        }
                        
                        drillDownSeries.getData().add(innerSeries);
                    }
                }
                
                drilldownList.add(drillDownSeries);
            }
        }
        
        return rlt;
    }
    
    
    private HighChartGraphResult columnResult(List<CategoryConsumption> categoryConsumptions, List<Category> allCategories, List<UserProfile> allUsers) {
        //List转Map，key为categoryOid + "_" + userOid，userOid为-1表示所有人
        Map<String, CategoryConsumption> categoryConsumptionsMap = new HashMap<String, CategoryConsumption>();
        for (CategoryConsumption item : categoryConsumptions) {
            categoryConsumptionsMap.put(item.getCategoryOid() + "_" + item.getUserOid(), item);
        }
        
        //初始化返回对象
        HighChartGraphResult rlt = new HighChartGraphResult();
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
        
        series = new HightChartSeries();
        series.setName("预算");
        series.setType("spline");
        series.setData(new ArrayList<HightChartSeries>());
        for (Category category : allCategories) {
            if (category.getCategoryLevel() == 0) {
                HightChartSeries innerSeries = new HightChartSeries();
                innerSeries.setName(category.getCategoryDesc());
                innerSeries.setY(category.getMonthlyBudget());
                innerSeries.setDrilldown(category.getCategoryOid().toString());
                
                series.getData().add(innerSeries);
            }
        }
        seriesList.add(series);
        
        //处理drilldown
        for (Category category : allCategories) {
            if (!category.getIsLeaf()) {
                series = new HightChartSeries();
                series.setType("spline");
                series.setId(category.getCategoryOid().toString());
                series.setName("预算");
                series.setData(new ArrayList<HightChartSeries>());
                
                for (Category inner : allCategories) {
                    if (category.getCategoryOid().equals(inner.getParentOid())) {
                        HightChartSeries innerSeries = new HightChartSeries();
                        innerSeries.setType("spline");
                        innerSeries.setName(inner.getCategoryDesc());
                        innerSeries.setY(inner.getMonthlyBudget());
                        if (!inner.getIsLeaf()) {
                            innerSeries.setDrilldown(inner.getCategoryOid().toString());
                        }
                        
                        series.getData().add(innerSeries);
                    }
                }
                drilldownList.add(series);
                
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
