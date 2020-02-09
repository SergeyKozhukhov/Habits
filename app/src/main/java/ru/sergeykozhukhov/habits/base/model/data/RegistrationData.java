package ru.sergeykozhukhov.habits.base.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegistrationData extends ConfidentialityData {

    @SerializedName("firstname")
    @Expose
    private String firstName;
    @SerializedName("lastname")
    @Expose
    private String lastName;

    public RegistrationData(String firstName, String lastName, String email, String password) {
        super(email, password);
        this.firstName = firstName;
        this.lastName = lastName;
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

}
