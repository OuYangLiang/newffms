package com.personal.oyl.newffms.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.personal.oyl.newffms.base.service.BaseService;
import com.personal.oyl.newffms.base.service.DbActionService;
import com.personal.oyl.newffms.base.service.PaginatingService;
import com.personal.oyl.newffms.pojo.ConsumptionItem;
import com.personal.oyl.newffms.pojo.key.ConsumptionItemKey;

public interface ConsumptionItemService extends
		BaseService<ConsumptionItem, ConsumptionItemKey>,
		DbActionService<ConsumptionItem, ConsumptionItemKey>,
		PaginatingService<ConsumptionItem> {
	public List<ConsumptionItem> queryConsumptionItemByCpn(BigDecimal cpnOid)
			throws SQLException;

	public void deleteByConsumption(BigDecimal cpnOid) throws SQLException;
}
