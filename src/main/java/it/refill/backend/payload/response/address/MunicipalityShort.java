package it.refill.backend.payload.response.address;

public class MunicipalityShort {

    private Long municipality_id;
    private String municipality_name;


    public MunicipalityShort() {
    }

    public MunicipalityShort(Long municipality_id, String municipality_name) {
        this.municipality_id = municipality_id;
        this.municipality_name = municipality_name;
    }

    public Long getMunicipality_id() {
        return this.municipality_id;
    }

    public void setMunicipality_id(Long municipality_id) {
        this.municipality_id = municipality_id;
    }

    public String getMunicipality_name() {
        return this.municipality_name;
    }

    public void setMunicipality_name(String municipality_name) {
        this.municipality_name = municipality_name;
    }

    public MunicipalityShort municipality_id(Long municipality_id) {
        this.municipality_id = municipality_id;
        return this;
    }

    public MunicipalityShort municipality_name(String municipality_name) {
        this.municipality_name = municipality_name;
        return this;
    }   
}