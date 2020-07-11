package it.refill.backend.repository.address;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.refill.backend.models.address.Municipality;

@Repository
public interface MunicipalityRepository extends JpaRepository<Municipality, Long> {
    
    @Query
    (value = "SELECT municipality_id, municipality_name FROM municipalities", nativeQuery = true)
    public List<Object[]> findMunicipalitiesShort();

}