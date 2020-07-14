package it.refill.backend.payload.request.list;

import javax.validation.constraints.NotBlank;

public class ProductList {
    @NotBlank
    private Long listId;

    @NotBlank
    private Long productId;

    @NotBlank 
    private Integer quantity;


    public ProductList() {
    }

    public ProductList(Long listId, Long productId, Integer quantity) {
        this.listId = listId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getListId() {
        return this.listId;
    }

    public void setListId(Long listId) {
        this.listId = listId;
    }

    public Long getProductId() {
        return this.productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ProductList listId(Long listId) {
        this.listId = listId;
        return this;
    }

    public ProductList productId(Long productId) {
        this.productId = productId;
        return this;
    }

    public ProductList quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

}