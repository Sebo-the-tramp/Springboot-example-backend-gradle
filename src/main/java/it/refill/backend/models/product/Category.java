package it.refill.backend.models.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import it.refill.backend.views.View;

@Entity
@Table(name = "categories")
public class Category {
        
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @JsonView(View.Supplier.class)
    @Column(name = "category_id")
    private Integer id;

    @JsonView(View.Supplier.class)
    private String categoryName;

    public Category(){}

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


}