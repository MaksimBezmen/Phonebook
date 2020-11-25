package entity;


import type.FamilyStatusType;
import type.SexType;

import java.time.LocalDate;

public class Contact {

    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private LocalDate birthday;
    private SexType gender;
    private String citizenship;
    private FamilyStatusType familyStatus;
    private String webSite;
    private String email;
    private String currentPlaceOfWork;
    private Address address;

    public Contact(String firstName, String lastName, String middleName, LocalDate birthday, SexType gender, String citizenship, FamilyStatusType familyStatus, String webSite, String email, String currentPlaceOfWork, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthday = birthday;
        this.gender = gender;
        this.citizenship = citizenship;
        this.familyStatus = familyStatus;
        this.webSite = webSite;
        this.email = email;
        this.currentPlaceOfWork = currentPlaceOfWork;
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public SexType getGender() {
        return gender;
    }

    public void setGender(SexType gender) {
        this.gender = gender;
    }


    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public FamilyStatusType getFamilyStatus() {
        return familyStatus;
    }

    public void setFamilyStatus(FamilyStatusType familyStatus) {
        this.familyStatus = familyStatus;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrentPlaceOfWork() {
        return currentPlaceOfWork;
    }

    public void setCurrentPlaceOfWork(String currentPlaceOfWork) {
        this.currentPlaceOfWork = currentPlaceOfWork;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", birthday=" + birthday +
                ", gender=" + gender +
                ", citizenship='" + citizenship + '\'' +
                ", familyStatus=" + familyStatus +
                ", webSite='" + webSite + '\'' +
                ", email='" + email + '\'' +
                ", currentPlaceOfWork='" + currentPlaceOfWork + '\'' +
                ", address=" + address.toString() +
                '}';
    }
}
