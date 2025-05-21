package br.com.microservice.estoque.domain;

import br.com.microservice.estoque.exception.EstoqueProdutoError;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class EstoqueProduto {

    public static final int TAMANHO_SKU_MIN = 2;
    public static final int QUANTIDADE_MIN = 0;

    private final String id;
    private String sku;
    private Integer quantidade;

    private EstoqueProduto(String id, String sku, Integer quantidade) {
        this.id = id;
        this.sku = validaSku(sku);
        this.quantidade = validaQuantidade(quantidade);
    }

    public static EstoqueProduto criar(
            String sku,
            Integer quantidade
    ) {
        return new EstoqueProduto(
                null,
                sku,
                quantidade
        );
    }

    public static EstoqueProduto reconstituir(
            String id,
            String sku,
            Integer quantidade
    ) {
        return new EstoqueProduto(
                id,
                sku,
                quantidade
        );
    }

    private String validaSku(String nome) {
        if (nome.trim().length() < TAMANHO_SKU_MIN) {
            throw new EstoqueProdutoError.EstoqueProdutoIllegalArgumentException("O campo de SKU não pode ser vazio.");
        }
        return nome;
    }

    private Integer validaQuantidade(Integer quantidade) {
        if (quantidade < QUANTIDADE_MIN) {
            throw new EstoqueProdutoError.EstoqueProdutoIllegalArgumentException("O preço não pode ser abaixo de 0.");
        }
        return quantidade;
    }

    public String getId() {
        return id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = validaSku(sku);
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = validaQuantidade(quantidade);
    }
}
