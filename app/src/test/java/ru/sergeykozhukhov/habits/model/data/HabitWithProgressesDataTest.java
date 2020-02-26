package ru.sergeykozhukhov.habits.model.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.GeneratorData;
import ru.sergeykozhukhov.habits.model.domain.Habit;
import ru.sergeykozhukhov.habits.model.domain.HabitWithProgresses;
import ru.sergeykozhukhov.habits.model.domain.Progress;
import ru.sergeykozhukhov.habits.model.domain.exception.BuildException;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class HabitWithProgressesDataTest {

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
    public void deserializeHabitWithProgressesDataListJsonSuccess() {

        String jsonInput = "[{\"habit\":" +
                "{\"id_habit\":100,\"title\":\"title1\",\"description\":\"description1\",\"date_start\":\"1970-01-01\",\"duration\":10}," +
                "\"progresses\":" +
                "[{\"idProgress\":10,\"id_progress\":110,\"date\":\"1970-01-01\"}," +
                "{\"idProgress\":11,\"id_progress\":111,\"date\":\"1970-01-02\"}]}," +
                "{\"habit\":" +
                "{\"id_habit\":200,\"title\":\"title2\",\"description\":\"description2\",\"date_start\":\"1970-01-01\",\"duration\":20}," +
                "\"progresses\":" +
                "[{\"idProgress\":20,\"id_progress\":210,\"date\":\"1970-01-01\"}," +
                "{\"idProgress\":21,\"id_progress\":211,\"date\":\"1970-01-02\"}]}]";

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(1970, 0, 1, 0, 0, 0);
        calendar1.set(Calendar.MILLISECOND, 0);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(1970, 0, 2, 0, 0, 0);
        calendar2.set(Calendar.MILLISECOND, 0);

        HabitData habitData1 = new HabitData(100, "title1", "description1", calendar1.getTime(), 10);
        HabitData habitData2 = new HabitData(200, "title2", "description2", calendar1.getTime(), 20);

        ProgressData progressData1 = new ProgressData(10, 110, 0, calendar1.getTime());
        ProgressData progressData2 = new ProgressData(11, 111, 0, calendar2.getTime());
        ProgressData progressData3 = new ProgressData(20, 210, 0, calendar1.getTime());
        ProgressData progressData4 = new ProgressData(21, 211, 0, calendar2.getTime());

        HabitWithProgressesData habitWithProgressesData1 = new HabitWithProgressesData(habitData1, Arrays.asList(progressData1, progressData2));
        HabitWithProgressesData habitWithProgressesData2 = new HabitWithProgressesData(habitData2, Arrays.asList(progressData3, progressData4));

        List<HabitWithProgressesData> habitWithProgressesDataListExpected = Arrays.asList(habitWithProgressesData1, habitWithProgressesData2);

        Type habitWithProgressesDataListOutput = new TypeToken<List<HabitWithProgressesData>>() {}.getType();

        List<HabitWithProgressesData> targetCollectionOutput = gson.fromJson(jsonInput, habitWithProgressesDataListOutput);
        assertThat(targetCollectionOutput, instanceOf(ArrayList.class));
        assertThat(targetCollectionOutput, is(habitWithProgressesDataListExpected));
    }

    @Test
    public void serializeHabitWithProgressesDataListJsonSuccess() {

        HabitData habitData1 = new HabitData(1, 100, "title1", "description1", new Date(0), 10);
        HabitData habitData2 = new HabitData(2, 200, "title2", "description2", new Date(0), 20);

        ProgressData progressData1 = new ProgressData(10, 110, 1, new Date(0));
        ProgressData progressData2 = new ProgressData(11, 111, 1, new Date(75600000));
        ProgressData progressData3 = new ProgressData(20, 210, 2, new Date(0));
        ProgressData progressData4 = new ProgressData(21, 211, 2, new Date(75600000));

        HabitWithProgressesData habitWithProgressesData1 = new HabitWithProgressesData(habitData1, Arrays.asList(progressData1, progressData2));
        HabitWithProgressesData habitWithProgressesData2 = new HabitWithProgressesData(habitData2, Arrays.asList(progressData3, progressData4));

        List<HabitWithProgressesData> habitWithProgressesDataListInput = Arrays.asList(habitWithProgressesData1, habitWithProgressesData2);

        String jsonExpected = "[{\"habit\":" +
                "{\"id_habit\":100,\"title\":\"title1\",\"description\":\"description1\",\"date_start\":\"1970-01-01\",\"duration\":10}," +
                "\"progresses\":" +
                "[{\"idProgress\":10,\"id_progress\":110,\"date\":\"1970-01-01\"}," +
                "{\"idProgress\":11,\"id_progress\":111,\"date\":\"1970-01-02\"}]}," +
                "{\"habit\":" +
                "{\"id_habit\":200,\"title\":\"title2\",\"description\":\"description2\",\"date_start\":\"1970-01-01\",\"duration\":20}," +
                "\"progresses\":" +
                "[{\"idProgress\":20,\"id_progress\":210,\"date\":\"1970-01-01\"}," +
                "{\"idProgress\":21,\"id_progress\":211,\"date\":\"1970-01-02\"}]}]";

        String jsonOutput = gson.toJson(habitWithProgressesDataListInput);

        assertThat(jsonOutput, is(jsonExpected));
    }
}