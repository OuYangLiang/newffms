package com.personal.oyl.newffms.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.personal.oyl.newffms.report.CategoryConsumption;


public interface ReportService {
    public List<CategoryConsumption> queryCategoryConsumptions(Date start, Date end) throws SQLException;
}
