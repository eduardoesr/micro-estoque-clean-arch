package br.com.microservice.estoque.utils;

import br.com.microservice.estoque.domain.EstoqueProduto;
import br.com.microservice.estoque.domain.value_objects.Produto;
import br.com.microservice.estoque.dto.EstoqueProdutoDTO;
import br.com.microservice.estoque.dto.rest_controller.InputCreateEstoqueProdutoDTO;
import br.com.microservice.estoque.dto.rest_controller.InputReduzirEstoqueProdutoDTO;
import br.com.microservice.estoque.dto.rest_controller.InputUpdateEstoqueProdutoDTO;
import br.com.microservice.estoque.gateway.database.mongo.entity.EstoqueProdutoEntity;
import br.com.microservice.estoque.gateway.database.mongo.mapper.EstoqueProdutoMapper;

import java.util.List;
import java.util.UUID;

public class EstoqueProdutoMockData {

    // SKUs válidos
    private static final String[] VALID_SKUS = {
            "MTP03BR/A",
            "MK893BZ/A"
    };

    // DTO válido básico
    public static InputCreateEstoqueProdutoDTO validInput() {
        return new InputCreateEstoqueProdutoDTO(
                VALID_SKUS[0],
                10
        );
    }

    public static EstoqueProduto validEstoqueProduto() {
        InputCreateEstoqueProdutoDTO valid = validInput();
        return EstoqueProduto.reconstituir(
                UUID.randomUUID().toString(),
                valid.sku(),
                valid.quantidade()
        );
    }

    // Variações de DTOs válidos para diferentes cenários
    public static InputCreateEstoqueProdutoDTO validInputWithDifferentSku() {
        return new InputCreateEstoqueProdutoDTO(
                VALID_SKUS[1],
                15
        );
    }

    public static InputUpdateEstoqueProdutoDTO validInputUpdateEstoqueProdutoDTO() {
        InputCreateEstoqueProdutoDTO valid = validInputWithDifferentSku();
        return new InputUpdateEstoqueProdutoDTO(
                valid.sku(),
                valid.quantidade()
        );
    }

    public static EstoqueProdutoEntity validEstoqueProdutoEntity() {
        EstoqueProduto produtoDTO = validEstoqueProduto();
        return EstoqueProdutoMapper.mapToEntity(produtoDTO);
    }

    public static InputReduzirEstoqueProdutoDTO validInputReduzirEstoqueProdutoDTO() {
        return new InputReduzirEstoqueProdutoDTO(
                List.of(
                        new Produto(VALID_SKUS[0], 5)
                )
        );
    }

    public static EstoqueProdutoDTO validEstoqueProdutoDTO() {
        return new EstoqueProdutoDTO(
                validEstoqueProduto().getId(),
                validEstoqueProduto().getSku(),
                validEstoqueProduto().getQuantidade()
        );
    }
}