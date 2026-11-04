package com.javi.curso.springcloud.kafka.command.services;

import com.javi.curso.springcloud.kafka.command.entities.Product;
import com.javi.curso.springcloud.kafka.command.models.dto.ProductDto;
import com.javi.curso.springcloud.kafka.command.models.mapper.Mappers;
import com.javi.curso.springcloud.kafka.command.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Override
    @Transactional(readOnly = true)
    public ProductDto findById(Long id) {
        return productRepository.findById(id).map(Mappers::toDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> findAll() {
        return productRepository.findAll()
                .stream()
                .map(Mappers::toDto)
                .toList();
    }

    @Override
    @Transactional
    public ProductDto update(Long id, ProductDto dto) {
        Optional<Product> entityOptional = productRepository.findById(id);
        if (entityOptional.isPresent()) {
            Product entity = entityOptional.get();
            entity.setName(dto.name());
            entity.setPrice(dto.price());
            return Mappers.toDto(productRepository.save(entity));
        }
        return null;
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        boolean result = productRepository.existsById(id);
        if (result) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
