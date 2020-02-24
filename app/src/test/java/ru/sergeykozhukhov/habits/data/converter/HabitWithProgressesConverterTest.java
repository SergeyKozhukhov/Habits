package ru.sergeykozhukhov.habits.data.converter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import ru.sergeykozhukhov.habits.GeneratorData;
import ru.sergeykozhukhov.habits.model.data.HabitData;
import ru.sergeykozhukhov.habits.model.data.HabitWithProgressesData;
import ru.sergeykozhukhov.habits.model.data.ProgressData;
import ru.sergeykozhukhov.habits.model.domain.Habit;
import ru.sergeykozhukhov.habits.model.domain.HabitWithProgresses;
import ru.sergeykozhukhov.habits.model.domain.Progress;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HabitWithProgressesConverterTest {

    private GeneratorData generatorData;

    private HabitWithProgressesConverter habitWithProgressesConverter;
    @Mock
    private HabitConverter habitConverter;
    @Mock
    private ProgressListConverter progressListConverter;

    @Before
    public void setUp() {
        habitWithProgressesConverter = new HabitWithProgressesConverter(habitConverter, progressListConverter);
        generatorData = new GeneratorData();
    }

    @Test
    public void convertTo() {
        HabitData habitData = generatorData.createHabitData(1);
        Habit habit = generatorData.createHabit(1);
        List<ProgressData> progressDataList = generatorData.createProgressDataList(1,1,2);
        List<Progress> progressList = generatorData.createProgressList(1,1,2);
        HabitWithProgressesData habitWithProgressesDataInput = new HabitWithProgressesData(habitData, progressDataList);
        HabitWithProgresses habitWithProgressesExpected = new HabitWithProgresses(habit, progressList);

        when(habitConverter.convertTo(habitData)).thenReturn(habit);
        when(progressListConverter.convertTo(progressDataList)).thenReturn(progressList);

        HabitWithProgresses habitWithProgressesOutput = habitWithProgressesConverter.convertTo(habitWithProgressesDataInput);

        assertThat(habitWithProgressesOutput, is(habitWithProgressesExpected));

        verify(habitConverter).convertTo(habitData);
        verify(progressListConverter).convertTo(progressDataList);
    }

    @Test
    public void convertFrom() {
        Habit habit = generatorData.createHabit(1);
        HabitData habitData = generatorData.createHabitData(1);
        List<Progress> progressList = generatorData.createProgressList(1,1,2);
        List<ProgressData> progressDataList = generatorData.createProgressDataList(1,1,2);
        HabitWithProgresses habitWithProgressesInput = new HabitWithProgresses(habit, progressList);
        HabitWithProgressesData habitWithProgressesDataExpected = new HabitWithProgressesData(habitData, progressDataList);

        when(habitConverter.convertFrom(habit)).thenReturn(habitData);
        when(progressListConverter.convertFrom(progressList)).thenReturn(progressDataList);

        HabitWithProgressesData habitWithProgressesDataOutput = habitWithProgressesConverter.convertFrom(habitWithProgressesInput);

        assertThat(habitWithProgressesDataOutput, is(habitWithProgressesDataExpected));

        verify(habitConverter).convertFrom(habit);
        verify(progressListConverter).convertFrom(progressList);
    }
}