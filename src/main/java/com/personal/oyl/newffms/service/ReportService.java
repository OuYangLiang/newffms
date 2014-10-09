package com.personal.oyl.newffms.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.personal.oyl.newffms.report.CategoryConsumption;


public interface ReportService {
    public List<CategoryConsumption> queryCategoryConsumptions(Date start, Date end,
            Set<BigDecimal> excludingRootCategoryOids) throws SQLException;
}
