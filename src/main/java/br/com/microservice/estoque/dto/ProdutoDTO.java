package br.com.microservice.estoque.dto;

public record ProdutoDTO (
    String sku,
    Integer quantidade
) {
}