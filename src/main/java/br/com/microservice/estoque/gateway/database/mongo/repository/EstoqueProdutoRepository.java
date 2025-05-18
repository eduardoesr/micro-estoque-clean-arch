package br.com.microservice.estoque.gateway.database.mongo.repository;

import br.com.microservice.estoque.gateway.database.mongo.entity.EstoqueProdutoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EstoqueProdutoRepository extends MongoRepository<EstoqueProdutoEntity, String> {
    Optional<EstoqueProdutoEntity> findBySku(String sku);
}
