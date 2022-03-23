package com.jfeat.am.module.timeReview.services.domain.service.domain;

import java.math.BigDecimal;

public class TimeHistory {

    String name;
    BigDecimal time;

    public TimeHistory(){};

    public TimeHistory(String name,BigDecimal time){
        this.name = name;
        this.time = time;
    }

    public TimeHistory(String name, Long time){
        this.name = name;
        this.time = new BigDecimal(time).divide(new BigDecimal(1000),3,BigDecimal.ROUND_HALF_UP);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getTime() {
        return time;
    }

    public void setTime(BigDecimal time) {
        this.time = time;
    }
}
