package ru.sergeykozhukhov.habits.data.converter;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class DateConverterTest {

    private DateConverter dateConverter;

    @Before
    public void setUp() throws Exception {
        dateConverter = new DateConverter();
    }

    @Test
    public void fromDate() {

        // arrange

        Date dateInput = new Date();
        long millisecondsExpectedOutput = dateInput.getTime();


        // act

        long millisecondsOutput = dateConverter.fromDate(dateInput);

        // assert

        assertThat(millisecondsOutput, is(millisecondsExpectedOutput));
    }

    @Test
    public void toDate() {

        // arrange

        long millisecondsInput = 0;
        Date dateExpectedOutput = new Date(millisecondsInput);

        // act

        Date dateOutput = dateConverter.toDate(millisecondsInput);

        // assert

        assertThat(dateOutput, is(dateExpectedOutput));

    }
}