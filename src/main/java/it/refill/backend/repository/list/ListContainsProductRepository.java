package it.refill.backend.repository.list;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.refill.backend.models.list.ListContainsProducts;

public interface ListContainsProductRepository extends JpaRepository<ListContainsProducts, Integer> {
    
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO list_contains_products(list_id, product_id, quantity) VALUES (:list_id, :product_id, :quantity)", nativeQuery = true)
    void addListProduct(@Param("list_id") Long listId, @Param("product_id") Long productId, @Param("quantity") Integer quantity);

    @Transactional
    @Modifying
    @Query(value = "UPDATE list_contains_products SET quantity = :quantity WHERE list_id = :list_id AND product_id = :product_id", nativeQuery = true)
    void upadteListProduct(@Param("list_id") Long listId, @Param("product_id") Long productId, @Param("quantity") Integer quantity);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM list_contains_products WHERE list_id = :list_id AND product_id = :product_id", nativeQuery = true)
    void removeListProduct(@Param("list_id") Long listId, @Param("product_id") Long productId);

    @Query(value = "SELECT COUNT(*) FROM list_contains_products WHERE product_id = :product_id AND list_id = :list_id", nativeQuery = true)
    Integer checkIfProductIsInList(@Param("product_id") Long productId, @Param("list_id") Long listId);    

    @Query(value = "SELECT quantity FROM list_contains_products WHERE list_id = :list_id AND product_id = :product_id LIMIT 1", nativeQuery = true)
    Integer getQuantityProduct(@Param("list_id") Long listId, @Param("product_id") Long productId);

}