package it.refill.backend.repository.list;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.refill.backend.models.list.ProductList;

@Repository
public interface ProductListRepository extends JpaRepository<ProductList, Integer>{

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO products_lists(list_name, list_description, is_cart, customer_id) VALUES (:list_name, :list_description, :is_cart, :customer_id)", nativeQuery = true)
    void addProductList(@Param("list_name") String listName, @Param("list_description") String listDescription, @Param("is_cart") Boolean isCart,  @Param("customer_id") Long customerId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM products_lists WHERE id = :id AND customer_id = :customer_id", nativeQuery = true)
    int deleteList(@Param("id") Long id, @Param("customer_id") Long customerId);
    
    @Query(value = "SELECT * FROM products_lists WHERE customer_id = :customer_id", nativeQuery = true)
    List<ProductList> getAllListsUser(@Param("customer_id") Long id);

}