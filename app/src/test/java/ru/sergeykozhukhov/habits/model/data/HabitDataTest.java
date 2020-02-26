package ru.sergeykozhukhov.habits.model.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import ru.sergeykozhukhov.habits.GeneratorData;
import ru.sergeykozhukhov.habits.data.converter.DateConverter;
import ru.sergeykozhukhov.habits.model.domain.Habit;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class HabitDataTest {

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
    public void serializeHabitDataJsonSuccess() {
        HabitData habitDataInput = new HabitData(101, "title1", "description1", new Date(0), 11);
        String jsonExpected = "{\"id_habit\":101," +
                "\"title\":\"title1\"," +
                "\"description\":\"description1\"," +
                "\"date_start\":\"1970-01-01\"," +
                "\"duration\":11}";

        String jsonOutput = gson.toJson(habitDataInput);

        assertThat(jsonOutput, is(jsonExpected));
    }

    @Test
    public void deserializeHabitDataJsonSuccess() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970, 0, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        String jsonInput = "{\"id_habit\":101," +
                "\"title\":\"title1\"," +
                "\"description\":\"description1\"," +
                "\"date_start\":\"1970-01-01\"," +
                "\"duration\":11}";
        HabitData habitDataExpected = new HabitData(101, "title1", "description1", calendar.getTime(), 11);

        HabitData habitDataOutput = gson.fromJson(jsonInput, HabitData.class);

        assertThat((habitDataOutput), is(habitDataExpected));
    }
}