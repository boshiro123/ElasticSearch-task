package com.example.demo.services;

import com.example.demo.dto.ProductDto;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductDto> getAll(){
        return productRepository.findAll().stream()
                .map(productMapper::toProductDto)
                .toList();
    }
    public ProductDto getById(Long id){
        return productRepository.findById(id).map(productMapper::toProductDto).get();
    }

}
