package br.com.microservice.estoque.dto;

public record EstoqueProdutoDTO(
        String id,
        String sku,
        Integer quantidade
) {
}
