package it.refill.backend.models.address;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="addresses")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Address {    

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="address_id")
    private Long id;

    private Long addressNumber;

    private String addressStreet;

    private String type;
      
    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "municipality_id", nullable = false)
    private Municipality municipality;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "county_id", nullable = false)
    private County county;
    
    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "nationality_id", nullable = false)    
    private Nationality nationality;    
    
    public Address(){}


    public Address(Long addressNumber, String addressStreet, String type, Municipality municipality, Nationality nationality, County county) {        
        this.addressNumber = addressNumber;
        this.addressStreet = addressStreet;
        this.type = type;
        this.municipality = municipality;
        this.nationality = nationality;
        this.county = county;
    }
    

    public Long getAddressNumber() {
        return this.addressNumber;
    }

    public void setAddressNumber(Long addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getAddressStreet() {
        return this.addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Municipality getMunicipality() {
        return this.municipality;
    }

    public void setMunicipality(Municipality municipality) {
        this.municipality = municipality;
    }

    public Nationality getNationality() {
        return this.nationality;
    }

    public void setNationality(Nationality nationality) {
        this.nationality = nationality;
    }

    public County getCounty() {
        return this.county;
    }

    public void setCounty(County county) {
        this.county = county;
    }
}