package com.example.demo.dto;

import lombok.Data;
import org.springframework.validation.annotation.Validated;


@Data
@Validated
public class LongUrlRequestDto {

    private String url;
}
