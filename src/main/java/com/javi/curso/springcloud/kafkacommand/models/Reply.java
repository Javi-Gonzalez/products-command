package com.javi.curso.springcloud.kafkacommand.models;

public record Reply <T>(String status, String message, T body) {
}
