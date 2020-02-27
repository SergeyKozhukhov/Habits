package ru.sergeykozhukhov.habits.data.converter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import ru.sergeykozhukhov.habits.GeneratorData;
import ru.sergeykozhukhov.habits.model.data.HabitWithProgressesData;
import ru.sergeykozhukhov.habits.model.domain.HabitWithProgresses;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit тесты на {@link HabitWithProgressesListConverter}
 **/
@RunWith(MockitoJUnitRunner.class)
public class HabitWithProgressesListConverterTest {

    /**
     * Генератор данных для тестирования
     */
    private GeneratorData generatorData;

    /**
     * Конвертер списка HabitWithProgresses моделей между data и domain слоями
     */
    private HabitWithProgressesListConverter habitWithProgressesListConverter;

    /**
     * Конвертер HabitWithProgresses модели между data и domain слоями
     */
    @Mock
    private HabitWithProgressesConverter habitWithProgressesConverter;

    @Before
    public void setUp() {
        habitWithProgressesListConverter = new HabitWithProgressesListConverter(habitWithProgressesConverter);
        generatorData = new GeneratorData();
    }

    /**
     * Тестирование на правильность конвертации в соответствующий экземпляр domain слоя
     */
    @Test
    public void convertTo() {
        List<HabitWithProgressesData> habitWithProgressesDataList = generatorData.createHabitWithProgressesDataList();
        List<HabitWithProgresses> habitWithProgressesListExpected = generatorData.createHabitWithProgressesList();

        when(habitWithProgressesConverter.convertTo(habitWithProgressesDataList.get(0))).thenReturn(habitWithProgressesListExpected.get(0));
        when(habitWithProgressesConverter.convertTo(habitWithProgressesDataList.get(1))).thenReturn(habitWithProgressesListExpected.get(1));

        List<HabitWithProgresses> habitWithProgressesListOutput = habitWithProgressesListConverter.convertTo(habitWithProgressesDataList);

        assertThat(habitWithProgressesListOutput, is(habitWithProgressesListExpected));

        verify(habitWithProgressesConverter).convertTo(habitWithProgressesDataList.get(0));
        verify(habitWithProgressesConverter).convertTo(habitWithProgressesDataList.get(1));
    }

    /**
     * Тестирование на правильность конвертации в соответствующий экземпляр data слоя
     */
    @Test
    public void convertFrom() {
        List<HabitWithProgresses> habitWithProgressesList = generatorData.createHabitWithProgressesList();
        List<HabitWithProgressesData> habitWithProgressesDataListExpected = generatorData.createHabitWithProgressesDataList();

        when(habitWithProgressesConverter.convertFrom(habitWithProgressesList.get(0))).thenReturn(habitWithProgressesDataListExpected.get(0));
        when(habitWithProgressesConverter.convertFrom(habitWithProgressesList.get(1))).thenReturn(habitWithProgressesDataListExpected.get(1));

        List<HabitWithProgressesData> habitWithProgressesDataListOutput = habitWithProgressesListConverter.convertFrom(habitWithProgressesList);

        assertThat(habitWithProgressesDataListOutput, is(habitWithProgressesDataListExpected));

        verify(habitWithProgressesConverter).convertFrom(habitWithProgressesList.get(0));
        verify(habitWithProgressesConverter).convertFrom(habitWithProgressesList.get(1));
    }
}