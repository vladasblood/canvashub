package com.example.canvashub.model;

import java.util.Objects;

public class Address {
    private String id;

    private String building;

    private String district;
    private String street;
    private String city;
    private String province;
    private String zip_code;
    private String country;

    public Address() {
    }

    public Address(String id, String building, String district, String street, String city, String province, String zip_code, String country) {
        this.id = id;
        this.building = building;
        this.district = district;
        this.street = street;
        this.city = city;
        this.province = province;
        this.zip_code = zip_code;
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return Objects.equals(id, address.id) && Objects.equals(country, address.country) && Objects.equals(district, address.district) && Objects.equals(building, address.building) && Objects.equals(street, address.street) && Objects.equals(city, address.city) && Objects.equals(province, address.province) && Objects.equals(zip_code, address.zip_code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, building, district, street, city, province, zip_code, country);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id='" + id + '\'' +
                ", street='" + street + '\'' +
                ", building='" + building + '\'' +
                ", district='" + district + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", zip_code='" + zip_code + '\'' +
                '}';
    }
}
