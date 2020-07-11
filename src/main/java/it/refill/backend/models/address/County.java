package it.refill.backend.models.address;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "counties")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class County {

    @Id    
    @Column(name="county_id")
    private Long countyId;

    @Column(name="county_name")
    private String name;

    @Column(name="county_short_name")
    private String shortString;

    @OneToMany(mappedBy = "county", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Address> addresses;   
    

    public County() {
    }

    public County(Long countyId, String name) {
        this.countyId = countyId;
        this.name = name;
    }

    public Long getIdCounty() {
        return this.countyId;
    }

    public void setIdCounty(Long countyId) {
        this.countyId = countyId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortString() {
        return this.shortString;
    }

    public void setShortString(String shortString) {
        this.shortString = shortString;
    }    
    
}