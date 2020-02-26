package ru.sergeykozhukhov.habits.model.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class JwtDataTest {

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
    public void deserializeJwtDataJsonSuccess() {

        String jsonInput = "{\"jwt\":\"token\"}";
        JwtData jwtDataExpected = new JwtData("token");

        JwtData jwtDataOutput = gson.fromJson(jsonInput, JwtData.class);

        assertThat(jwtDataOutput, is(jwtDataExpected));
    }

}