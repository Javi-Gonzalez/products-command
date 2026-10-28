package com.javi.curso.springcloud.kafkacommand.services;

import com.javi.curso.springcloud.kafkacommand.models.dto.ProductDto;

public interface ProductService {
    ProductDto create(ProductDto dto);
}
