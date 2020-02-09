package ru.sergeykozhukhov.habits.notes.backup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ru.sergeykozhukhov.habits.base.model.data.JwtData;

public class AuthenticationData extends JwtData {
    @SerializedName("message")
    @Expose
    private String message;

    public AuthenticationData(String message, String jwt) {
        super(jwt);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
