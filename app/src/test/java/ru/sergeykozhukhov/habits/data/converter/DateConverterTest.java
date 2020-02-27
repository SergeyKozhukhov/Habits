package ru.sergeykozhukhov.habits.data.converter;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Unit тесты на {@link DateConverter}
 **/
public class DateConverterTest {

    /**
     * Конвертер даты для чтения/записи Date параметра из/в БД
     */
    private DateConverter dateConverter;

    @Before
    public void setUp() {
        dateConverter = new DateConverter();
    }

    /**
     * Тестирование на правильоть конвертации в формат доступный базе данных
     */
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

    /**
     * Тестирование на правильоть Конвертации в Date представление даты из базы данных
     */
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