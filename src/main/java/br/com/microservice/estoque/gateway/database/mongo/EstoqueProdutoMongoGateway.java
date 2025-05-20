package br.com.microservice.estoque.gateway.database.mongo;

import br.com.microservice.estoque.domain.EstoqueProduto;
import br.com.microservice.estoque.gateway.CrudEstoqueProdutoGateway;
import br.com.microservice.estoque.gateway.database.mongo.entity.EstoqueProdutoEntity;
import br.com.microservice.estoque.gateway.database.mongo.mapper.EstoqueProdutoMapper;
import br.com.microservice.estoque.gateway.database.mongo.repository.EstoqueProdutoRepository;
import br.com.microservice.estoque.gateway.exception.mongo.EstoqueProdutoMongoError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class EstoqueProdutoMongoGateway implements CrudEstoqueProdutoGateway {

    private final EstoqueProdutoRepository repository;

    public EstoqueProdutoMongoGateway(EstoqueProdutoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<EstoqueProduto> findBySku(String sku) {

        if (sku == null || sku.isBlank()) {
            throw new EstoqueProdutoMongoError.EstoqueProdutoInvalidArgumentException("O SKU não pode ser vazio ou nulo.");
        }

        try {
            Optional<EstoqueProdutoEntity> entity = repository.findBySku(sku);
            return entity.map(EstoqueProdutoMapper::mapToDomain);

        } catch (Exception e) {
//            log.error("Falha ao buscar produto por SKU: {}", sku, e);
            throw new EstoqueProdutoMongoError.EstoqueProdutoPersistenceException("Erro ao acessar o banco de dados.", e);
        }
    }

    @Override
    public Optional<EstoqueProduto> findById(String id) {
        if (id == null || id.isBlank()) {
            throw new EstoqueProdutoMongoError.EstoqueProdutoInvalidArgumentException("Id do produto inválido.");
        }

        try {
            Optional<EstoqueProdutoEntity> entity = repository.findById(id);
            return entity.map(EstoqueProdutoMapper::mapToDomain);
        } catch (Exception e) {
//            log.error("Falha ao buscar produto por ID: {}", id, e);
            throw new EstoqueProdutoMongoError.EstoqueProdutoPersistenceException("Erro ao acessar o banco de dados.", e);
        }
    }

    @Override
    public Boolean existId(String id) {
        try {
            return repository.existsById(id);
        } catch (Exception e) {
//            log.error("Falha ao verificar existência do produto com ID: {}", id, e);
            throw new EstoqueProdutoMongoError.EstoqueProdutoPersistenceException("Erro ao acessar o banco de dados.", e);
        }
    }

    @Override
    public EstoqueProduto save(EstoqueProduto produto) {
        if (produto == null) {
            throw new EstoqueProdutoMongoError.EstoqueProdutoInvalidArgumentException("O produto não pode ser nulo.");
        }
        try {
            EstoqueProdutoEntity entity = EstoqueProdutoMapper.mapToEntity(produto);
            EstoqueProdutoEntity savedEntity = repository.save(entity);
            return EstoqueProdutoMapper.mapToDomain(savedEntity);
        } catch (DataIntegrityViolationException e) {
//            log.error("Conflito ao salvar produto: {}", produto.getSku(), e);
            throw new EstoqueProdutoMongoError.EstoqueProdutoConflictException("EstoqueProduto já cadastrado com este SKU.");
        } catch (Exception e) {
//            log.error("Falha ao salvar produto.", e);
            throw new EstoqueProdutoMongoError.EstoqueProdutoPersistenceException("Erro ao persistir produto.", e);
        }
    }

    @Override
    public void deleteById(String id) {

        if (!repository.existsById(id)) {
            throw new EstoqueProdutoMongoError.EstoqueProdutoNotFoundException("EstoqueProduto não encontrado para exclusão.");
        }

        try {
            repository.deleteById(id);
        } catch (Exception e) {
//            log.error("Falha ao excluir produto com ID: {}", id, e);
            throw new EstoqueProdutoMongoError.EstoqueProdutoPersistenceException("Erro ao excluir produto.", e);
        }
    }

    @Override
    public List<EstoqueProduto> findAll(Pageable page) {
        return repository.findAll(page).map(EstoqueProdutoMapper::mapToDomain).stream().toList();
    }
}