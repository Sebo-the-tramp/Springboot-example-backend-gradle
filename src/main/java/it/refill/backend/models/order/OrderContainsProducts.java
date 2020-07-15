package it.refill.backend.models.order;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import it.refill.backend.models.product.Product;

@Entity
@Table(name = "order_contains_products")
public class OrderContainsProducts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal price;

    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    private Product product;

    public OrderContainsProducts() {
    }

    public OrderContainsProducts(Long id, BigDecimal price, Integer quantity, Order order, Product product) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.order = order;
        this.product = product;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public OrderContainsProducts id(Long id) {
        this.id = id;
        return this;
    }

    public OrderContainsProducts price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public OrderContainsProducts quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrderContainsProducts order(Order order) {
        this.order = order;
        return this;
    }

    public OrderContainsProducts product(Product product) {
        this.product = product;
        return this;
    }

}