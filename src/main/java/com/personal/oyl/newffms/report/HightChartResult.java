package com.personal.oyl.newffms.report;

import java.util.List;

public class HightChartResult {
    private List<HightChartSeries> series;
    private List<HightChartSeries> drilldown;

    public List<HightChartSeries> getSeries() {
        return series;
    }

    public void setSeries(List<HightChartSeries> series) {
        this.series = series;
    }

    public List<HightChartSeries> getDrilldown() {
        return drilldown;
    }

    public void setDrilldown(List<HightChartSeries> drilldown) {
        this.drilldown = drilldown;
    }

}
