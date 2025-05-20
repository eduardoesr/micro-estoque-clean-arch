package br.com.microservice.estoque.dto.rest_controller;

import br.com.microservice.estoque.domain.value_objects.Produto;

import java.util.List;

public record InputReduzirEstoqueProdutoDTO(
        List<Produto> produtos
) {
}