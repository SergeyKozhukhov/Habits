package ru.sergeykozhukhov.habits.data.converter;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import ru.sergeykozhukhov.habits.GeneratorData;
import ru.sergeykozhukhov.habits.model.data.ProgressData;
import ru.sergeykozhukhov.habits.model.domain.Progress;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Unit тесты на {@link ProgressListConverter}
 **/
public class ProgressListConverterTest {

    /**
     * Генератор данных для тестирования
     */
    private GeneratorData generatorData;

    /**
     * Конвертер списка Progresses моделей между data и domain слоями
     */
    private ProgressListConverter progressListConverter;

    @Before
    public void setUp() {
        progressListConverter = new ProgressListConverter();
        generatorData = new GeneratorData();
    }

    /**
     * Тестирование на правильность конвертации в соответствующий экземпляр domain слоя
     */
    @Test
    public void convertTo() {
        List<ProgressData> progressDataListInput = generatorData.createProgressDataList(1, 1, 3);
        List<Progress> progressListExpected = generatorData.createProgressList(1, 1, 3);

        List<Progress> progressListOutput = progressListConverter.convertTo(progressDataListInput);

        assertThat(progressListOutput, is(progressListExpected));
    }

    /**
     * Тестирование на правильность конвертации в соответствующий экземпляр data слоя
     */
    @Test
    public void convertFrom() {
        List<Progress> progressListInput = generatorData.createProgressList(1, 1, 3);
        List<ProgressData> progressDataListExpected = generatorData.createProgressDataList(1, 1, 3);

        List<ProgressData> progressDataListOutput = progressListConverter.convertFrom(progressListInput);

        assertThat(progressDataListOutput, is(progressDataListExpected));
    }
}