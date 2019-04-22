package uk.aber.ac.agroecommerce;

public class OrdersModel {

    private String streetAddress, city, country, date, personName, phoneNumber, postalCode, state, time, totalAmount,uid;

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrdersModel() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public OrdersModel(String streetAddress, String city, String country, String date, String personName, String phoneNumber, String postalCode, String state, String time, String totalAmount, String uid) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.country = country;
        this.date = date;
        this.personName = personName;
        this.phoneNumber = phoneNumber;
        this.postalCode = postalCode;
        this.state = state;
        this.time = time;
        this.totalAmount = totalAmount;
        this.uid =uid;
    }
}
