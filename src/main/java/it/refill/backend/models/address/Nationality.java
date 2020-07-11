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
@Table(name = "nationalities")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Nationality {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="nationality_id")
    private Long nationalityId;

    private String nationalityName;

    private String nationalityShort;

    @OneToMany(mappedBy = "municipality", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Address> addresses;   

    public Nationality(){}    


    public Long getNationalityId() {
        return this.nationalityId;
    }

    public void setNationalityId(Long nationalityId) {
        this.nationalityId = nationalityId;
    }


    public String getNationalityName() {
        return this.nationalityName;
    }

    public void setNationalityName(String nationalityName) {
        this.nationalityName = nationalityName;
    }

    public String getNationalityShort() {
        return this.nationalityShort;
    }

    public void setNationalityShort(String nationalityShort) {
        this.nationalityShort = nationalityShort;
    }
}