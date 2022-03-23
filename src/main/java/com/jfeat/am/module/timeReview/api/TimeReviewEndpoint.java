package com.jfeat.am.module.timeReview.api;


import com.jfeat.am.module.timeReview.annotation.TimeReview;
import com.jfeat.am.module.timeReview.util.RedisKeySetting;
import com.jfeat.am.module.timeReview.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.am.module.log.annotation.BusinessLog;
import com.jfeat.am.module.timeReview.services.domain.service.*;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * <p>
 * api
 * </p>
 *
 * @author Code generator
 * @since 2022-03-22
 */
@RestController

@Api("timeReview")
@RequestMapping("/api/crud/timeReview")
public class TimeReviewEndpoint {

    @Resource
    TimeReviewService timeReviewService;

    @Resource
    RedisUtil redisUtil;


    @TimeReview
    @BusinessLog(name = "测试", value = "时长测试")
    @GetMapping("")
    @ApiOperation("测试200")
    public Tip deletePlan()  {

        int z = 0;
        for (int i =1;i<20000 ; i++){
            for(int j =1;j<20000 ; j++){
                z = i+j;
            }
        }

        return SuccessTip.create(z);
    }

    @BusinessLog(name = "获得redis统计信息", value = "获得redis统计信息")
    @GetMapping("/info")
    @ApiOperation("获得redis统计信息")
    public Tip info(){
        Object timeReviewObj = redisUtil.get(RedisKeySetting.TIME_REVIEW_KEY);

        return SuccessTip.create(timeReviewObj);
    }

}
