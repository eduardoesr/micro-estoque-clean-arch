package br.com.microservice.estoque.controller;

import br.com.microservice.estoque.domain.EstoqueProduto;
import br.com.microservice.estoque.dto.rest_controller.InputCreateEstoqueProdutoDTO;
import br.com.microservice.estoque.gateway.CrudEstoqueProdutoGateway;
import br.com.microservice.estoque.usecase.CreateEstoqueProdutoUseCase;
import br.com.microservice.estoque.usecase.mapper.EstoqueProdutoMapper;
import br.com.microservice.estoque.utils.EstoqueProdutoMockData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CreateEstoqueProdutoController.class)
@AutoConfigureMockMvc
@Import(CreateEstoqueProdutoUseCase.class)
class CreateEstoqueProdutoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CrudEstoqueProdutoGateway gateway;

    @Autowired
    ObjectMapper mapper;

    @Test
    void createSucess() throws Exception {
        InputCreateEstoqueProdutoDTO input = EstoqueProdutoMockData.validInput();
        EstoqueProduto produtoMock = EstoqueProdutoMockData.validEstoqueProduto();

        when(gateway.save(any()))
                .thenReturn(produtoMock);

        String resultExpectedJson = mapper.writeValueAsString(EstoqueProdutoMapper.mapToDTO(produtoMock));

        mockMvc.perform(
                    post("/create-estoque-produto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input))
                ).andExpect(status().isCreated())
                    .andExpect(MockMvcResultMatchers.content().json(resultExpectedJson));
    }

    @Test
    void createWithEstoqueProdutoAlreadyExistsException() throws Exception {
        InputCreateEstoqueProdutoDTO input = EstoqueProdutoMockData.validInput();
        EstoqueProduto produtoMock = EstoqueProdutoMockData.validEstoqueProduto();

        when(gateway.findBySku(any()))
                .thenReturn(Optional.of(produtoMock));

        mockMvc.perform(
                        post("/create-estoque-produto")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(input))
                ).andExpect(status().isConflict())
                .andExpect(jsonPath("$.message", is("Esse SKU já foi utilizado.")));
    }
}