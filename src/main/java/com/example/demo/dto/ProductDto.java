package com.example.demo.dto;

import java.sql.Date;
import java.util.List;

public record ProductDto(
        Long id,
        String name,
        Integer price,
        Date date,
        String description,
        List<SKUDto> skuList
) {
}
