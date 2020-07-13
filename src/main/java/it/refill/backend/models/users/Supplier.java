package it.refill.backend.models.users;

import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.lang.Nullable;

import it.refill.backend.models.product.Product;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@Table(name = "suppliers")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    private Long id;

    private String name;
    private String supplierIva;
    private String description;
    private Float supplierLat;
    private Float supplierLong;

    @JsonIgnore  
    @OneToOne(mappedBy = "supplier", cascade = CascadeType.ALL)
    private User user;

    @Nullable
    @JsonIgnoreProperties("supplier") 
    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Product> products;

    public Supplier(){}

    public Supplier(String name, String supplierIva, Float supplierLat, Float supplierLong, User user, String description) {        
        this.name = name;
        this.supplierIva = supplierIva;
        this.supplierLat = supplierLat;
        this.supplierLong = supplierLong;
        this.user = user;
        this.description = description;
    }
    

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSupplierIva() {
        return this.supplierIva;
    }

    public void setSupplierIva(String supplierIva) {
        this.supplierIva = supplierIva;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getSupplierLat() {
        return this.supplierLat;
    }

    public void setSupplierLat(Float supplierLat) {
        this.supplierLat = supplierLat;
    }

    public Float getSupplierLong() {
        return this.supplierLong;
    }

    public void setSupplierLong(Float supplierLong) {
        this.supplierLong = supplierLong;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}