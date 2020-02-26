package ru.sergeykozhukhov.habits.model.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ru.sergeykozhukhov.habits.GeneratorData;
import ru.sergeykozhukhov.habits.model.domain.Progress;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ProgressDataTest {

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
    public void serializeProgressDataJsonSuccess() {

        ProgressData progressDataInput = new ProgressData(1, 100L, 10, new Date(0));
        String jsonExpected = "{\"idProgress\":1,\"id_progress\":100,\"date\":\"1970-01-01\"}";

        String jsonOutput = gson.toJson(progressDataInput);

        assertThat(jsonOutput, is(jsonExpected));
    }

    @Test
    public void deserializeProgressDataJsonSuccess() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(1970, 0, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        String jsonInput = "{\"idProgress\":1," +
                "\"id_progress\":100," +
                "\"date\":\"1970-01-01\"}";
        ProgressData progressDataExpected = new ProgressData(1, 100, 0, calendar.getTime());

        ProgressData progressDataOutput = gson.fromJson(jsonInput, ProgressData.class);

        assertThat((progressDataOutput), is(progressDataExpected));
    }
}