package br.com.microservice.estoque.usecase.mapper;

import br.com.microservice.estoque.domain.EstoqueProduto;
import br.com.microservice.estoque.dto.EstoqueProdutoDTO;

public class EstoqueProdutoMapper {
    public static EstoqueProdutoDTO mapToDTO(EstoqueProduto estoqueProduto) {
        return new EstoqueProdutoDTO(
                estoqueProduto.getId(),
                estoqueProduto.getSku(),
                estoqueProduto.getQuantidade()
        );
    }
}
