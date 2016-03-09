package com.personal.oyl.newffms.web;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.base.service.PaginatingService;
import com.personal.oyl.newffms.constants.Constants;
import com.personal.oyl.newffms.pojo.BasePojo;
import com.personal.oyl.newffms.pojo.BootstrapTableJsonRlt;
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

public abstract class BaseController {
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
    
    protected final boolean isKeepSearchParameter(HttpServletRequest request)
    {
        String keepSP = request.getParameter("keepSp");
        
        if (Constants.VALUE_YES.equalsIgnoreCase(keepSP)) {
            return true;
        }
        
        return false;

    }

    protected final void clearSearchParameter(HttpServletRequest request, HttpSession session, String sessionKey_)
    {
        if(!isKeepSearchParameter(request))
        {
            session.removeAttribute(sessionKey_);
        }
    }
    
    protected final <T extends BasePojo> JqGridJsonRlt<T> initPaging(PaginatingService<T> service, T param) throws SQLException {
        int count = service.getCountOfSummary(param);
        
        List<T> list = null;
        
        if (count == 0) {
        	list = new ArrayList<T>();
        } else {
        	list = service.getListOfSummary(param);
        }
        
        JqGridJsonRlt<T> rlt = new JqGridJsonRlt<T>();
        rlt.setRows(list);
        rlt.setPage(param.getRequestPage());
        rlt.setRecords(count);
        rlt.setTotal(BigDecimal.valueOf(count).divide(BigDecimal.valueOf(param.getSizePerPage()), BigDecimal.ROUND_UP).intValue());
        
        return rlt;
    }
    
    protected final <T extends BasePojo> BootstrapTableJsonRlt<T> initBootstrapPaging(PaginatingService<T> service, T param) throws SQLException {
        int count = service.getCountOfSummary(param);
        
        List<T> list = null;
        
        if (count == 0) {
        	list = new ArrayList<T>();
        } else {
        	list = service.getListOfSummary(param);
        }
        
        BootstrapTableJsonRlt<T> rlt = new BootstrapTableJsonRlt<T>();
        rlt.setRows(list);
        rlt.setTotal(count);
        
        return rlt;
    }
}
