package br.com.microservice.estoque.controller;

import br.com.microservice.estoque.dto.EstoqueProdutoDTO;
import br.com.microservice.estoque.dto.rest_controller.InputReduzirEstoqueProdutoDTO;
import br.com.microservice.estoque.dto.usecase.UpdateEstoqueProdutoDTO;
import br.com.microservice.estoque.usecase.ReduzirEstoqueProdutoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reduzir-estoque-produto")
@Tag(name = "Estoque", description = "Endpoints que modificam, controla, cria e deleta estoque de produtos.")
public class ReduzirEstoqueProdutoController {
    final ReduzirEstoqueProdutoUseCase useCase;

    public ReduzirEstoqueProdutoController(ReduzirEstoqueProdutoUseCase useCase) {
        this.useCase = useCase;
    }

    @PutMapping
    @Operation(
            summary = "Reduz estoque de um produto"
    )
    public ResponseEntity<List<EstoqueProdutoDTO>> update(@Valid @RequestBody InputReduzirEstoqueProdutoDTO input){
        return ResponseEntity.ok(useCase.update(input));
    }
}
