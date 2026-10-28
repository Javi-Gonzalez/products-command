package com.javi.curso.springcloud.kafkacommand.services;

import com.javi.curso.springcloud.kafkacommand.entities.Product;
import com.javi.curso.springcloud.kafkacommand.models.dto.ProductDto;
import com.javi.curso.springcloud.kafkacommand.models.mapper.Mappers;
import com.javi.curso.springcloud.kafkacommand.repositories.ProductRepository;
import org.apache.catalina.mapper.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public ProductDto create(ProductDto dto) {
        return Mappers.toDto(productRepository.save(Mappers.toEntity(dto)));
    }
}
