package com.jfeat.am.module.timeReview.aop;

import cn.hutool.core.stream.CollectorUtil;
import com.jfeat.am.module.timeReview.services.domain.service.domain.TimeHistory;
import com.jfeat.am.module.timeReview.services.domain.service.domain.TimeReview;
import com.jfeat.am.module.timeReview.util.RedisKeySetting;
import com.jfeat.am.module.timeReview.util.RedisUtil;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 */
@Aspect
@Component
public class TimeAop {

    @Autowired
    RedisUtil redisUtil;


    protected static final Logger log = LoggerFactory.getLogger(TimeAop.class);

    //包范围的切面
    //@Pointcut("(execution(* com.jfeat.am.module..*.*(..)) && !execution(* com.jfeat.am.module.timeReview.util..*.*(..)))")
    //注解方式的切面
    @Pointcut(value = "@annotation(com.jfeat.am.module.timeReview.annotation.TimeReview)")
    public void cutService() {
    }

    @Resource
    RedisTemplate redisTemplate;

    private static final String MAX_VALUE_KEY = RedisKeySetting.MAX_VALUE_KEY;
    private static final String TIME_LIST_KEY = RedisKeySetting.TIME_LIST_KEY;
    public static final String TIME_REVIEW_KEY = RedisKeySetting.TIME_REVIEW_KEY;

    @Around("cutService()")
    public Object eavQueryService(ProceedingJoinPoint point) throws Throwable {


        String methodName =  point.getTarget().getClass().getName() +"."+ ((MethodSignature) point.getSignature()).getMethod().getName();

        long starTime=System.currentTimeMillis();
        log.info("------------------------"+methodName+"方法开始执行------------------------");
        Object proceed = point.proceed();
        long endTime=System.currentTimeMillis();
        Long time=endTime-starTime;


        setValue(time,methodName);

        log.info("------------------------时长："+time/1000 +"s------------------------");
        log.info("------------------------"+point.getClass().getName()+" 方法执行结束------------------------");
        return proceed;
    }

    void  setValue(Long time,String methodName){
        BigDecimal timeDecimal = new BigDecimal(time).divide(new BigDecimal(1000),3,BigDecimal.ROUND_HALF_UP);
        Object timeReviewObject = redisUtil.get(TIME_REVIEW_KEY);
        TimeReview timeReview = new TimeReview();
        if(timeReviewObject != null){
            timeReview = (TimeReview)timeReviewObject;
        }


        BigDecimal maxTime = timeReview.getMaxTime();
        if(maxTime != null){
           if( maxTime.compareTo(timeDecimal) > 0){

           }else{
               timeReview.setMaxTime(timeDecimal);
           }
        }
        else{
            timeReview.setMaxTime(timeDecimal);
        }

           Map<String, BigDecimal> nameTimeMap = timeReview.getNameTimeMap();
           if(nameTimeMap == null){
               nameTimeMap = new HashMap<>();
           }
           nameTimeMap.put(methodName,timeDecimal);


        List<TimeHistory> historyList = timeReview.getHistoryList();
        if(historyList == null){
            historyList = new ArrayList<>();
        }
        historyList.add(new TimeHistory(methodName, time));

        timeReview.setNameTimeMap(nameTimeMap);
        timeReview.setHistoryList(historyList);

            redisUtil.set(TIME_REVIEW_KEY,timeReview);

    }





}
