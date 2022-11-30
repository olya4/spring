package com.geekbrains.homework3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AspectResponseDto {
    private String durationProductService;
    private String durationOrderService;
    private String durationUserService;
}
