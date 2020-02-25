package ru.sergeykozhukhov.habits.model.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class JwtDataTest {

    @Test
    public void deserializeJwtJsonSuccess() {

        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        builder.setDateFormat("yyyy-MM-dd");
        Gson gson = builder.create();

        String jwtString = "token";

        String jsonInput = "{\"jwt\":\"" + jwtString + "\"}";

        JwtData targetObject = gson.fromJson(jsonInput, JwtData.class);

        assertThat(targetObject.getJwt(), is(jwtString));
    }

}