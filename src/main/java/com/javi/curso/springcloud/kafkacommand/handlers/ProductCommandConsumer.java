package com.javi.curso.springcloud.kafkacommand.handlers;


import com.javi.curso.springcloud.kafkacommand.models.Command;
import com.javi.curso.springcloud.kafkacommand.models.Reply;
import com.javi.curso.springcloud.kafkacommand.models.dto.ProductDto;
import com.javi.curso.springcloud.kafkacommand.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

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
    public Function<Message<Command<ProductDto>>, Message<Reply<?>>> handleCommands() {
        return msg -> {
            Command<ProductDto> cmd = msg.getPayload();
            String type = cmd.type() == null ? "" : cmd.type().toUpperCase();
            Reply<ProductDto> reply;
            switch (type) {
                case "CREATE" -> {
                    if(cmd.body() == null) {
                        log.warn("Create empty body");
                        reply = new Reply<>("ERROR","Create Empty body",null);
                    }
                    ProductDto productSave = service.create(cmd.body());

                    log.info("Creating product name={}, price={}", productSave.name(), productSave.price());
                    reply = new Reply<>("SUCCESS","Create product name", productSave);
                }
                /*case "UPDATE" -> log.info("Updating product name={}, price={}", null, null);
                case "DELETE" -> log.info("Deleting product");
                case "READ_ALL" -> log.info("Reading all products");
                case "READ_ONE" -> log.info("Reading one product by id");*/
                default -> {
                    log.info("Unknown command type={}", type);
                    reply = new Reply<>("ERROR","\"Unknown command type=",null);
                }
            }
            String correlationId = msg.getHeaders().get("correlationId", String.class);
            log.info("Recibiendo CorrelationId={}", correlationId);
            MessageBuilder<Reply<?>> out = MessageBuilder.withPayload(reply);
            if (correlationId != null) {
                out.setHeader("correlationId", correlationId);
            }
            return  out.build();
        };
    }
}
