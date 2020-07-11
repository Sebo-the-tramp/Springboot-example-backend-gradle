package it.refill.backend.repository.address;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.refill.backend.models.address.County;

@Repository
public interface CountyRepository extends JpaRepository<County, Long> {
    
    @Query
    (value = "SELECT county_id, county_name FROM counties WHERE county_short_name = TN", nativeQuery = true)
    public List<Object[]> findCountiesShort();

}