package it.refill.backend.models.list;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import it.refill.backend.models.list.embeddable.ListProductId;
import it.refill.backend.models.product.Product;

@Entity
@Table(name="list_contains_products")
public class ListContainsProducts {
    @EmbeddedId
    private ListProductId id;

    private Float quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("listId")
    private ListProduct list;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    private Product product;


    public ListContainsProducts() {
    }

    public ListContainsProducts(ListProductId id, Float quantity, ListProduct list, Product product) {
        this.id = id;
        this.quantity = quantity;
        this.list = list;
        this.product = product;
    }

    public ListProductId getId() {
        return this.id;
    }

    public void setId(ListProductId id) {
        this.id = id;
    }

    public Float getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public ListProduct getList() {
        return this.list;
    }

    public void setList(ListProduct list) {
        this.list = list;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ListContainsProducts id(ListProductId id) {
        this.id = id;
        return this;
    }

    public ListContainsProducts quantity(Float quantity) {
        this.quantity = quantity;
        return this;
    }

    public ListContainsProducts list(ListProduct list) {
        this.list = list;
        return this;
    }

    public ListContainsProducts product(Product product) {
        this.product = product;
        return this;
    }
    
}