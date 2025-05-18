package br.com.microservice.estoque.usecase;

import br.com.microservice.estoque.domain.EstoqueProduto;
import br.com.microservice.estoque.dto.EstoqueProdutoDTO;
import br.com.microservice.estoque.dto.rest_controller.InputReduzirEstoqueProdutoDTO;
import br.com.microservice.estoque.exception.EstoqueProdutoError;
import br.com.microservice.estoque.gateway.CrudEstoqueProdutoGateway;
import br.com.microservice.estoque.usecase.mapper.EstoqueProdutoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReduzirEstoqueProdutoUseCase {

    private final CrudEstoqueProdutoGateway gateway;

    public ReduzirEstoqueProdutoUseCase(CrudEstoqueProdutoGateway gateway) {
        this.gateway = gateway;
    }

    public List<EstoqueProdutoDTO> update(InputReduzirEstoqueProdutoDTO listaEstoqueProdutoDTO) {
        return listaEstoqueProdutoDTO.produtos().stream().map(produtoDTO -> {
            EstoqueProduto estoqueProduto = gateway.findBySku(produtoDTO.sku())
                    .orElseThrow(() -> new EstoqueProdutoError.EstoqueProdutoNotFoundException("ReduzirEstoqueProdutoUseCase: Sku (" + produtoDTO.sku() + ") do produto não encontrado"));

            if (produtoDTO.quantidade() != null) {
                estoqueProduto.setQuantidade(estoqueProduto.getQuantidade() - produtoDTO.quantidade());
            }

            return EstoqueProdutoMapper.mapToDTO(gateway.save(estoqueProduto));
        }).toList();
    }
}
