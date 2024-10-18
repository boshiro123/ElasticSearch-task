package com.example.demo.mapper;

import com.example.demo.dto.ProductDto;
import com.example.demo.dto.SKUDto;
import com.example.demo.models.Product;
import com.example.demo.models.SKU;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SKUMapper {

    SKUDto toSKUDto(SKU sku);

    SKU toSKU(SKUDto skuDto);

}

