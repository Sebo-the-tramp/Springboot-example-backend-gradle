package it.refill.backend.repository.products;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.refill.backend.models.product.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {    

    public List<Product> findBySupplierId(Long id);
    public Product getOneByIdAndSupplierId(Long productId, Long supplierId);

}