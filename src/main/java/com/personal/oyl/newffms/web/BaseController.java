package com.personal.oyl.newffms.web;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.base.service.PaginatingService;
import com.personal.oyl.newffms.constants.Constants;
import com.personal.oyl.newffms.pojo.BasePojo;
import com.personal.oyl.newffms.pojo.JqGridJsonRlt;
import com.personal.oyl.newffms.service.AccountAuditService;
import com.personal.oyl.newffms.service.AccountIncomingService;
import com.personal.oyl.newffms.service.AccountService;
import com.personal.oyl.newffms.service.CategoryService;
import com.personal.oyl.newffms.service.ConsumptionItemService;
import com.personal.oyl.newffms.service.ConsumptionService;
import com.personal.oyl.newffms.service.IncomingService;
import com.personal.oyl.newffms.service.TransactionService;
import com.personal.oyl.newffms.service.UserProfileService;

public class BaseController {
    @Autowired
    protected TransactionService transactionService;
    @Autowired
    protected UserProfileService userProfileService;
    @Autowired
    protected CategoryService categoryService;
    @Autowired
    protected ConsumptionService consumptionService;
    @Autowired
    protected ConsumptionItemService consumptionItemService;
    @Autowired
    protected AccountService accountService;
    @Autowired
    protected IncomingService incomingService;
    @Autowired
    protected AccountIncomingService accountIncomingService;
    @Autowired
    protected AccountAuditService accountAuditService;
    
    protected boolean isKeepSearchParameter(HttpServletRequest request)
    {
        String keepSP = request.getParameter("keepSp");
        
        if (Constants.VALUE_YES.equalsIgnoreCase(keepSP)) {
            return true;
        }
        
        return false;

    }

    
    protected void clearSearchParameter(HttpServletRequest request, HttpSession session, String sessionKey_)
    {
        if(!isKeepSearchParameter(request))
        {
            session.removeAttribute(sessionKey_);
        }
    }
    
    protected <T extends BasePojo> JqGridJsonRlt<T> initPaging(PaginatingService<T> service, T param) throws SQLException {
        int count = service.getCountOfSummary(param);
        List<T> list = service.getListOfSummary(param);
        
        JqGridJsonRlt<T> rlt = new JqGridJsonRlt<T>();
        rlt.setRows(list);
        rlt.setPage(param.getRequestPage());
        rlt.setRecords(count);
        rlt.setTotal(BigDecimal.valueOf(count).divide(BigDecimal.valueOf(param.getSizePerPage()), BigDecimal.ROUND_UP).intValue());
        
        return rlt;
    }
}
