package com.personal.oyl.newffms.web;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.personal.oyl.newffms.pojo.BootstrapTableJsonRlt;
import com.personal.oyl.newffms.pojo.Consumption;
import com.personal.oyl.newffms.pojo.ConsumptionItem;
import com.personal.oyl.newffms.util.DateUtil;

@Controller
@RequestMapping("/consumption")
public class ConsumptionItemController extends BaseController {
	private static final String SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM = "SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM";
	private static final Map<String, String> colMapping;
	
	static {
		colMapping = new HashMap<String, String>();
		colMapping.put("CPN_TIME", "CPN_TIME");
		colMapping.put("consumption.cpnTime", "CPN_TIME");
	}
    
    @RequestMapping("summary")
    public String summary(HttpServletRequest request, Model model, HttpSession session) throws SQLException {
        this.clearSearchParameter(request, session, SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM);
        
        //初始化查询条件。
        
        model.addAttribute("users", userProfileService.selectAllUsers());
        
        //设置默认查询条件值，并放入session中
        
        ConsumptionItem searchParam = (ConsumptionItem) session.getAttribute(SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM);
        
        if (null == searchParam) {
            searchParam = new ConsumptionItem();
            searchParam.setConsumption(new Consumption());
            searchParam.getConsumption().setCpnTimeFrom(DateUtil.getInstance().getFirstTimeOfCurrentMonth());
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
			@RequestParam("itemDesc") String itemDesc,
			@RequestParam("ownerOid") BigDecimal ownerOid,
			@RequestParam("categoryOid") BigDecimal categoryOid,
			@RequestParam("categoryDesc") String categoryDesc, HttpSession session) throws Exception {
    	//从页面接受查询参数，并放入session中。
        ConsumptionItem searchParam = new ConsumptionItem();
        searchParam.setConsumption(new Consumption());
        searchParam.setOwnerOid(ownerOid);
        searchParam.setCategoryOid(categoryOid);
        searchParam.setCategoryDesc(categoryDesc);
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
    public BootstrapTableJsonRlt<ConsumptionItem> listOfSummary(@RequestParam(value = "offset", required = true) int offset,
            @RequestParam(value = "limit", required = true) int limit,
            @RequestParam(value = "sort", required = true) String sort,
            @RequestParam(value = "order", required = true) String order, HttpSession session) throws SQLException {
        
    	int sizePerPage = limit;
		int requestPage = offset / limit + 1;
		String sortField = colMapping.get(sort);
		String sortDir = order;
    	
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
        
        BootstrapTableJsonRlt<ConsumptionItem> rlt =  this.initBootstrapPaging(consumptionItemService, searchParam);
        
        for (ConsumptionItem item : rlt.getRows()) {
            item.setCategoryFullDesc(categoryService.selectFullDescByKey(item.getCategoryOid()));
        }
        
        return rlt;
    }
}
