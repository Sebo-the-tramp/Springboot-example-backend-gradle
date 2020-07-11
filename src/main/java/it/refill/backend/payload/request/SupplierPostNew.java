package it.refill.backend.payload.request;

import java.util.Set;

import javax.validation.constraints.*;

import it.refill.backend.models.users.Supplier;
import it.refill.backend.models.users.UserAuth;

public class SupplierPostNew {

    @NotBlank
    private String type;

    @NotNull
    private Supplier supplier;

    @NotNull
    private Set<AddressReduced> addresses;

    @NotNull
    private UserAuth userAuth;

    public SupplierPostNew() {
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Supplier getSupplier() {
        return this.supplier;
    }

    public void setSupplier(Supplier suppleir) {
        this.supplier = suppleir;
    }

    public Set<AddressReduced> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(Set<AddressReduced> addresses) {
        this.addresses = addresses;
    }

    public UserAuth getUserAuth() {
        return this.userAuth;
    }

    public void setUserAuth(UserAuth userAuth) {
        this.userAuth = userAuth;
    }

    public static class AddressReduced {

        private Long addressNumber;
        private String addressStreet;
        private String type;
        private Long municipalityId;
        private Long nationalityId;
        private Long countyId;        

        public AddressReduced() {
        }

        public AddressReduced(Long addressNumber, String addressStreet, String type, Long municipalityId, Long nationalityId, Long countyId) {
            this.addressNumber = addressNumber;
            this.addressStreet = addressStreet;
            this.type = type;
            this.municipalityId = municipalityId;
            this.nationalityId = nationalityId;
            this.countyId = countyId;
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

        public Long getMunicipalityId() {
            return this.municipalityId;
        }

        public void setMunicipalityId(Long municipalityId) {
            this.municipalityId = municipalityId;
        }

        public Long getNationalityId() {
            return this.nationalityId;
        }

        public void setNationalityId(Long nationalityId) {
            this.nationalityId = nationalityId;
        }

        public Long getCountyId(){
            return this.countyId;
        }

        public void setCountyId(Long countyId){
            this.countyId = countyId;
        }

    }
}