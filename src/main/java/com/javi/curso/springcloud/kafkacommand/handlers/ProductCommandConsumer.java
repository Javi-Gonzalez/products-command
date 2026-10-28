package com.javi.curso.springcloud.kafkacommand.handlers;


import com.javi.curso.springcloud.kafkacommand.models.Command;
import com.javi.curso.springcloud.kafkacommand.models.Reply;
import com.javi.curso.springcloud.kafkacommand.models.dto.ProductDto;
import com.javi.curso.springcloud.kafkacommand.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;
import java.util.function.Function;

@Configuration
public class ProductCommandConsumer {

    private static final Logger log = LoggerFactory.getLogger(ProductCommandConsumer.class);

    private final ProductService service;

    public ProductCommandConsumer(ProductService service) {
        this.service = service;
    }

    @Bean
    public Function<Command<ProductDto>, Reply<?>> handleCommands() {
        return cmd -> {
            String type = cmd.type() == null ? "" : cmd.type().toUpperCase();

            switch (type) {
                case "CREATE" -> {
                    if(cmd.body() == null) {
                        log.warn("Create empty body");
                        return new Reply<>("ERROR","Create Empty body",null);
                    }
                    ProductDto productSave = service.create( cmd.body());

                    log.info("Creating product name={}, price={}", productSave.name(), productSave.price());
                    return new Reply<>("SUCCESS","Create product name", productSave);
                }
                /*case "UPDATE" -> log.info("Updating product name={}, price={}", null, null);
                case "DELETE" -> log.info("Deleting product");
                case "READ_ALL" -> log.info("Reading all products");
                case "READ_ONE" -> log.info("Reading one product by id");*/
                default -> {
                    log.info("Unknown command type={}", type);
                    return new Reply<>("ERROR","\"Unknown command type=",null);
                }
            }

        };
    }
}
