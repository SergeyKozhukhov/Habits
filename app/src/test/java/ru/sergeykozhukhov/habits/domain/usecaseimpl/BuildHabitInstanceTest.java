package ru.sergeykozhukhov.habits.domain.usecaseimpl;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.model.domain.Habit;
import ru.sergeykozhukhov.habits.model.domain.exception.BuildException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class BuildHabitInstanceTest {

    private BuildHabitInteractor buildHabitInstance;

    @Before
    public void setUp() {
        buildHabitInstance = new BuildHabitInteractor();
    }

    @Test
    public void buildSuccess() throws BuildException, ParseException {
        String title = "title";
        String description = "description";
        String startDateString = "01-01-2000";
        String durationString = "21";

        String pattern = "dd-MM-yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        Date startDate = dateFormat.parse(startDateString);

        int duration = 21;

        Habit habitExpected = new Habit(title, description, startDate, duration);

        Habit habitOutput = buildHabitInstance.build(title, description, startDateString, durationString);

        assertThat(habitOutput, is(habitExpected));
    }

    @Test
    public void buildErrorNull() {
        try {
            buildHabitInstance.build(null, null, null, null);
            fail();
        } catch (BuildException e) {
            assertThat(R.string.null_data_build_instance_exception, is(e.getMessageRes()));
        }
    }

    @Test
    public void buildErrorMinTitle(){
        String title = "tl";
        String description = "description";
        String startDateString = "01-01-2000";
        String durationString = "21";

        try {
            buildHabitInstance.build(title, description, startDateString, durationString);
            fail();
        } catch (BuildException e) {
            assertThat(R.string.title_min_build_instance_exception, is(e.getMessageRes()));
        }
    }

    @Test
    public void buildErrorMaxTitle(){
        String title = "qwertyuiopasdfghjklz";
        String description = "description";
        String startDateString = "01-01-2000";
        String durationString = "21";

        try {
            buildHabitInstance.build(title, description, startDateString, durationString);
            fail();
        } catch (BuildException e) {
            assertThat(R.string.title_max_build_instance_exception, is(e.getMessageRes()));
        }
    }

    @Test
    public void buildErrorMinDescription(){
        String title = "title";
        String description = "something";
        String startDateString = "01-01-2000";
        String durationString = "21";

        try {
            buildHabitInstance.build(title, description, startDateString, durationString);
            fail();
        } catch (BuildException e) {
            assertThat(R.string.description_min_build_instance_exception, is(e.getMessageRes()));
        }
    }

    @Test
    public void buildErrorMaxDescription(){
        String title = "title";
        char[] array = new char[200];
        for (int i = 0; i<array.length; i++){
            array[i] = 'a';
        }
        String description = String.valueOf(array);

        String startDateString = "01-01-2000";
        String durationString = "21";

        try {
            buildHabitInstance.build(title, description, startDateString, durationString);
            fail();
        } catch (BuildException e) {
            assertThat(R.string.description_max_build_instance_exception, is(e.getMessageRes()));
        }
    }

    @Test
    public void buildErrorParseStartDate(){
        String title = "title";
        String description = "description";

        String startDateString = "1 день в 2000 году";
        String durationString = "21";

        try {
            buildHabitInstance.build(title, description, startDateString, durationString);
            fail();
        } catch (BuildException e) {
            assertThat(R.string.parsing_start_date_build_instance_exception, is(e.getMessageRes()));
        }
    }

    @Test
    public void buildErrorParseDuration(){
        String title = "title";
        String description = "description";

        String startDateString = "01-01-2000";
        String durationString = "Двадцать один день";

        try {
            buildHabitInstance.build(title, description, startDateString, durationString);
            fail();
        } catch (BuildException e) {
            assertThat(R.string.parsing_duration_build_instance_exception, is(e.getMessageRes()));
        }
    }

    @Test
    public void buildErrorMinDuration(){
        String title = "title";
        String description = "description";
        String startDateString = "01-01-2000";
        String durationString = "3";

        try {
            buildHabitInstance.build(title, description, startDateString, durationString);
            fail();
        } catch (BuildException e) {
            assertThat(R.string.duration_min_build_instance_exception, is(e.getMessageRes()));
        }
    }

    @Test
    public void buildErrorMaxDuration(){
        String title = "title";
        String description = "description";
        String startDateString = "01-01-2000";
        String durationString = "400";

        try {
            buildHabitInstance.build(title, description, startDateString, durationString);
            fail();
        } catch (BuildException e) {
            assertThat(R.string.duration_max_build_instance_exception, is(e.getMessageRes()));
        }
    }
}