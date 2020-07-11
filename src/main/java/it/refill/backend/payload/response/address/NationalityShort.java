package it.refill.backend.payload.response.address;

public class NationalityShort {

    private Long nationality_id;
    private String nationality_name;

    public NationalityShort(){};

    public NationalityShort(Long nationality_id, String nationality_name) {
        this.nationality_id = nationality_id;
        this.nationality_name = nationality_name;
    }

    public Long getNationality_id() {
        return this.nationality_id;
    }

    public void setNationality_id(Long nationality_id) {
        this.nationality_id = nationality_id;
    }

    public String getNationality_name() {
        return this.nationality_name;
    }

    public void setNationality_name(String nationality_name) {
        this.nationality_name = nationality_name;
    }
    
}