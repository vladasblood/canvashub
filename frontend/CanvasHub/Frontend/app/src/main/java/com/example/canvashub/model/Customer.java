package com.example.canvashub.model;

import java.sql.Timestamp;
import java.util.Objects;

public class Customer {
    private String id;
    private String username;
    private String password;
    private String first_name;
    private String last_name;
    private String email_address;
    private String contact_number;
    private Address address_Id;
    private String token;
    private int tries;
    private long tries_timestamp;

    public Customer() {
    }

    public Customer(String id, String username, String password, String first_name, String last_name, String email_address, String contact_number, Address address_Id, String token, int tries, long tries_timestamp) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email_address = email_address;
        this.contact_number = contact_number;
        this.address_Id = address_Id;
        this.token = token;
        this.tries = tries;
        this.tries_timestamp = tries_timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public Address getAddress_Id() {
        return address_Id;
    }

    public void setAddress_Id(Address address_Id) {
        this.address_Id = address_Id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getTries() {
        return tries;
    }

    public void setTries(int tries) {
        this.tries = tries;
    }

    public long getTries_timestamp() {
        return tries_timestamp;
    }

    public void setTries_timestamp(long tries_timestamp) {
        this.tries_timestamp = tries_timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return tries == customer.tries && tries_timestamp == customer.tries_timestamp && Objects.equals(id, customer.id) && Objects.equals(username, customer.username) && Objects.equals(password, customer.password) && Objects.equals(first_name, customer.first_name) && Objects.equals(last_name, customer.last_name) && Objects.equals(email_address, customer.email_address) && Objects.equals(contact_number, customer.contact_number) && Objects.equals(address_Id, customer.address_Id) && Objects.equals(token, customer.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, first_name, last_name, email_address, contact_number, address_Id, token, tries, tries_timestamp);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email_address='" + email_address + '\'' +
                ", contact_number='" + contact_number + '\'' +
                ", address_Id=" + address_Id +
                ", token='" + token + '\'' +
                ", tries=" + tries +
                ", tries_timestamp=" + tries_timestamp +
                '}';
    }
}

