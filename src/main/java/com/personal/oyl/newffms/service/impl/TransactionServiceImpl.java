package com.personal.oyl.newffms.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.constants.AccountAuditType;
import com.personal.oyl.newffms.constants.AccountType;
import com.personal.oyl.newffms.pojo.Account;
import com.personal.oyl.newffms.pojo.AccountAudit;
import com.personal.oyl.newffms.pojo.AccountConsumption;
import com.personal.oyl.newffms.pojo.AccountIncoming;
import com.personal.oyl.newffms.pojo.BaseObject;
import com.personal.oyl.newffms.pojo.Category;
import com.personal.oyl.newffms.pojo.Consumption;
import com.personal.oyl.newffms.pojo.ConsumptionForm;
import com.personal.oyl.newffms.pojo.ConsumptionItem;
import com.personal.oyl.newffms.pojo.Incoming;
import com.personal.oyl.newffms.pojo.UserProfile;
import com.personal.oyl.newffms.pojo.key.AccountKey;
import com.personal.oyl.newffms.pojo.key.CategoryKey;
import com.personal.oyl.newffms.pojo.key.ConsumptionKey;
import com.personal.oyl.newffms.pojo.key.IncomingKey;
import com.personal.oyl.newffms.service.AccountAuditService;
import com.personal.oyl.newffms.service.AccountConsumptionService;
import com.personal.oyl.newffms.service.AccountIncomingService;
import com.personal.oyl.newffms.service.AccountService;
import com.personal.oyl.newffms.service.CategoryService;
import com.personal.oyl.newffms.service.ConsumptionItemService;
import com.personal.oyl.newffms.service.ConsumptionService;
import com.personal.oyl.newffms.service.IncomingService;
import com.personal.oyl.newffms.service.TransactionService;
import com.personal.oyl.newffms.service.UserProfileService;

public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private ConsumptionService consumptionService;
    @Autowired
    private ConsumptionItemService consumptionItemService;
    @Autowired
    private AccountConsumptionService accountConsumptionService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountAuditService accountAuditService;
    @Autowired
    private IncomingService incomingService;
    @Autowired
    private AccountIncomingService accountIncomingService;
    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private CategoryService categoryService;

    @Override
    public void createConsumption(ConsumptionForm form) throws SQLException {
        consumptionService.insert(form.getConsumption());
        
        for (ConsumptionItem item : form.getCpnItems()) {
            item.setCpnOid(form.getConsumption().getCpnOid());
            
            consumptionItemService.insert(item);
        }
        
        for (Account acnt : form.getAccounts()) {
            AccountConsumption item = new AccountConsumption();
            item.setAcntOid(acnt.getAcntOid());
            item.setAmount(acnt.getPayment());
            item.setCpnOid(form.getConsumption().getCpnOid());
            
            accountConsumptionService.insert(item);
        }
    }

    @Override
    public void updateConsumption(ConsumptionForm form) throws SQLException {
        accountConsumptionService.deleteByConsumption(form.getConsumption().getCpnOid());
        consumptionItemService.deleteByConsumption(form.getConsumption().getCpnOid());
        
        for (ConsumptionItem item : form.getCpnItems()) {
            item.setCpnOid(form.getConsumption().getCpnOid());
            
            consumptionItemService.insert(item);
        }
        
        for (Account acnt : form.getAccounts()) {
            AccountConsumption item = new AccountConsumption();
            item.setAcntOid(acnt.getAcntOid());
            item.setAmount(acnt.getPayment());
            item.setCpnOid(form.getConsumption().getCpnOid());
            
            accountConsumptionService.insert(item);
        }
        
        consumptionService.updateByPrimaryKeySelective(form.getConsumption());
    }

    @Override
    public void deleteConsumption(BigDecimal cpnOid) throws SQLException {
        accountConsumptionService.deleteByConsumption(cpnOid);
        consumptionItemService.deleteByConsumption(cpnOid);
        
        consumptionService.deleteByKey(new ConsumptionKey(cpnOid));
    }

    @Override
    public void confirmConsumption(BigDecimal cpnOid, String operator) throws SQLException {
        Date now = new Date();
        
        Consumption oldObj = consumptionService.selectByKey(new ConsumptionKey(cpnOid));
        
        Consumption newObj = new Consumption();
        newObj.setCpnOid(cpnOid);
        newObj.setConfirmed(true);
        newObj.setBaseObject(new BaseObject());
        newObj.getBaseObject().setUpdateTime(now);
        newObj.getBaseObject().setUpdateBy(operator);
        newObj.getBaseObject().setSeqNo(oldObj.getBaseObject().getSeqNo());
        
        consumptionService.updateByPrimaryKeySelective(newObj);
        
        List<AccountConsumption> acntConsumptions = accountConsumptionService.selectByConsumption(cpnOid);
        for (AccountConsumption acntConsumption : acntConsumptions) {
            Account oldAcnt = accountService.selectByKey(new AccountKey(acntConsumption.getAcntOid()));
            oldAcnt.subtract(acntConsumption.getAmount());
            oldAcnt.getBaseObject().setUpdateTime(now);
            oldAcnt.getBaseObject().setUpdateBy(operator);
            
            oldAcnt.setAcntDesc(null);
            oldAcnt.setAcntType(null);
            oldAcnt.setQuota(null);
            oldAcnt.setOwnerOid(null);
            oldAcnt.getBaseObject().setCreateBy(null);
            oldAcnt.getBaseObject().setCreateTime(null);
            
            accountService.updateByPrimaryKeySelective(oldAcnt);
            
            AccountAudit audit = new AccountAudit();
            audit.setBaseObject(new BaseObject());
            audit.getBaseObject().setCreateBy(operator);
            audit.getBaseObject().setCreateTime(now);
            
            audit.setAdtDesc(oldObj.getCpnType().getDesc());
            audit.setAdtType(AccountAuditType.Subtract);
            audit.setAmount(acntConsumption.getAmount());
            audit.setConfirmed(true);
            audit.setAcntOid(oldAcnt.getAcntOid());
            audit.setCpnOid(oldObj.getCpnOid());
            
            accountAuditService.insert(audit);
        }
    }

    @Override
    public void rollbackConsumption(BigDecimal cpnOid, String operator) throws SQLException {
        Date now = new Date();
        
        Consumption oldObj = consumptionService.selectByKey(new ConsumptionKey(cpnOid));
        
        Consumption newObj = new Consumption();
        newObj.setCpnOid(cpnOid);
        newObj.setConfirmed(false);
        newObj.setBaseObject(new BaseObject());
        newObj.getBaseObject().setUpdateTime(now);
        newObj.getBaseObject().setUpdateBy(operator);
        newObj.getBaseObject().setSeqNo(oldObj.getBaseObject().getSeqNo());
        
        consumptionService.updateByPrimaryKeySelective(newObj);
        accountAuditService.deleteByConsumption(cpnOid);
        
        List<AccountConsumption> acntConsumptions = accountConsumptionService.selectByConsumption(cpnOid);
        for (AccountConsumption acntConsumption : acntConsumptions) {
            Account oldAcnt = accountService.selectByKey(new AccountKey(acntConsumption.getAcntOid()));
            oldAcnt.add(acntConsumption.getAmount());
            oldAcnt.getBaseObject().setUpdateTime(now);
            oldAcnt.getBaseObject().setUpdateBy(operator);
            
            oldAcnt.setAcntDesc(null);
            oldAcnt.setAcntType(null);
            oldAcnt.setQuota(null);
            oldAcnt.setOwnerOid(null);
            oldAcnt.getBaseObject().setCreateBy(null);
            oldAcnt.getBaseObject().setCreateTime(null);
            
            accountService.updateByPrimaryKeySelective(oldAcnt);
        }
    }

    @Override
    public void createIncoming(Incoming form) throws SQLException {
        incomingService.insert(form);
        
        AccountIncoming param = new AccountIncoming();
        param.setAcntOid(form.getAcntOid());
        param.setIncomingOid(form.getIncomingOid());
        
        accountIncomingService.insert(param);
        
    }

    @Override
    public void updateIncoming(Incoming form) throws SQLException {
        incomingService.updateByPrimaryKeySelective(form);
        
        accountIncomingService.deleteByIncoming(form.getIncomingOid());
        
        AccountIncoming param = new AccountIncoming();
        param.setAcntOid(form.getAcntOid());
        param.setIncomingOid(form.getIncomingOid());
        
        accountIncomingService.insert(param);
    }

    @Override
    public void deleteIncoming(BigDecimal incomingOid) throws SQLException {
        accountIncomingService.deleteByIncoming(incomingOid);
        
        incomingService.deleteByKey(new IncomingKey(incomingOid));
    }

    @Override
    public void confirmIncoming(BigDecimal incomingOid, String operator) throws SQLException {
        Date now = new Date();
        Incoming oldObj = incomingService.selectByKey(new IncomingKey(incomingOid));
        
        Incoming newObj = new Incoming();
        newObj.setIncomingOid(incomingOid);
        newObj.setConfirmed(true);
        newObj.setBaseObject(new BaseObject());
        newObj.getBaseObject().setUpdateTime(now);
        newObj.getBaseObject().setUpdateBy(operator);
        newObj.getBaseObject().setSeqNo(oldObj.getBaseObject().getSeqNo());
        
        incomingService.updateByPrimaryKeySelective(newObj);
        
        AccountIncoming acntIncoming = accountIncomingService.selectByIncoming(incomingOid);
        Account oldAcnt = accountService.selectByKey(new AccountKey(acntIncoming.getAcntOid()));
        oldAcnt.add(oldObj.getAmount());
        oldAcnt.getBaseObject().setUpdateTime(now);
        oldAcnt.getBaseObject().setUpdateBy(operator);
        
        oldAcnt.setAcntDesc(null);
        oldAcnt.setAcntType(null);
        oldAcnt.setQuota(null);
        oldAcnt.setOwnerOid(null);
        oldAcnt.getBaseObject().setCreateBy(null);
        oldAcnt.getBaseObject().setCreateTime(null);
        
        accountService.updateByPrimaryKeySelective(oldAcnt);
        
        AccountAudit audit = new AccountAudit();
        audit.setBaseObject(new BaseObject());
        audit.getBaseObject().setCreateBy(operator);
        audit.getBaseObject().setCreateTime(now);
        
        audit.setAdtDesc("[" + oldObj.getIncomingType().getDesc() + "], " + oldObj.getIncomingDesc());
        audit.setAdtType(AccountAuditType.Add);
        audit.setAmount(oldObj.getAmount());
        audit.setConfirmed(true);
        audit.setAcntOid(oldAcnt.getAcntOid());
        audit.setIncomingOid(incomingOid);
        
        accountAuditService.insert(audit);
    }

    @Override
    public void rollbackIncoming(BigDecimal incomingOid, String operator) throws SQLException {
        Date now = new Date();
        Incoming oldObj = incomingService.selectByKey(new IncomingKey(incomingOid));
        
        Incoming newObj = new Incoming();
        newObj.setIncomingOid(incomingOid);
        newObj.setConfirmed(false);
        newObj.setBaseObject(new BaseObject());
        newObj.getBaseObject().setUpdateTime(now);
        newObj.getBaseObject().setUpdateBy(operator);
        newObj.getBaseObject().setSeqNo(oldObj.getBaseObject().getSeqNo());
        
        incomingService.updateByPrimaryKeySelective(newObj);
        accountAuditService.deleteByIncoming(incomingOid);
        
        AccountIncoming acntIncoming = accountIncomingService.selectByIncoming(incomingOid);
        Account oldAcnt = accountService.selectByKey(new AccountKey(acntIncoming.getAcntOid()));
        oldAcnt.subtract(oldObj.getAmount());
        oldAcnt.getBaseObject().setUpdateTime(now);
        oldAcnt.getBaseObject().setUpdateBy(operator);
        
        oldAcnt.setAcntDesc(null);
        oldAcnt.setAcntType(null);
        oldAcnt.setQuota(null);
        oldAcnt.setOwnerOid(null);
        oldAcnt.getBaseObject().setCreateBy(null);
        oldAcnt.getBaseObject().setCreateTime(null);
        
        accountService.updateByPrimaryKeySelective(oldAcnt);
    }

    @Override
    public void createAccount(Account form) throws SQLException {
        accountService.insert(form);
        
        AccountAudit audit = new AccountAudit();
        audit.setBaseObject(form.getBaseObject());
        
        audit.setAdtDesc("初始化");
        audit.setAdtType(AccountAuditType.Add);
        audit.setAmount(form.getBalance());
        audit.setConfirmed(true);
        audit.setAcntOid(form.getAcntOid());
        
        accountAuditService.insert(audit);
    }

    @Override
    public void updateAccount(Account form) throws SQLException {
        Account oldObj = accountService.selectByKey(new AccountKey(form.getAcntOid()));
        accountService.updateByPrimaryKeySelective(form);
        
        boolean balanceChanged = oldObj.getBalance().compareTo(form.getBalance()) != 0;
        boolean quotaChanged   = false;
        boolean debtChanged    = false;
        
        if (oldObj.getAcntType().equals(AccountType.Creditcard)) {
            quotaChanged = oldObj.getQuota().compareTo(form.getQuota()) != 0;
            debtChanged  = oldObj.getDebt().compareTo(form.getDebt()) != 0;
        }
        
        if (balanceChanged || quotaChanged || debtChanged) {
            AccountAudit audit = new AccountAudit();
            audit.setBaseObject(new BaseObject());
            audit.getBaseObject().setCreateBy(form.getBaseObject().getUpdateBy());
            audit.getBaseObject().setCreateTime(new Date());
            
            String desc = null;
            
            if (oldObj.getAcntType().equals(AccountType.Creditcard)) {
                desc = "余额：[" + oldObj.getBalance() + "]->[" + form.getBalance() + "], 限额：[" + oldObj.getQuota()
                        + "]->[" + form.getQuota() + "]，欠款：[" + oldObj.getDebt() + "]->[" + form.getDebt() + "]。";
            } else {
                desc = "余额：[" + oldObj.getBalance() + "]->[" + form.getBalance() + "]";
            }
            
            audit.setAdtDesc(desc);
            audit.setAdtType(AccountAuditType.Change);
            audit.setAmount(form.getBalance().subtract(oldObj.getBalance()));
            audit.setConfirmed(true);
            audit.setAcntOid(oldObj.getAcntOid());
            
            accountAuditService.insert(audit);
        }
    }

    @Override
    public void deleteAccount(BigDecimal acntOid) throws SQLException {
        accountAuditService.deleteByAcnt(acntOid);
        accountService.deleteByKey(new AccountKey(acntOid));
    }

    @Override
    public void doAccountTransfer(BigDecimal srcAcntOid, BigDecimal targetAcntOid, BigDecimal payment, String operator)
            throws SQLException {
        Date now = new Date();
        
        Account srcObj = accountService.selectByKey(new AccountKey(srcAcntOid));
        Account targetObj = accountService.selectByKey(new AccountKey(targetAcntOid));
        
        srcObj.subtract(payment);
        targetObj.add(payment);
        
        Account obj = new Account();
        obj.setAcntOid(srcObj.getAcntOid());
        obj.setBalance(srcObj.getBalance());
        obj.setDebt(srcObj.getDebt());
        obj.setBaseObject(srcObj.getBaseObject());
        obj.getBaseObject().setCreateBy(null);
        obj.getBaseObject().setCreateTime(null);
        obj.getBaseObject().setUpdateTime(now);
        obj.getBaseObject().setUpdateBy(operator);
        
        accountService.updateByPrimaryKeySelective(obj);
        
        obj = new Account();
        obj.setAcntOid(targetObj.getAcntOid());
        obj.setBalance(targetObj.getBalance());
        obj.setDebt(targetObj.getDebt());
        obj.setBaseObject(targetObj.getBaseObject());
        obj.getBaseObject().setCreateBy(null);
        obj.getBaseObject().setCreateTime(null);
        obj.getBaseObject().setUpdateTime(now);
        obj.getBaseObject().setUpdateBy(operator);
        
        accountService.updateByPrimaryKeySelective(obj);
        
        
        AccountAudit audit = new AccountAudit();
        audit.setBaseObject(new BaseObject());
        audit.getBaseObject().setCreateBy(operator);
        audit.getBaseObject().setCreateTime(now);
        audit.setAdtDesc("转账至：" + targetObj.getAcntHumanDesc());
        audit.setAdtType(AccountAuditType.Subtract);
        audit.setAmount(payment);
        audit.setConfirmed(true);
        audit.setAcntOid(srcAcntOid);
        audit.setRefAcntOid(targetAcntOid);
        
        accountAuditService.insert(audit);
        
        audit = new AccountAudit();
        audit.setBaseObject(new BaseObject());
        audit.getBaseObject().setCreateBy(operator);
        audit.getBaseObject().setCreateTime(now);
        audit.setAdtDesc("进账自：" + srcObj.getAcntHumanDesc());
        audit.setAdtType(AccountAuditType.Add);
        audit.setAmount(payment);
        audit.setConfirmed(true);
        audit.setAcntOid(targetAcntOid);
        audit.setRefAcntOid(srcAcntOid);
        
        accountAuditService.insert(audit);
    }

    @Override
	public void updateMyProfile(UserProfile form) throws SQLException {
		userProfileService.updateByPrimaryKeySelective(form);
	}

    @Override
	public void createCategory(Category form) throws SQLException {
		
		Category parent = null;
		
		if (null == form.getParentOid()) {
			form.setCategoryLevel(Integer.valueOf(0));
		} else {
			parent = categoryService.selectByKey(new CategoryKey(form.getParentOid()));
			form.setCategoryLevel(parent.getCategoryLevel() + 1);
		}
		
		form.setIsLeaf(true);
		
		categoryService.insert(form);
		
		while (null != parent) {
			Category newObj = new Category();
			newObj.setBaseObject(new BaseObject());
			newObj.getBaseObject().setSeqNo(parent.getBaseObject().getSeqNo());
			newObj.getBaseObject().setUpdateBy(form.getBaseObject().getCreateBy());
			newObj.getBaseObject().setUpdateTime(form.getBaseObject().getCreateTime());
			
			newObj.setCategoryOid(parent.getCategoryOid());
			newObj.setMonthlyBudget(categoryService.selectTotalBudgetByParent(parent.getCategoryOid()));
			
			if (parent.getIsLeaf()) {
				newObj.setIsLeaf(false);
			}
			
			categoryService.updateByPrimaryKeySelective(newObj);
			
			if (null == parent.getParentOid()) {
				parent = null;
			} else {
				parent = categoryService.selectByKey(new CategoryKey(parent.getParentOid()));
			}
		}
	}

    @Override
	public void updateCategory(Category form) throws SQLException {
		categoryService.updateByPrimaryKeySelective(form);
		
		if (null != form.getMonthlyBudget()) {
			
			Category parent = null;
			
			if (null != form.getParentOid()) {
				parent = categoryService.selectByKey(new CategoryKey(form.getParentOid()));
			}
			
			while (null != parent) {
				Category newObj = new Category();
				newObj.setBaseObject(new BaseObject());
				newObj.getBaseObject().setSeqNo(parent.getBaseObject().getSeqNo());
				newObj.getBaseObject().setUpdateBy(form.getBaseObject().getUpdateBy());
				newObj.getBaseObject().setUpdateTime(form.getBaseObject().getUpdateTime());
				
				newObj.setCategoryOid(parent.getCategoryOid());
				newObj.setMonthlyBudget(categoryService.selectTotalBudgetByParent(parent.getCategoryOid()));
				
				categoryService.updateByPrimaryKeySelective(newObj);
				
				if (null == parent.getParentOid()) {
					parent = null;
				} else {
					parent = categoryService.selectByKey(new CategoryKey(parent.getParentOid()));
				}
			}
		}
	}

    @Override
	public void deleteCategory(BigDecimal categoryOid, String operator) throws SQLException {
		Category oldObj = categoryService.selectByKey(new CategoryKey(categoryOid));
		categoryService.deleteByKey(new CategoryKey(categoryOid));
		
		Category parent = null;
		
		if (null != oldObj.getParentOid()) {
			parent = categoryService.selectByKey(new CategoryKey(oldObj.getParentOid()));
		}
		
		while (null != parent) {
			Category newObj = new Category();
			newObj.setBaseObject(new BaseObject());
			newObj.getBaseObject().setSeqNo(parent.getBaseObject().getSeqNo());
			newObj.getBaseObject().setUpdateBy(operator);
			newObj.getBaseObject().setUpdateTime(new Date());
			
			newObj.setCategoryOid(parent.getCategoryOid());
			newObj.setMonthlyBudget(categoryService.selectTotalBudgetByParent(parent.getCategoryOid()));
			
			if (null == newObj.getMonthlyBudget() || BigDecimal.ZERO.compareTo(newObj.getMonthlyBudget()) == 0) {
				List<Category> subList = categoryService.selectByParent(newObj.getCategoryOid());
				
				if (null == subList || subList.isEmpty()) {
					newObj.setIsLeaf(true);
					newObj.setMonthlyBudget(BigDecimal.ZERO);
				}
			}
			
			categoryService.updateByPrimaryKeySelective(newObj);
			
			if (null == parent.getParentOid()) {
				parent = null;
			} else {
				parent = categoryService.selectByKey(new CategoryKey(parent.getParentOid()));
			}
		}
		
	}

}
