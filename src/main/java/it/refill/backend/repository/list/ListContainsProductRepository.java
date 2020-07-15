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
    @Query(value = "INSERT INTO list_contains_products(list_id, product_id, customer_id, quantity) VALUES (:list_id, :product_id, :customer_id, :quantity)", nativeQuery = true)
    void addProductList(@Param("list_id") Long listId, @Param("product_id") Long productId, @Param("customer_id") Long customerId, @Param("quantity") Integer quantity);

    @Transactional
    @Modifying
    @Query(value = "UPDATE list_contains_products SET quantity = :quantity, WHERE list_id = :list_id, product_id = :product_id, customer_id = :customer_id)", nativeQuery = true)
    void upadteProductList(@Param("list_id") Long listId, @Param("product_id") Long productId, @Param("customer_id") Long customerId, @Param("quantity") Integer quantity);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM list_contains_products WHERE list_id = :list_id, product_id = :product_id, customer_id = :customer_id)", nativeQuery = true)
    void removeProductList(@Param("list_id") Long listId, @Param("product_id") Long productId, @Param("customer_id") Long customerId);

    @Query(value = "SELECT quantity FROM list_contains_products WHERE EXISTS (SELECT quantity FROM list_contains_products WHERE product_id = :product_id AND list_id = :list_id)", nativeQuery = true)
    Integer checkIfProductIsInList(@Param("product_id") Long productId, @Param("list_id") Long listId);

    @Query(value = "SELECT * FROM list_contains_products INNER JOIN products_lists ON list_contains_products.list_id = products_lists.id WHERE list_id = :list_id AND customer_id = :customer_id LIMIT 1", nativeQuery = true)
    Boolean checkIfListIsOwnedByUser(@Param("list_id") Long productId, @Param("customer_id") Long listId);

}