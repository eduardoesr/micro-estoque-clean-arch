package br.com.microservice.estoque.gateway.database.mongo;

import br.com.microservice.estoque.domain.EstoqueProduto;
import br.com.microservice.estoque.gateway.database.mongo.entity.EstoqueProdutoEntity;
import br.com.microservice.estoque.gateway.database.mongo.mapper.EstoqueProdutoMapper;
import br.com.microservice.estoque.gateway.database.mongo.repository.EstoqueProdutoRepository;
import br.com.microservice.estoque.gateway.exception.mongo.EstoqueProdutoMongoError;
import br.com.microservice.estoque.utils.EstoqueProdutoMockData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.AssertionErrors;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@ExtendWith(MockitoExtension.class)
class EstoqueProdutoMongoGatewayTest {

    @Mock
    EstoqueProdutoRepository repository;

    @InjectMocks
    EstoqueProdutoMongoGateway gateway;

    @Test
    void findBySku() {
        EstoqueProduto mock = EstoqueProdutoMockData.validEstoqueProduto();

        Mockito.when(repository.findBySku(Mockito.any(String.class)))
                .thenReturn(Optional.of(EstoqueProdutoMapper.mapToEntity(mock)));

        var opEstoqueProduto = gateway.findBySku(mock.getSku());

        assertTrue("Verifica função findBySku do gateway mongo", opEstoqueProduto.isPresent());
        AssertionErrors.assertEquals(
                "Verifica resultado retornado",
                mock.getId(),
                opEstoqueProduto.get().getId()
        );
        verify(repository).findBySku(mock.getSku());
    }

    @Test
    void findBySkyNull() {
        Assertions.assertThrows(
                EstoqueProdutoMongoError.EstoqueProdutoInvalidArgumentException.class,
                () -> gateway.findBySku(null)
        );
    }

    @Test
    void findBySkuWithRuntimeException() {
        Mockito.when(repository.findBySku(any())).thenThrow(new RuntimeException());

        Assertions.assertThrows(
                EstoqueProdutoMongoError.EstoqueProdutoPersistenceException.class,
                () -> gateway.findBySku(EstoqueProdutoMockData.validInput().sku())
        );
    }

    @Test
    void findById() {
        EstoqueProduto mock = EstoqueProdutoMockData.validEstoqueProduto();

        Mockito.when(repository.findById(Mockito.any(String.class)))
                .thenReturn(Optional.of(EstoqueProdutoMapper.mapToEntity(mock)));

        var opEstoqueProduto = gateway.findById(mock.getId());

        assertTrue("Verifica função findById do gateway mongo", opEstoqueProduto.isPresent());
        AssertionErrors.assertEquals(
                "Verifica resultado retornado",
                mock.getId(),
                opEstoqueProduto.get().getId()
        );
        verify(repository).findById(mock.getId());
    }

    @Test
    void findByIdNull() {
        Assertions.assertThrows(
                EstoqueProdutoMongoError.EstoqueProdutoInvalidArgumentException.class,
                () -> gateway.findById(null)
        );
    }

    @Test
    void findByIdWithRuntimeException() {
        Mockito.when(repository.findById(any())).thenThrow(new RuntimeException());
        Assertions.assertThrows(
                EstoqueProdutoMongoError.EstoqueProdutoPersistenceException.class,
                () -> gateway.findById(UUID.randomUUID().toString())
        );
    }

    @Test
    void existId() {
        String id = UUID.randomUUID().toString();
        Mockito.when(repository.existsById(id)).thenReturn(true);
        assertTrue("Verifica se existe id", gateway.existId(id));
    }

    @Test
    void existIdWithRuntimeException() {
        String id = UUID.randomUUID().toString();
        Mockito.when(repository.existsById(id)).thenThrow(new RuntimeException());

        Assertions.assertThrows(
                EstoqueProdutoMongoError.EstoqueProdutoPersistenceException.class,
                () -> gateway.existId(id)
        );
    }

    @Test
    void findAll() {
        Pageable pageable = Pageable.ofSize(10);
        List<EstoqueProdutoEntity> mockResults = List.of(
                EstoqueProdutoMockData.validEstoqueProdutoEntity(),
                EstoqueProdutoMockData.validEstoqueProdutoEntity()
        );
        Mockito.when(repository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(
                mockResults,
                PageRequest.of(0, 10),
                mockResults.size()
        ));

        var result = gateway.findAll(pageable);

        AssertionErrors.assertEquals(
                "Verificando retorno esperado",
                result.size(),
                mockResults.size()
        );

        result.forEach(e -> {
            int index = result.indexOf(e);
            AssertionErrors.assertEquals(
                    "Index: " +index+". Verificando id: " + e.getId() + " dos produtos do resultado",
                    e.getId(),
                    mockResults.get(index).getId()
            );
        });
    }

    @Test
    void save() {
        EstoqueProduto mock = EstoqueProdutoMockData.validEstoqueProduto();

        Mockito.when(repository.save(any(EstoqueProdutoEntity.class)))
                .thenAnswer(i -> i.getArgument(0));

        EstoqueProduto produto = gateway.save(mock);

        AssertionErrors.assertEquals(
                "Verificando id do resultado com o esperado",
                produto.getId(),
                mock.getId()
        );
    }

    @Test
    void saveEstoqueProdutoNull() {
        Assertions.assertThrows(
                EstoqueProdutoMongoError.EstoqueProdutoInvalidArgumentException.class,
                () -> gateway.save(null)
        );
    }

    @Test
    void saveWithDataIntegrityViolationException() {
        Mockito.when(repository.save(any()))
                        .thenThrow(new DataIntegrityViolationException("Error Test"));

        Assertions.assertThrows(
                EstoqueProdutoMongoError.EstoqueProdutoConflictException.class,
                () -> gateway.save(EstoqueProdutoMockData.validEstoqueProduto())
        );
    }

    @Test
    void saveWithException() {
        Mockito.when(repository.save(any()))
                .thenThrow(new RuntimeException());

        Assertions.assertThrows(
                EstoqueProdutoMongoError.EstoqueProdutoPersistenceException.class,
                () -> gateway.save(EstoqueProdutoMockData.validEstoqueProduto())
        );
    }

    @Test
    void deleteById() {
        String id = UUID.randomUUID().toString();

        Mockito.when(repository.existsById(id))
                .thenReturn(true);

        gateway.deleteById(id);
        Mockito.verify(repository).deleteById(any(String.class));

    }

    @Test
    void deleteByIdNull() {
        Assertions.assertThrows(
                EstoqueProdutoMongoError.EstoqueProdutoNotFoundException.class,
                () -> gateway.deleteById(null)
        );
    }

    @Test
    void deleteByIdWithException() {
        Mockito.when(repository.existsById(any(String.class)))
                        .thenReturn(true);
        Mockito.doThrow(new RuntimeException()).when(repository).deleteById(any());
        Assertions.assertThrows(
                EstoqueProdutoMongoError.EstoqueProdutoPersistenceException.class,
                () -> gateway.deleteById(UUID.randomUUID().toString())
        );
    }
}