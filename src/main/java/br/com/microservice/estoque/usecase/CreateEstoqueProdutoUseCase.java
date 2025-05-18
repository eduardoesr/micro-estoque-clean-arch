package br.com.microservice.estoque.usecase;

import br.com.microservice.estoque.domain.EstoqueProduto;
import br.com.microservice.estoque.dto.EstoqueProdutoDTO;
import br.com.microservice.estoque.dto.usecase.CreateEstoqueProdutoDTO;
import br.com.microservice.estoque.exception.EstoqueProdutoError;
import br.com.microservice.estoque.gateway.CrudEstoqueProdutoGateway;
import br.com.microservice.estoque.usecase.mapper.EstoqueProdutoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CreateEstoqueProdutoUseCase {

    private final CrudEstoqueProdutoGateway gateway;

    public CreateEstoqueProdutoUseCase(CrudEstoqueProdutoGateway gateway) {
        this.gateway = gateway;
    }

    public EstoqueProdutoDTO create(CreateEstoqueProdutoDTO estoqueProdutoDTO) {
        var opEstoqueProduto = gateway.findBySku(estoqueProdutoDTO.sku());

        if(opEstoqueProduto.isPresent()) {
//            log.info(
//                    "Não foi possivel salvar um produto, SKU já utilizada"
//            );
            throw new EstoqueProdutoError.EstoqueProdutoAlreadyExistsException("Esse SKU já foi utilizado."); //TODO implementar erros personalizados
        }

        var estoqueProduto = EstoqueProduto.criar(
                estoqueProdutoDTO.sku(),
                estoqueProdutoDTO.quantidade()
        );

        return EstoqueProdutoMapper.mapToDTO(gateway.save(estoqueProduto));
    }
}
