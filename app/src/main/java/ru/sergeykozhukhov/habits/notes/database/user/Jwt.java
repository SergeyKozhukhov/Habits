package ru.sergeykozhukhov.habits.notes.database.user;

import androidx.room.ColumnInfo;

public class Jwt {

    // @SerializedName("jwt")
    // @Expose
    @ColumnInfo(name = "token")
    private String jwt;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
