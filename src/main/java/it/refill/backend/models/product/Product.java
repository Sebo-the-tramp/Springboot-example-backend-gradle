package it.refill.backend.models.product;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import it.refill.backend.views.View;
import it.refill.backend.models.users.Supplier;

@Entity(name="Product")
@Table(name="products")
public class Product {
    
    @JsonView(View.Supplier.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonView(View.Supplier.class)
    @Enumerated(EnumType.STRING)
    private ESaleType type;

    @JsonView(View.Supplier.class)
    private String productName;

    @JsonView(View.Supplier.class)
    private String productDescription;

    @JsonView(View.Supplier.class)
    private String imgLink;

    @JsonView(View.Supplier.class)
    private double price;

    @JsonView(View.Supplier.class)
    private boolean showProduct;
            
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @JsonView(View.Supplier.class)
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "category_id", nullable = true)
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductContainsIngredient> ingredients;
   
    public Product(){};

    public Product(ESaleType type, String productName, String productDescription, String imgLink, double price, Supplier supplier, boolean showProduct) {
        this.type = type;
        this.productName = productName;
        this.productDescription = productDescription;
        this.imgLink = imgLink;
        this.price = price;
        this.supplier = supplier;        
        this.showProduct = showProduct;
    }

    public Product(ESaleType type, String productName, String productDescription, String imgLink, double price, Supplier supplier, boolean showProduct, Category category) {
        this.type = type;
        this.productName = productName;
        this.productDescription = productDescription;
        this.imgLink = imgLink;
        this.price = price;
        this.supplier = supplier;        
        this.showProduct = showProduct;    
        this.category = category;    
    }

    public Long getId(){
        return this.id;
    }

    public ESaleType getType() {
        return this.type;
    }

    public void setType(ESaleType type) {
        this.type = type;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return this.productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getImgLink() {
        return this.imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Supplier getSupplier() {
        return this.supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean getshowProduct() {
        return this.showProduct;
    }

    public void setshowProduct(boolean showProduct) {
        this.showProduct = showProduct;
    }	

}