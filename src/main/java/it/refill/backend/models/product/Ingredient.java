package it.refill.backend.models.product;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import it.refill.backend.models.address.County;

@Entity(name="Ingredient")
@Table(name = "ingredients")
public class Ingredient {
    
    @Id    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ingredientName;       

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductContainsIngredient> products;

    public Ingredient(){}

    public Ingredient(Long id, String ingredientName, County county) {
        this.id = id;
        this.ingredientName = ingredientName;        
    }

    public Long getIngredientId() {
        return this.id;
    }

    public void setIngredientId(Long id) {
        this.id = id;
    }

    public String getIngredientName() {
        return this.ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Ingredient)) {
            return false;
        }
        Ingredient ingredient = (Ingredient) o;
        return Objects.equals(id, ingredient.id) && Objects.equals(ingredientName, ingredient.ingredientName) && Objects.equals(products, ingredient.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ingredientName, products);
    }    

}