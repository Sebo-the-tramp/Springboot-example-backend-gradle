package it.refill.backend.models.address;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="municipalities")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Municipality {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="municipality_id")
    private Long municipalityId;
    
    private Long postalCode;

    private String municipalityName;

    private String municipalityImage;

    private String municipalityDescription;
    
    @OneToMany(mappedBy = "municipality", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Address> addresses;   
    
    public Municipality(){};

    public Long getPostalCode() {
        return this.postalCode;
    }

    public Long getMunicipalityId() {
        return this.municipalityId;
    }

    public void setMunicipalityId(Long municipalityId) {
        this.municipalityId = municipalityId;
    }

    public void setPostalCode(Long postalCode) {
        this.postalCode = postalCode;
    }

    public String getMunicipalityName() {
        return this.municipalityName;
    }

    public void setMunicipalityName(String municipalityName) {
        this.municipalityName = municipalityName;
    }

    public String getMunicipalityImage() {
        return this.municipalityImage;
    }

    public void setMunicipalityImage(String municipalityImage) {
        this.municipalityImage = municipalityImage;
    }

    public String getMunicipalityDescription() {
        return this.municipalityDescription;
    }

    public void setMunicipalityDescription(String municipalityDescription) {
        this.municipalityDescription = municipalityDescription;
    }
    

}