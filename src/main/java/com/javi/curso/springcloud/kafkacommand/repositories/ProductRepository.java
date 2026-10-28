package com.javi.curso.springcloud.kafkacommand.repositories;

import com.javi.curso.springcloud.kafkacommand.entities.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
