package br.com.microservice.estoque.controller;

import br.com.microservice.estoque.dto.EstoqueProdutoDTO;
import br.com.microservice.estoque.usecase.ReadEstoqueProdutoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estoque-produto")
@Tag(name = "Estoque", description = "Endpoints que modificam, controla, cria e deleta estoque de produtos.")
public class ReadEstoqueProdutoController {
    final ReadEstoqueProdutoUseCase useCase;

    public ReadEstoqueProdutoController(ReadEstoqueProdutoUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Encontrar estoque de produto."
    )
    public ResponseEntity<EstoqueProdutoDTO> findById(@PathVariable("id") String id) {
        return ResponseEntity.ok(useCase.find(id));
    }

    @GetMapping
    @Operation(
            summary = "Listar todos os estoques de produto."
    )
    public ResponseEntity<List<EstoqueProdutoDTO>> findAll(
            @PageableDefault(page = 0, size = 10, sort = "id") Pageable page
    ) {
        return ResponseEntity.ok(useCase.findAll(page));
    }
}
