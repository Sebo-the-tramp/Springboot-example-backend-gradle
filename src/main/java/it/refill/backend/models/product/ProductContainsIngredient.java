package it.refill.backend.models.product;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import it.refill.backend.models.address.County;
import it.refill.backend.models.product.embeddable.ProductIngredientId;

@Entity
@Table(name="product_contains_ingredient")
public class ProductContainsIngredient {
    
    @EmbeddedId
    private ProductIngredientId id;

    private Float quantity;

    private boolean critical;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ingredientId")
    private Ingredient ingredient;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    private Product product;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "county_id", referencedColumnName = "county_id")
    private County county;

    public ProductContainsIngredient(){}

    public ProductContainsIngredient(ProductIngredientId id, Float quantity, boolean critical) {
        this.id = id;
        this.quantity = quantity;
        this.critical = critical;        
    }

    public ProductIngredientId getId() {
        return this.id;
    }

    public void setId(ProductIngredientId id) {
        this.id = id;
    }

    public Float getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public boolean isCritical() {
        return this.critical;
    }

    public boolean getCritical() {
        return this.critical;
    }

    public void setCritical(boolean critical) {
        this.critical = critical;
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductContainsIngredient id(ProductIngredientId id) {
        this.id = id;
        return this;
    }

    public ProductContainsIngredient quantity(Float quantity) {
        this.quantity = quantity;
        return this;
    }

    public ProductContainsIngredient critical(boolean critical) {
        this.critical = critical;
        return this;
    }

    public ProductContainsIngredient ingredient(Ingredient ingredient) {
        this.ingredient = ingredient;
        return this;
    }

    public ProductContainsIngredient product(Product product) {
        this.product = product;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ProductContainsIngredient)) {
            return false;
        }
        ProductContainsIngredient productContainsIngredient = (ProductContainsIngredient) o;
        return Objects.equals(id, productContainsIngredient.id) && Objects.equals(quantity, productContainsIngredient.quantity) && critical == productContainsIngredient.critical && Objects.equals(ingredient, productContainsIngredient.ingredient) && Objects.equals(product, productContainsIngredient.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, critical, ingredient, product);
    }
    
}