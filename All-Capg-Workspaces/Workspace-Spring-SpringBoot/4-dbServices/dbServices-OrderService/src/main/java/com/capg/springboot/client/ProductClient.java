package com.capg.springboot.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.capg.springboot.dto.ProductDTO;

@FeignClient(name = "DBSERVICES-PRODUCTSERVICE")
public interface ProductClient {

    @GetMapping("/products")
    List<ProductDTO> getProducts();
}

 