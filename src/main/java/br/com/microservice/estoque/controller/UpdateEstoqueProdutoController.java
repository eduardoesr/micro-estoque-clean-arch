package br.com.microservice.estoque.controller;

import br.com.microservice.estoque.dto.EstoqueProdutoDTO;
import br.com.microservice.estoque.dto.rest_controller.InputReduzirEstoqueProdutoDTO;
import br.com.microservice.estoque.dto.rest_controller.InputUpdateEstoqueProdutoDTO;
import br.com.microservice.estoque.dto.usecase.UpdateEstoqueProdutoDTO;
import br.com.microservice.estoque.usecase.UpdateEstoqueProdutoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("update-estoque-produto")
@Tag(name = "Estoque", description = "Endpoints que modificam, controla, cria e deleta estoque de produtos.")
public class UpdateEstoqueProdutoController {
    final UpdateEstoqueProdutoUseCase useCase;

    public UpdateEstoqueProdutoController(UpdateEstoqueProdutoUseCase useCase) {
        this.useCase = useCase;
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualiza alguns dados de um estoque de produto"
    )
    public ResponseEntity<EstoqueProdutoDTO> update(@PathVariable("id") String id, @Valid @RequestBody InputUpdateEstoqueProdutoDTO input){
        return ResponseEntity.ok(useCase.update(
                id,
                new UpdateEstoqueProdutoDTO(
                        input.sku(),
                        input.quantidade()
                )
        ));
    }

    @PutMapping
    @Operation(
            summary = "Reduz estoque de um produto"
    )
    public ResponseEntity<List<EstoqueProdutoDTO>> reduzir(@Valid @RequestBody InputReduzirEstoqueProdutoDTO input){
        return ResponseEntity.ok(useCase.reduzir(input));
    }
}
