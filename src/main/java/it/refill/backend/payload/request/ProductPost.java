package it.refill.backend.payload.request;

import it.refill.backend.models.product.Category;
import it.refill.backend.models.product.ESaleType;

public class ProductPost {
    
    private ESaleType type;

    private String productName;

    private String productDescription;

    private String imgLink;

    private double price;

    private Category category;

    public ProductPost(){}

    public ProductPost(ESaleType type, String productName, String productDescription, String imgLink, double price, Category category) {
        this.type = type;
        this.productName = productName;
        this.productDescription = productDescription;
        this.imgLink = imgLink;
        this.price = price;
        this.category = category;
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

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }



}