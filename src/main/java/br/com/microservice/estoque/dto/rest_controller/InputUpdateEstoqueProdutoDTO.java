package br.com.microservice.estoque.dto.rest_controller;

public record InputUpdateEstoqueProdutoDTO(
        String sku,
        Integer quantidade
) {
}
