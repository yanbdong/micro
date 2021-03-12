package com.itmuch.cloud.study.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
// 配置更改时得到特殊处理
// 需要手动 post /actuator/refresh
@RefreshScope
@Slf4j
public class ConfigClientController {

    @Value("${spring.rabbitmq.queue_name}")
    public String queueName;

    @Value("${spring.rabbitmq.exchange_name}")
    public String exchangeName;

    @Value("${spring.rabbitmq.routing_key}")
    public String routingKey;

    @Value("${profile}")
    private String profile;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/profile")
    public String hello() {
        return this.profile;
    }

    @PostMapping("/send/{info}")
    public String sendAmqp(@PathVariable("info") String info) {
        try {
            String msgId = UUID.randomUUID().toString().replace("-", "").substring(0, 32);
            Map<String, Object> map = new HashMap<>();
            map.put("msgId", msgId);
            map.put("sendTime", DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now()));
            map.put("msg", info);
            this.rabbitTemplate.convertAndSend(exchangeName, routingKey, map);
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
