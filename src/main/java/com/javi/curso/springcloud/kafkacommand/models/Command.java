package com.javi.curso.springcloud.kafkacommand.models;

public record Command<T>(String type, Long id, T body) {
}
