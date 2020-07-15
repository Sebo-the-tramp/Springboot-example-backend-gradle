package it.refill.backend.models.order;

import java.util.Date;

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

@Entity(name="Order")
@Table(name="orders")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date orderData;

    private boolean orderPayed;

    private String orderDetails;

    private boolean orderDelivered;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;    


    public Order() {
    }

    public Order(Date orderData, boolean orderPayed, String orderDetails, boolean orderDelivered, Customer customer) {        
        this.orderData = orderData;
        this.orderPayed = orderPayed;
        this.orderDetails = orderDetails;
        this.orderDelivered = orderDelivered;
        this.customer = customer;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOrderData() {
        return this.orderData;
    }

    public void setOrderData(Date orderData) {
        this.orderData = orderData;
    }

    public boolean isOrderPayed() {
        return this.orderPayed;
    }

    public boolean getOrderPayed() {
        return this.orderPayed;
    }

    public void setOrderPayed(boolean orderPayed) {
        this.orderPayed = orderPayed;
    }

    public String getOrderDetails() {
        return this.orderDetails;
    }

    public void setOrderDetails(String orderDetails) {
        this.orderDetails = orderDetails;
    }

    public boolean isOrderDelivered() {
        return this.orderDelivered;
    }

    public boolean getOrderDelivered() {
        return this.orderDelivered;
    }

    public void setOrderDelivered(boolean orderDelivered) {
        this.orderDelivered = orderDelivered;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Order id(Long id) {
        this.id = id;
        return this;
    }

    public Order orderData(Date orderData) {
        this.orderData = orderData;
        return this;
    }

    public Order orderPayed(boolean orderPayed) {
        this.orderPayed = orderPayed;
        return this;
    }

    public Order orderDetails(String orderDetails) {
        this.orderDetails = orderDetails;
        return this;
    }

    public Order orderDelivered(boolean orderDelivered) {
        this.orderDelivered = orderDelivered;
        return this;
    }

    public Order customer(Customer customer) {
        this.customer = customer;
        return this;
    }

}