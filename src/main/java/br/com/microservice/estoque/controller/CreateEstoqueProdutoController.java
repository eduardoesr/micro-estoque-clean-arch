package br.com.microservice.estoque.controller;

import br.com.microservice.estoque.dto.EstoqueProdutoDTO;
import br.com.microservice.estoque.dto.rest_controller.InputCreateEstoqueProdutoDTO;
import br.com.microservice.estoque.dto.usecase.CreateEstoqueProdutoDTO;
import br.com.microservice.estoque.usecase.CreateEstoqueProdutoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/create-estoque-produto")
@Tag(name = "Estoque", description = "Endpoints que modificam, controla, cria e deleta estoque de produtos.")
public class CreateEstoqueProdutoController {
    final CreateEstoqueProdutoUseCase useCase;

    public CreateEstoqueProdutoController(CreateEstoqueProdutoUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    @Operation(
            summary = "Cria um novo estoque de produto"
    )
    public ResponseEntity<EstoqueProdutoDTO> create(@Valid @RequestBody InputCreateEstoqueProdutoDTO input) {
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(useCase.create(
                new CreateEstoqueProdutoDTO(
                    input.sku(),
                    input.quantidade()
                )
        ));
    }
}
