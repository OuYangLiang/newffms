package com.personal.oyl.newffms.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.personal.oyl.newffms.base.service.BaseService;
import com.personal.oyl.newffms.base.service.DbActionService;
import com.personal.oyl.newffms.base.service.PaginatingService;
import com.personal.oyl.newffms.pojo.Consumption;
import com.personal.oyl.newffms.pojo.key.ConsumptionKey;
import com.personal.oyl.newffms.report.PersonalConsumption;

public interface ConsumptionService extends
		BaseService<Consumption, ConsumptionKey>,
		DbActionService<Consumption, ConsumptionKey>,
		PaginatingService<Consumption> {

	public List<PersonalConsumption> queryPersonalConsumption(Date start,
			Date end) throws SQLException;
}
