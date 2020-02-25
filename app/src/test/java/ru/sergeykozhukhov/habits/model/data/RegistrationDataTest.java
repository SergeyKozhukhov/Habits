package ru.sergeykozhukhov.habits.model.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class RegistrationDataTest {

    @Test
    public void serializeRegistrationJsonSuccess() {

        String firstname = "firstname";
        String lastname = "lastname";
        String email = "email@gmail.com";
        String password = "password";

        RegistrationData registrationData = new RegistrationData(firstname, lastname, email, password);

        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        builder.setDateFormat("yyyy-MM-dd");
        Gson gson = builder.create();

        String jsonExpected = "{\"firstname\":\""+firstname+"\",\"lastname\":\""+lastname+"\",\"email\":\""+email+"\",\"password\":\""+password+"\"}";

        String jsonOutput = gson.toJson(registrationData);

        assertThat(jsonOutput, is(jsonExpected));
    }

}