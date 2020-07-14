package it.refill.backend.models.list;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.refill.backend.models.users.Customer;

@Entity(name="ProductList")
@Table(name="products_lists")
public class ProductList {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String listName;

    private String listDescription;

    private BigDecimal totalPrice;

    private boolean isCart;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;


    public ProductList() {
    }

    public ProductList(Long id, String listName, String listDescription, BigDecimal totalPrice, boolean isCart, Customer customer) {
        this.id = id;
        this.listName = listName;
        this.listDescription = listDescription;
        this.totalPrice = totalPrice;
        this.isCart = isCart;
        this.customer = customer;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getListName() {
        return this.listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getListDescription() {
        return this.listDescription;
    }

    public void setListDescription(String listDescription) {
        this.listDescription = listDescription;
    }

    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isIsCart() {
        return this.isCart;
    }

    public boolean getIsCart() {
        return this.isCart;
    }

    public void setIsCart(boolean isCart) {
        this.isCart = isCart;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ProductList id(Long id) {
        this.id = id;
        return this;
    }

    public ProductList listName(String listName) {
        this.listName = listName;
        return this;
    }

    public ProductList listDescription(String listDescription) {
        this.listDescription = listDescription;
        return this;
    }

    public ProductList totalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public ProductList isCart(boolean isCart) {
        this.isCart = isCart;
        return this;
    }

    public ProductList customer(Customer customer) {
        this.customer = customer;
        return this;
    }

}