package ru.sergeykozhukhov.habits.model.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ru.sergeykozhukhov.habits.GeneratorData;
import ru.sergeykozhukhov.habits.model.domain.Confidentiality;
import ru.sergeykozhukhov.habits.model.domain.Habit;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

// https://github.com/eugenp/tutorials/tree/master/gson/src/test/java/com/baeldung/gson/serialization
// https://www.baeldung.com/gson-deserialization-guide
public class ConfidentialityDataTest {

    @Test
    public void serializeConfidentialityJsonSuccess() {

        String email = "ivanov@gmail.com";
        String password = "workhardplayhard";
        ConfidentialityData confidentialityData = new ConfidentialityData(email, password);

        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        builder.setDateFormat("yyyy-MM-dd");
        Gson gson = builder.create();

        String jsonExpected = "{\"email\":\"" + email + "\",\"password\":\"" + confidentialityData.getPassword() + "\"}";

        String jsonOutput = gson.toJson(confidentialityData);

        assertThat(jsonOutput, is(jsonExpected));
    }

}