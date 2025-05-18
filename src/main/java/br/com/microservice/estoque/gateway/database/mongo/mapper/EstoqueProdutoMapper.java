package br.com.microservice.estoque.gateway.database.mongo.mapper;

import br.com.microservice.estoque.domain.EstoqueProduto;
import br.com.microservice.estoque.gateway.database.mongo.entity.EstoqueProdutoEntity;

public class EstoqueProdutoMapper {
    public static EstoqueProduto mapToDomain(EstoqueProdutoEntity dto) {
        return EstoqueProduto.reconstituir(
                dto.getId(),
                dto.getSku(),
                dto.getQuantidade()
        );
    }

    public static EstoqueProdutoEntity mapToEntity(EstoqueProduto domain) {
        return new EstoqueProdutoEntity(
                domain.getId(),
                domain.getSku(),
                domain.getQuantidade()
        );
    }
}
