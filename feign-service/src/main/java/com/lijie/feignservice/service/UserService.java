package com.lijie.feignservice.service;

import com.lijie.feignservice.domain.CommonResult;
import com.lijie.feignservice.domain.User;
import com.lijie.feignservice.service.impl.UserFallbackService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Created by macro on 2019/9/5.
 * 通过@FeignClient注解实现了一个Feign客户端，其中的value为user-service表示这是对user-service服务的接口调用客户端.
 * 最总像controller调service层一样，只需要将这个UserService层主到需要的controller层就可以实现负载均衡了。
 * UserFeignController里面注入了UserService。这个UserService里面的value = "user-service"表示的就是要去调用的
 * 微服务的名字是user-service。fallback = UserFallbackService.class表示如果user-service这个微服务调不通，那么服务降级后
 * 就会走UserFallbackService这个类里面的方法
 */
@FeignClient(value = "user-service",fallback = UserFallbackService.class)
public interface UserService {
    @PostMapping("/user/create")
    CommonResult create(@RequestBody User user);

    @GetMapping("/user/{id}")
    CommonResult<User> getUser(@PathVariable Long id);

    @GetMapping("/user/getByUsername")
    CommonResult<User> getByUsername(@RequestParam String username);

    @PostMapping("/user/update")
    CommonResult update(@RequestBody User user);

    @PostMapping("/user/delete/{id}")
    CommonResult delete(@PathVariable Long id);
}
