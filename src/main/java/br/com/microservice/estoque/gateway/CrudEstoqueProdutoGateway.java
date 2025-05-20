package br.com.microservice.estoque.gateway;

import br.com.microservice.estoque.domain.EstoqueProduto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CrudEstoqueProdutoGateway {
    Optional<EstoqueProduto> findBySku(String sku);
    Optional<EstoqueProduto> findById(String id);
    Boolean existId(String id);
    EstoqueProduto save(EstoqueProduto estoqueProduto);
    void deleteById(String id);
    List<EstoqueProduto> findAll(Pageable page);
}
