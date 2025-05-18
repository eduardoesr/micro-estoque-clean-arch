package br.com.microservice.estoque.usecase;

import br.com.microservice.estoque.domain.EstoqueProduto;
import br.com.microservice.estoque.dto.EstoqueProdutoDTO;
import br.com.microservice.estoque.exception.EstoqueProdutoError;
import br.com.microservice.estoque.gateway.CrudEstoqueProdutoGateway;
import br.com.microservice.estoque.usecase.mapper.EstoqueProdutoMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadEstoqueProdutoUseCase {
    private final CrudEstoqueProdutoGateway gateway;

    public ReadEstoqueProdutoUseCase(CrudEstoqueProdutoGateway gateway) {
        this.gateway = gateway;
    }

    public EstoqueProdutoDTO find(String id) {
        System.out.printf("AQUI - %s\n", id);
        EstoqueProduto estoqueProduto = gateway.findById(id).orElseThrow(
                () -> new EstoqueProdutoError.EstoqueProdutoNotFoundException("Produto não encontrado"));
        System.out.printf("AQUI 2 - %s\n", estoqueProduto);
        System.out.printf("AQUI 3 - %s\n", estoqueProduto.getQuantidade());
        return EstoqueProdutoMapper.mapToDTO(estoqueProduto);
    }

    public List<EstoqueProdutoDTO> findAll(Pageable page) {
        List<EstoqueProduto> estoques = gateway.findAll(page);
        return estoques.stream().map(EstoqueProdutoMapper::mapToDTO).toList();
    }
}
