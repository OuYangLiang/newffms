package com.personal.oyl.newffms.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.personal.oyl.newffms.base.dao.BaseDao;
import com.personal.oyl.newffms.base.dao.DbActionDao;
import com.personal.oyl.newffms.base.dao.PaginatingDao;
import com.personal.oyl.newffms.pojo.ConsumptionItem;

public interface ConsumptionItemDao extends BaseDao<ConsumptionItem>, DbActionDao<ConsumptionItem>, PaginatingDao<ConsumptionItem> {
    public List<ConsumptionItem> queryConsumptionItemByCpn(BigDecimal cpnOid) throws SQLException;
    
    public ConsumptionItem selectOneByCategory(BigDecimal categoryOid) throws SQLException;
}
