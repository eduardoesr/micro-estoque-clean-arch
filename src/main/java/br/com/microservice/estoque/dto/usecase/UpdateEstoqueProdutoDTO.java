package br.com.microservice.estoque.dto.usecase;

public record UpdateEstoqueProdutoDTO(
        String sku,
        Integer quantidade
) {
}
