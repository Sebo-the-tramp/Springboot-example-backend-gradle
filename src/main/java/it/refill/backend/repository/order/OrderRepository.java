package it.refill.backend.repository.order;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.refill.backend.models.order.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {    

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO order_contains_products(price, quantity, product_id, order_id) (SELECT products.price, quantity, products.id as product_id, :order_id as order_id FROM list_contains_products INNER JOIN products ON list_contains_products.product_id = products.id)", nativeQuery = true)
    Integer copyProducts(@Param("order_id") Long orderId);

}