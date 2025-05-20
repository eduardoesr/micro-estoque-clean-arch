package br.com.microservice.estoque.domain.value_objects;

public record Produto(
    String sku,
    Integer quantidade
) {
}