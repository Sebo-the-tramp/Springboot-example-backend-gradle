package it.refill.backend.models.list.embeddable;

import java.io.Serializable;

import javax.persistence.Column;

public class ListProductId implements Serializable {
    
    /**
     * class made in order to provide a composite Id for the list     
     * 
     * @author Sebastian Cavada
     */

    private static final long serialVersionUID = 1L;

    @Column(name = "list_id")
    private Long listId;

    @Column(name="product_id")
    private Long productId;


    public ListProductId() {
    }

    public ListProductId(Long listId, Long productId) {
        this.listId = listId;
        this.productId = productId;
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

    public ListProductId listId(Long listId) {
        this.listId = listId;
        return this;
    }

    public ListProductId productId(Long productId) {
        this.productId = productId;
        return this;
    }

}