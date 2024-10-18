package com.example.demo.controllers;

import com.example.demo.dto.ProductDto;
import com.example.demo.services.ESService;
import com.example.demo.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
@CrossOrigin(origins = "http://localhost:4200")
public class ApiController {

    private final ProductService productService;

    private final ESService esService;

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @GetMapping("/product/all")
    public List<ProductDto> getAllProducts() {
        return productService.getAll();
    }


    @GetMapping("/createIndex")
    public String createAnIndex() throws IOException {
        esService.createAnIndex();
        esService.indexingDB();
        return "esService.createAnIndex()";
    }

    @PostMapping("/index/add")
    public String addToES(@RequestParam("id") Long id) throws IOException {
        esService.addToES(id);
        return "It was indexed";
    }

    @GetMapping(value = "/search", params = {"text", "color"})
    public List<ProductDto> filterSearch(@RequestParam("text") String text,
                                         @RequestParam("color") String color) throws IOException {
        return esService.search(text, color);
    }


}
