package com.example.focusedhubapp;

import androidx.annotation.NonNull;

public class User {
    private String charity;
    private String address;
    private String code;

    public User(String charity, String address, String code) {
        this.charity = charity;
        this.address = address;
        this.code = code;
    }

    public String getCharity() {
        return charity;
    }

    public void setCharity(String charity) {
        this.charity = charity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @NonNull
    @Override
    public String toString() {
        return charity;
    }
}
