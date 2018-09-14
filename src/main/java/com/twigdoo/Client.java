package com.twigdoo;

public class Client {
    private String name;
    private String phone;
    private String email;
    private String address;


    public String getName() {
        return name;
    }

    public Client withName(String name) {
        this.name = name;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Client withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Client withEmail(String email) {
        this.email = email;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Client withAddress(String address) {
        this.address = address;
        return this;
    }
}
