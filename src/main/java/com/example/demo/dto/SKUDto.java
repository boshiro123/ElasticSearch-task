package com.example.demo.dto;

import com.example.demo.models.Product;

public record SKUDto (

     Long id,
     String code,
     String color,
     Boolean access
)
{}
