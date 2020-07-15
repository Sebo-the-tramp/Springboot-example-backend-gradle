package it.refill.backend.payload.request.list;

import javax.validation.constraints.NotBlank;

public class ProductPayload {
    @NotBlank
    private Long listId;

    @NotBlank
    private Long productId;

    @NotBlank 
    private Integer quantity;


    public ProductPayload() {
    }

    public ProductPayload(Long listId, Long productId, Integer quantity) {
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

    public ProductPayload listId(Long listId) {
        this.listId = listId;
        return this;
    }

    public ProductPayload productId(Long productId) {
        this.productId = productId;
        return this;
    }

    public ProductPayload quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

}