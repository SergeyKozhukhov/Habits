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
    public void serializeConfidentialityJsonSuccess() {

        String email = "mail@gmail.com";
        String password = "password";
        ConfidentialityData confidentialityDataInput = new ConfidentialityData(email, password);
        String jsonExpected = "{\"email\":\"mail@gmail.com\",\"password\":\"password\"}";

        String jsonOutput = gson.toJson(confidentialityDataInput);

        assertThat(jsonOutput, is(jsonExpected));
    }


}