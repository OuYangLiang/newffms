package com.personal.oyl.newffms.web;

import java.sql.SQLException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.personal.oyl.newffms.pojo.Consumption;
import com.personal.oyl.newffms.pojo.ConsumptionItem;
import com.personal.oyl.newffms.pojo.JqGridJsonRlt;
import com.personal.oyl.newffms.util.DateUtil;

@Controller
@RequestMapping("/consumption")
public class ConsumptionItemController extends BaseController {
	private static final String SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM = "SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM";
    
    @RequestMapping("summary")
    public String summary(HttpServletRequest request, HttpSession session) throws SQLException {
        this.clearSearchParameter(request, session, SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM);
        
        //初始化查询条件。
        
        //设置默认查询条件值，并放入session中
        
        ConsumptionItem searchParam = (ConsumptionItem) session.getAttribute(SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM);
        
        if (null == searchParam) {
            searchParam = new ConsumptionItem();
            searchParam.setConsumption(new Consumption());
            searchParam.getConsumption().setCpnTimeFrom(DateUtil.getInstance().getFirstTimeOfLastMonth());
            searchParam.getConsumption().setCpnTimeTo(DateUtil.getInstance().getEndTime(new Date()));
            
            searchParam.setRequestPage(1);
            searchParam.setSizePerPage(10);
            searchParam.setSortField("CPN_TIME");
            searchParam.setSortDir("desc");
            
            session.setAttribute(SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM, searchParam);
        }

        return "consumption/summary";
    }
    
    @RequestMapping("search")
    @ResponseBody
	public String search(@RequestParam("cpnTimeFrom") Date cpnTimeFrom,
			@RequestParam("cpnTimeTo") Date cpnTimeTo,
			@RequestParam("confirmed") Boolean confirmed, 
			@RequestParam("itemDesc") String itemDesc, HttpSession session) throws Exception {
    	//从页面接受查询参数，并放入session中。
        ConsumptionItem searchParam = new ConsumptionItem();
        searchParam.setConsumption(new Consumption());
        searchParam.getConsumption().setCpnTimeFrom(DateUtil.getInstance().getBeginTime(cpnTimeFrom));
        searchParam.getConsumption().setCpnTimeTo(DateUtil.getInstance().getEndTime(cpnTimeTo));
        searchParam.getConsumption().setConfirmed(confirmed);
        searchParam.setItemDesc(itemDesc);
        
        searchParam.trimAllString();
        searchParam.setAllEmptyStringToNull();
        
        session.setAttribute(SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM, searchParam);
        
        return "OK";
    }
    
    @RequestMapping("/listOfSummary")
    @ResponseBody
    public JqGridJsonRlt<ConsumptionItem> listOfSummary(@RequestParam(value = "requestPage", required = true) int requestPage,
            @RequestParam(value = "sizePerPage", required = true) int sizePerPage,
            @RequestParam(value = "sortField", required = true) String sortField,
            @RequestParam(value = "sortDir", required = true) String sortDir, HttpSession session) throws SQLException {
        
        //从session中取出查询对象并查询

    	ConsumptionItem searchParam = (ConsumptionItem) session.getAttribute(SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM);
        
        searchParam.setStart( (requestPage - 1) * sizePerPage );
        searchParam.setSizePerPage(sizePerPage);
        searchParam.setRequestPage(requestPage);
        
        if (sortField != null && !sortField.trim().isEmpty()) {
            searchParam.setSortField(sortField);
            searchParam.setSortDir(sortDir);
        }
        
        session.setAttribute(SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM, searchParam);
        
        JqGridJsonRlt<ConsumptionItem> rlt =  this.initPaging(consumptionItemService, searchParam);
        
        for (ConsumptionItem item : rlt.getRows()) {
            item.setCategoryFullDesc(categoryService.selectFullDescByKey(item.getCategoryOid()));
        }
        
        return rlt;
    }
}
