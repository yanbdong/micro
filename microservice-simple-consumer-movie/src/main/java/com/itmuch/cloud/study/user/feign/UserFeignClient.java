package com.itmuch.cloud.study.user.feign;

import com.itmuch.cloud.study.user.entity.User;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

// Auto ribbon binded
//@FeignClient(name = ConsumerMovieApplication.userServiceName)
public interface UserFeignClient {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    User findById(@PathVariable("id") Long id);

    @GetMapping("/get")
    User get(@RequestParam("id") Long id, @RequestParam("username") String username);

    @GetMapping("/getM")
    User getM(@RequestParam Map<String, Object> map);
}