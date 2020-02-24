package ru.sergeykozhukhov.habits.data.converter;

import org.junit.Before;
import org.junit.Test;

import ru.sergeykozhukhov.habits.GeneratorData;
import ru.sergeykozhukhov.habits.model.data.HabitData;
import ru.sergeykozhukhov.habits.model.domain.Habit;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class HabitConverterTest {

    private GeneratorData generatorData;

    private HabitConverter habitConverter;

    @Before
    public void setUp() {
        habitConverter = new HabitConverter();
        generatorData = new GeneratorData();
    }

    @Test
    public void convertTo() {
        HabitData habitDataInput = generatorData.createHabitData(1);
        Habit habitExpected = generatorData.createHabit(1);

        Habit habitOutput = habitConverter.convertTo(habitDataInput);

        assertThat(habitOutput, is(habitExpected));
    }

    @Test
    public void convertFrom() {
        Habit habitInput = generatorData.createHabit(1);
        HabitData habitDataExpected = generatorData.createHabitData(1);

        HabitData habitDataOutput = habitConverter.convertFrom(habitInput);

        assertThat(habitDataOutput, is(habitDataExpected));
    }
}