package com.personal.oyl.newffms.pojo;

import java.util.List;

public class JqGridJsonRlt {
    private int page;//request page
    private int records;// total records from this query
    private int total;//total pages from this query
    private List<? extends Object> rows;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRecords() {
        return records;
    }

    public void setRecords(int records) {
        this.records = records;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<? extends Object> getRows() {
        return rows;
    }

    public void setRows(List<? extends Object> rows) {
        this.rows = rows;
    }
}
