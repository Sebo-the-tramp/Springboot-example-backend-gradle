package it.refill.backend.repository.products;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.refill.backend.models.product.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO product_categories(product_id, category_id) VALUES (:product_id, :category_id)", nativeQuery = true)
    void insertProductCategory(@Param("product_id") Long product_id, @Param("category_id") Integer category_id);
}