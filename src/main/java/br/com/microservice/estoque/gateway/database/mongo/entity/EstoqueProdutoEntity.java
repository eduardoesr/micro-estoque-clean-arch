package br.com.microservice.estoque.gateway.database.mongo.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "estoque")
@RequiredArgsConstructor
public class EstoqueProdutoEntity {
    @Id
    private String id;
    @Indexed(unique = true)
    private String sku;
    private Integer quantidade;

    public EstoqueProdutoEntity(String id, String sku, Integer quantidade) {
        this.id = id;
        this.sku = sku;
        this.quantidade = quantidade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}