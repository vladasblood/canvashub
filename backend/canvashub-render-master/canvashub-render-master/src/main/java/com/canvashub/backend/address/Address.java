package com.canvashub.backend.address;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "ADDRESS")
public class Address {
    @Id
    @Column(name = "ADDRESS_ID")
    private String id;

    @Column(name = "BUILDING")
    private String building;

    @Column(name = "STREET")
    private String street;

    @Column(name = "DISTRICT")
    private String district;

    @Column(name = "CITY")
    private String city;

    @Column(name = "PROVINCE")
    private String province;

    @Column(name = "ZIP_CODE")
    private String zip_code;

    @Column(name = "COUNTRY")
    private String country;
}
