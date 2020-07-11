package it.refill.backend.payload.response.address;

public class CountyShort {

    private Long countyId;
    private String countyName;


    public CountyShort() {
    }


    public CountyShort(Long countyId, String countyName) {
        this.countyId = countyId;
        this.countyName = countyName;
    }

    public Long getCountyId() {
        return this.countyId;
    }

    public void setCountyId(Long countyId) {
        this.countyId = countyId;
    }

    public String getCountyName() {
        return this.countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    
}