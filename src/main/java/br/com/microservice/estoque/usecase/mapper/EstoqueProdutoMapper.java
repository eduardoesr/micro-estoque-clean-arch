package br.com.microservice.estoque.usecase.mapper;

import br.com.microservice.estoque.domain.EstoqueProduto;
import br.com.microservice.estoque.dto.EstoqueProdutoDTO;

public class EstoqueProdutoMapper {
    public static EstoqueProduto mapToDomain(EstoqueProdutoDTO dto) {
        return EstoqueProduto.reconstituir(
                dto.id(),
                dto.sku(),
                dto.quantidade()
        );
    }

    public static EstoqueProdutoDTO mapToDTO(EstoqueProduto estoqueProduto) {
        return new EstoqueProdutoDTO(
                estoqueProduto.getId(),
                estoqueProduto.getSku(),
                estoqueProduto.getQuantidade()
        );
    }
}
