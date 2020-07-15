package it.refill.backend.repository.list;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.refill.backend.models.list.ListProduct;

@Repository
public interface ListProductRepository extends JpaRepository<ListProduct, Integer>{

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO list_products(list_name, list_description, is_cart, customer_id) VALUES (:list_name, :list_description, :is_cart, :customer_id)", nativeQuery = true)
    void addListProduct(@Param("list_name") String listName, @Param("list_description") String listDescription, @Param("is_cart") Boolean isCart,  @Param("customer_id") Long customerId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM list_products WHERE id = :id AND customer_id = :customer_id", nativeQuery = true)
    int deleteList(@Param("id") Long id, @Param("customer_id") Long customerId);
    
    @Query(value = "SELECT * FROM list_products WHERE customer_id = :customer_id", nativeQuery = true)
    List<ListProduct> getAllListsUser(@Param("customer_id") Long id);

    @Query(value = "SELECT COUNT(*) FROM list_products WHERE id = :list_id AND customer_id = :customer_id LIMIT 1", nativeQuery = true)
    Integer checkIfListIsOwnedByUser(@Param("list_id") Long listId, @Param("customer_id") Long customerId);

}