package edu.tartu.esi;

import java.io.Serializable;

public class LocationEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String address;
    private String fullAddress;

    public LocationEvent() {
    }

    public LocationEvent(String id, String address, String fullAddress) {
        this.id = id;
        this.address = address;
        this.fullAddress = fullAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    @Override
    public String toString() {
        return "LocationEvent{" +
                "id='" + id + '\'' +
                ", address='" + address + '\'' +
                ", fullAddress='" + fullAddress + '\'' +
                '}';
    }
}
