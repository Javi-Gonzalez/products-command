package com.javi.curso.springcloud.kafka.command.models;

public record Command<T>(CommandType type, Long id, T body) {
}
