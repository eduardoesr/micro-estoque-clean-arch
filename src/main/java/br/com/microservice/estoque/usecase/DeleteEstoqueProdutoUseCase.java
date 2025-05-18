package br.com.microservice.estoque.usecase;

import br.com.microservice.estoque.domain.EstoqueProduto;
import br.com.microservice.estoque.exception.EstoqueProdutoError;
import br.com.microservice.estoque.gateway.CrudEstoqueProdutoGateway;
import org.springframework.stereotype.Service;

@Service
public class DeleteEstoqueProdutoUseCase {

    private final CrudEstoqueProdutoGateway gateway;

    public DeleteEstoqueProdutoUseCase(CrudEstoqueProdutoGateway gateway) {
        this.gateway = gateway;
    }

    public void delete(String id) {
        EstoqueProduto estoqueProduto = gateway.findById(id).orElseThrow(() -> new EstoqueProdutoError.EstoqueProdutoNotFoundException("Produto não encontrado."));
        gateway.deleteById(estoqueProduto.getId());
    }
}
