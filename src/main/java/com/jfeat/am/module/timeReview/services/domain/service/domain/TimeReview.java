package com.jfeat.am.module.timeReview.services.domain.service.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class TimeReview {
    public BigDecimal maxTime;
    public Map<String,BigDecimal> nameTimeMap;
    public List<TimeHistory> historyList;

    public List<TimeHistory> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<TimeHistory> historyList) {
        this.historyList = historyList;
    }

    public BigDecimal getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(BigDecimal maxTime) {
        this.maxTime = maxTime;
    }

    public Map<String, BigDecimal> getNameTimeMap() {
        return nameTimeMap;
    }

    public void setNameTimeMap(Map<String, BigDecimal> nameTimeMap) {
        this.nameTimeMap = nameTimeMap;
    }
}
