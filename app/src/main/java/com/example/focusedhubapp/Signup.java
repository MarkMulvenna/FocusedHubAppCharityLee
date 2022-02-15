package com.example.focusedhubapp;

public class Signup {
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactDay() {
        return contactDay;
    }

    public void setContactDay(String contactDay) {
        this.contactDay = contactDay;
    }

    public String getDonationSelection() {
        return donationSelection;
    }

    public void setDonationSelection(String donationSelection) {
        this.donationSelection = donationSelection;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    private String firstName;
    private String lastName;
    private String address;
    private String contactTel;
    private String mobile;
    private String email;

    public Signup() {
    }

    public String getTimeofDay() {
        return timeofDay;
    }

    public void setTimeofDay(String timeofDay) {
        this.timeofDay = timeofDay;
    }

    public String getMainCharity() {
        return mainCharity;
    }

    public void setMainCharity(String mainCharity) {
        this.mainCharity = mainCharity;
    }

    public Signup(String firstName, String lastName, String address, String contactTel, String mobile, String email, String mainCharity, String donationSelection, String contactDay, String timeofDay, String notes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.contactTel = contactTel;
        this.mobile = mobile;
        this.email = email;
        this.donationSelection = donationSelection;
        this.contactDay = contactDay;
        this.timeofDay = timeofDay;
        this.mainCharity = mainCharity;
        this.notes = notes;
    }

    private String contactDay;
    private String donationSelection;
    private String notes;
    private String timeofDay;
    private String mainCharity;







}
