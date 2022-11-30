package com.geekbrains.homework3.controllers;


import com.geekbrains.homework3.aop.AppAspect;
import com.geekbrains.homework3.dto.AspectResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AspectController {

    private final AppAspect appAspect;

    @GetMapping("/api/v1/statistic")
    public AspectResponseDto showStatistic() {
        String s1 = " Время выполнения ProductService = " + appAspect.getDurationProductService() + " ms";
        String s2 = " Время выполнения OrderService = " + appAspect.getDurationOrderService() + " ms";
        String s3 = " Время выполнения UserService = " + appAspect.getDurationUserService() + " ms";
        return new AspectResponseDto(s1, s2, s3);
    }

}
