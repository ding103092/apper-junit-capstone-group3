package group3.flight;

import group3.constants.Address;

public class Airport {
    String name;
    Address address;
    String code;
    public Airport(String name, Address address) {
        this.name = name;
        this.address = address;
        this.code = address.country().toUpperCase().substring(0,3);
    }

    @Override
    public String toString() {
        return name.toUpperCase() + " Airport";
    }
}
