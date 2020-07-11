package it.refill.backend.repository.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.refill.backend.models.address.Nationality;

@Repository
public interface NationalityRepository extends JpaRepository<Nationality, Long> {
    
}