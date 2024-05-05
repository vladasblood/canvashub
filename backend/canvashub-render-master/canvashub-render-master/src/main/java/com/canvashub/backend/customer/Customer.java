package com.canvashub.backend.customer;

import com.canvashub.backend.address.Address;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "CUSTOMER")
public class Customer {
    @Id
    @Column(name = "CUSTOMER_ID")
    private String id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "FIRST_NAME")
    private String first_name;

    @Column(name = "LAST_NAME")
    private String last_name;

    @Column(name = "EMAIL_ADDRESS")
    private String email_address;

    @Column(name = "CONTACT_NUMBER")
    private String contact_number;

    @ManyToOne
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ADDRESS_ID")
    private Address address_Id;

    @Column (name = "TOKEN")
    private String token;

    @Column (name = "TRIES")
    private String tries;

    @Column (name = "TRIES_TIMESTAMP")
    private Long tries_timestamp;
}
