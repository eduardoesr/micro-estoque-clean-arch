package br.com.microservice.estoque.dto.rest_controller;

public record InputCreateEstoqueProdutoDTO(
        String sku,
        Integer quantidade
) {
}