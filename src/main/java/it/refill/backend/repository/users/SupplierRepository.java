package it.refill.backend.repository.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.refill.backend.models.users.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {    
}