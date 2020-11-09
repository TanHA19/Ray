package com.tan.play.sms.ray.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/** @Author 谭婧杰 定时任务查询用户充值是否已经到期 5分钟执行一次 */
@Configuration
@EnableScheduling
@Slf4j
public class RechargeInquireScheduleTask {}
