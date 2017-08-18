package com.mytestedapp.rest;


import com.google.gson.annotations.SerializedName;

public class ProfileResponse {

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("totalRides")
    private String totalRides;

    public ProfileResponse() {
    }

    public ProfileResponse(String firstName, String lastName, String totalRides) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.totalRides = totalRides;
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

    public String getTotalRides() {
        return totalRides;
    }

    public void setTotalRides(String totalRides) {
        this.totalRides = totalRides;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", totalRides='" + totalRides + '\'' +
                '}';
    }
}
