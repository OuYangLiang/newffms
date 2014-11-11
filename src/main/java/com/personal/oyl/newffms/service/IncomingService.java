package com.personal.oyl.newffms.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.personal.oyl.newffms.base.service.BaseService;
import com.personal.oyl.newffms.base.service.DbActionService;
import com.personal.oyl.newffms.base.service.PaginatingService;
import com.personal.oyl.newffms.pojo.Incoming;
import com.personal.oyl.newffms.pojo.key.IncomingKey;

public interface IncomingService extends BaseService<Incoming, IncomingKey>,
		DbActionService<Incoming, IncomingKey>, PaginatingService<Incoming> {

	public List<Incoming> selectByIncomingDateRange(Date incomingDateFrom,
			Date incomingDateTo) throws SQLException;

}
