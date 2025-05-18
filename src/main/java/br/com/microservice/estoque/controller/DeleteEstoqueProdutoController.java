package br.com.microservice.estoque.controller;

import br.com.microservice.estoque.usecase.DeleteEstoqueProdutoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("delete-estoque-produto")
@Tag(name = "Estoque", description = "Endpoints que modificam, controla, cria e deleta estoque de produtos.")
public class DeleteEstoqueProdutoController {

    final DeleteEstoqueProdutoUseCase useCase;

    public DeleteEstoqueProdutoController(DeleteEstoqueProdutoUseCase useCase) {
        this.useCase = useCase;
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deleta um estoque de produto"
    )
    public ResponseEntity<Void> delete(@NotBlank @PathVariable("id") String id) {
        useCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
