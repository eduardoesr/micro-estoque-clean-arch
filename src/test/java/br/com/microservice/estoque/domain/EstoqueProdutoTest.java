package br.com.microservice.estoque.domain;

import br.com.microservice.estoque.exception.EstoqueProdutoError;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EstoqueProdutoTest {

    @Test
    void criarDeveRetornarEstoqueProdutoValido() {
        EstoqueProduto produto = EstoqueProduto.criar("SKU12", 10);
        assertNull(produto.getId());
        assertEquals("SKU12", produto.getSku());
        assertEquals(10, produto.getQuantidade());
    }

    @Test
    void reconstituirDeveRetornarEstoqueProdutoValido() {
        EstoqueProduto produto = EstoqueProduto.reconstituir("id1", "SKU99", 5);
        assertEquals("id1", produto.getId());
        assertEquals("SKU99", produto.getSku());
        assertEquals(5, produto.getQuantidade());
    }

    @Test
    void criarDeveLancarExcecaoQuandoSkuInvalido() {
        EstoqueProdutoError.EstoqueProdutoIllegalArgumentException ex = assertThrows(
                EstoqueProdutoError.EstoqueProdutoIllegalArgumentException.class,
                () -> EstoqueProduto.criar(" ", 10)
        );
        assertEquals("O campo de SKU não pode ser vazio.", ex.getMessage());

        ex = assertThrows(
                EstoqueProdutoError.EstoqueProdutoIllegalArgumentException.class,
                () -> EstoqueProduto.criar("A", 10)
        );
        assertEquals("O campo de SKU não pode ser vazio.", ex.getMessage());
    }

    @Test
    void criarDeveLancarExcecaoQuandoQuantidadeInvalida() {
        EstoqueProdutoError.EstoqueProdutoIllegalArgumentException ex = assertThrows(
                EstoqueProdutoError.EstoqueProdutoIllegalArgumentException.class,
                () -> EstoqueProduto.criar("SKU12", -1)
        );
        assertEquals("O preço não pode ser abaixo de 0.", ex.getMessage());
    }

    @Test
    void settersDevemAlterarValores() {
        EstoqueProduto produto = EstoqueProduto.criar("SKU12", 10);
        produto.setSku("SKU20");
        produto.setQuantidade(99);
        assertEquals("SKU20", produto.getSku());
        assertEquals(99, produto.getQuantidade());
    }
}