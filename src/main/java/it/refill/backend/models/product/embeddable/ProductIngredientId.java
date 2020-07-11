package it.refill.backend.models.product.embeddable;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductIngredientId implements Serializable {
    
    /**
     * class made in order to provide a composite Id for the list     
     * 
     * @author Sebastian Cavada
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "ingredient_id")
    private Long ingredientId;

    @Column(name="product_id")
    private Long productId;

    public ProductIngredientId(){}

    public ProductIngredientId(Long ingredientId, Long productId){
        this.ingredientId = ingredientId;
        this.productId = productId;
    }


    public Long getIngredientId() {
        return this.ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public Long getProductId() {
        return this.productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ProductIngredientId)) {
            return false;
        }
        ProductIngredientId productIngredientId = (ProductIngredientId) o;
        return Objects.equals(ingredientId, productIngredientId.ingredientId) && Objects.equals(productId, productIngredientId.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredientId, productId);
    }

}