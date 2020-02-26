package ru.sergeykozhukhov.habits.model.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class RegistrationDataTest {

    private static final String PATTERN = "yyyy-MM-dd";

    private Gson gson;

    @Before
    public void setUp() {
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        builder.setDateFormat(PATTERN);
        gson = builder.create();
    }

    @Test
    public void serializeRegistrationDataJsonSuccess() {

        RegistrationData registrationDataInput = new RegistrationData("firstname", "lastname", "email@gmail.com", "password");

        String jsonExpected = "{\"firstname\":\"firstname\"," +
                "\"lastname\":\"lastname\"," +
                "\"email\":\"email@gmail.com\"," +
                "\"password\":\"password\"}";

        String jsonOutput = gson.toJson(registrationDataInput);

        assertThat(jsonOutput, is(jsonExpected));
    }

}