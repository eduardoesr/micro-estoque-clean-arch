package br.com.microservice.estoque.controller;

import br.com.microservice.estoque.domain.EstoqueProduto;
import br.com.microservice.estoque.dto.EstoqueProdutoDTO;
import br.com.microservice.estoque.gateway.CrudEstoqueProdutoGateway;
import br.com.microservice.estoque.usecase.ReadEstoqueProdutoUseCase;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(ReadEstoqueProdutoController.class)
@AutoConfigureMockMvc
@Import(ReadEstoqueProdutoUseCase.class)
public class ReadEstoqueProdutoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CrudEstoqueProdutoGateway gateway;

    @Autowired
    ObjectMapper mapper;

    @Test
    void readFindProdutoSucess() throws Exception {
        EstoqueProduto mock = EstoqueProdutoMockData.validEstoqueProduto();

        when(gateway.findById(any()))
                .thenReturn(Optional.of(mock));

        String resultExpectedJson = mapper.writeValueAsString(EstoqueProdutoMapper.mapToDTO(mock));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/estoque-produto/{id}", UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(resultExpectedJson));
    }

    @Test
    void readFindAllSucess() throws Exception {
        EstoqueProduto mock = EstoqueProdutoMockData.validEstoqueProduto();

        when(gateway.findAll(any()))
                .thenReturn(List.of(mock));

        String resultExpectedJson = mapper.writeValueAsString(List.of(EstoqueProdutoMapper.mapToDTO(mock)));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/estoque-produto")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(resultExpectedJson));
    }

    @Test
    void readFindEstoqueProdutoWithEstoqueProdutoNotFoundException() throws Exception {

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/estoque-produto/{id}", UUID.randomUUID().toString())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Produto não encontrado"));
    }

    @Test
    void readFindAllEmptyEstoqueProdutos() throws Exception {

        String resultExpectedJson = mapper.writeValueAsString(List.of());

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/estoque-produto")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(resultExpectedJson));
    }
}
