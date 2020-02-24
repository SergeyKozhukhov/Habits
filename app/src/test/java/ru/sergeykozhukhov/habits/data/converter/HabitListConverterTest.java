package ru.sergeykozhukhov.habits.data.converter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import ru.sergeykozhukhov.habits.GeneratorData;
import ru.sergeykozhukhov.habits.model.data.HabitData;
import ru.sergeykozhukhov.habits.model.domain.Habit;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HabitListConverterTest {

    private GeneratorData generatorData;

    private HabitListConverter habitListConverter;

    @Mock
    private HabitConverter habitConverter;

    @Before
    public void setUp() {
        habitListConverter = new HabitListConverter(habitConverter);
        generatorData = new GeneratorData();
    }

    @Test
    public void convertTo() {
        List<HabitData> habitDataListInput = generatorData.createHabitDataList(1,2);
        List<Habit> habitListExpected = generatorData.createHabitList(1,2);

        when(habitConverter.convertTo(habitDataListInput.get(0))).thenReturn(habitListExpected.get(0));
        when(habitConverter.convertTo(habitDataListInput.get(1))).thenReturn(habitListExpected.get(1));

        List<Habit> habitListOutput = habitListConverter.convertTo(habitDataListInput);

        assertThat(habitListOutput, is(habitListExpected));

        verify(habitConverter).convertTo(habitDataListInput.get(0));
        verify(habitConverter).convertTo(habitDataListInput.get(1));
        verifyNoMoreInteractions(habitConverter);
    }

    @Test
    public void convertFrom() {
        List<Habit> habitListInput = generatorData.createHabitList(1,2);
        List<HabitData> habitDataListExpected= generatorData.createHabitDataList(1,2);

        when(habitConverter.convertFrom(habitListInput.get(0))).thenReturn(habitDataListExpected.get(0));
        when(habitConverter.convertFrom(habitListInput.get(1))).thenReturn(habitDataListExpected.get(1));

        List<HabitData> habitDataListOutput = habitListConverter.convertFrom(habitListInput);

        assertThat(habitDataListOutput, is(habitDataListExpected));

        verify(habitConverter).convertFrom(habitListInput.get(0));
        verify(habitConverter).convertFrom(habitListInput.get(1));
        verifyNoMoreInteractions(habitConverter);
    }
}