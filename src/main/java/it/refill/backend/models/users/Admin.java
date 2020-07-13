package it.refill.backend.models.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "admins")
public class Admin {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="admin_id")
    private Long adminId;

    private String name;
    private String surname;

    private String image;

    @JsonIgnoreProperties("admin")
    @OneToOne(mappedBy = "admin")
    private User user;

    public Admin() {
    }

    public Admin(Long adminId, String name, String surname, String image) {
        this.adminId = adminId;
        this.name = name;
        this.surname = surname;
        this.image = image;
    }

    public Long getAdminId() {
        return this.adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Admin adminId(Long adminId) {
        this.adminId = adminId;
        return this;
    }

    public Admin name(String name) {
        this.name = name;
        return this;
    }

    public Admin surname(String surname) {
        this.surname = surname;
        return this;
    }

    public Admin image(String image) {
        this.image = image;
        return this;
    }

}