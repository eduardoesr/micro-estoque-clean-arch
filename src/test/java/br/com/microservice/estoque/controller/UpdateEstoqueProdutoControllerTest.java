package br.com.microservice.estoque.controller;

import br.com.microservice.estoque.domain.EstoqueProduto;
import br.com.microservice.estoque.dto.EstoqueProdutoDTO;
import br.com.microservice.estoque.dto.rest_controller.InputReduzirEstoqueProdutoDTO;
import br.com.microservice.estoque.dto.rest_controller.InputUpdateEstoqueProdutoDTO;
import br.com.microservice.estoque.gateway.CrudEstoqueProdutoGateway;
import br.com.microservice.estoque.usecase.UpdateEstoqueProdutoUseCase;
import br.com.microservice.estoque.usecase.mapper.EstoqueProdutoMapper;
import br.com.microservice.estoque.utils.EstoqueProdutoMockData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@WebMvcTest(UpdateEstoqueProdutoController.class)
@AutoConfigureMockMvc
@Import(UpdateEstoqueProdutoUseCase.class)
public class UpdateEstoqueProdutoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CrudEstoqueProdutoGateway gateway;

    @Autowired
    ObjectMapper mapper;

    @Test
    void updateEstoqueProdutoSucesss() throws Exception {
        InputUpdateEstoqueProdutoDTO input = EstoqueProdutoMockData.validInputUpdateEstoqueProdutoDTO();
        EstoqueProduto mock = EstoqueProdutoMockData.validEstoqueProduto();

        Mockito.when(gateway.findById(mock.getId())).thenReturn(Optional.of(mock));
        Mockito.when(gateway.save(Mockito.any(EstoqueProduto.class))).thenAnswer(invocationOnMock -> {
            return invocationOnMock.getArgument(0);
        });

        mock.setSku(input.sku());
        mock.setQuantidade(input.quantidade());

        mockMvc.perform(
                MockMvcRequestBuilders.put("/update-estoque-produto/{id}", mock.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(EstoqueProdutoMapper.mapToDTO(mock))));
    }

    @Test
    void reduzirDeveReduzirEstoqueDeTodosOsProdutos() {
        // Arrange
        InputReduzirEstoqueProdutoDTO input = EstoqueProdutoMockData.validInputReduzirEstoqueProdutoDTO();
        List<EstoqueProdutoDTO> expected = input.produtos().stream().map(produtoInput -> {
            EstoqueProduto mock = EstoqueProdutoMockData.validEstoqueProduto();
            mock.setSku(produtoInput.sku());
            mock.setQuantidade(mock.getQuantidade() - produtoInput.quantidade());
            Mockito.when(gateway.findBySku(produtoInput.sku())).thenReturn(Optional.of(mock));
            Mockito.when(gateway.save(Mockito.any(EstoqueProduto.class))).thenAnswer(inv -> inv.getArgument(0));
            return EstoqueProdutoMapper.mapToDTO(mock);
        }).toList();

        UpdateEstoqueProdutoUseCase useCase = new UpdateEstoqueProdutoUseCase(gateway);

        // Act
        List<EstoqueProdutoDTO> result = useCase.reduzir(input);
    }

    @Test
    void updateEstoqueProdutoWithEstoqueProdutoNotFoundException() throws Exception {
        InputUpdateEstoqueProdutoDTO input = EstoqueProdutoMockData.validInputUpdateEstoqueProdutoDTO();
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/update-estoque-produto/{id}", UUID.randomUUID().toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.message")
                        .value("UpdateEstoqueProdutoUseCase: Id do produto não encontrado"));
    }
}
