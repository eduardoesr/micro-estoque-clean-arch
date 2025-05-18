package br.com.microservice.estoque.usecase;

import br.com.microservice.estoque.domain.EstoqueProduto;
import br.com.microservice.estoque.dto.EstoqueProdutoDTO;
import br.com.microservice.estoque.dto.usecase.UpdateEstoqueProdutoDTO;
import br.com.microservice.estoque.exception.EstoqueProdutoError;
import br.com.microservice.estoque.gateway.CrudEstoqueProdutoGateway;
import br.com.microservice.estoque.usecase.mapper.EstoqueProdutoMapper;
import org.springframework.stereotype.Service;

@Service
public class UpdateEstoqueProdutoUseCase {

    private final CrudEstoqueProdutoGateway gateway;

    public UpdateEstoqueProdutoUseCase(CrudEstoqueProdutoGateway gateway) {
        this.gateway = gateway;
    }

    public EstoqueProdutoDTO update(String id, UpdateEstoqueProdutoDTO estoqueProdutoDTO) {
        EstoqueProduto estoqueProduto = gateway.findById(id)
                .orElseThrow(() -> new EstoqueProdutoError.EstoqueProdutoNotFoundException("UpdateEstoqueProdutoUseCase: Id do produto não encontrado"));

        if(estoqueProdutoDTO.sku()!= null) {
            estoqueProduto.setSku(estoqueProdutoDTO.sku());
        }

        if(estoqueProdutoDTO.quantidade() != null) {
            estoqueProduto.setQuantidade(estoqueProdutoDTO.quantidade());
        }

        return EstoqueProdutoMapper.mapToDTO(gateway.save(estoqueProduto));
    }
}
