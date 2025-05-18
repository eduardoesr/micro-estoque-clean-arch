package br.com.microservice.estoque.dto.usecase;

public record CreateEstoqueProdutoDTO(
        String sku,
        Integer quantidade
) {
}
