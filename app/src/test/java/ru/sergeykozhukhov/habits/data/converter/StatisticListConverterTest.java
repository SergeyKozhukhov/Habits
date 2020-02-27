package ru.sergeykozhukhov.habits.data.converter;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import ru.sergeykozhukhov.habits.GeneratorData;
import ru.sergeykozhukhov.habits.model.data.StatisticData;
import ru.sergeykozhukhov.habits.model.domain.Statistic;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Unit тесты на {@link StatisticListConverter}
 **/
public class StatisticListConverterTest {

    /**
     * Генератор данных для тестирования
     */
    private GeneratorData generatorData;

    /**
     * Конвертер списка Statistic моделей между data и domain слоями
     */
    private StatisticListConverter statisticListConverter;

    @Before
    public void setUp() {
        statisticListConverter = new StatisticListConverter();
        generatorData = new GeneratorData();
    }

    /**
     * Тестирование на правильность конвертации в соответствующий экземпляр domain слоя
     */
    @Test
    public void convertTo() {
        List<StatisticData> statisticDataList = generatorData.createStatisticDataList();
        List<Statistic> statisticListExpected = generatorData.createStatisticList();

        List<Statistic> statisticListOutput = statisticListConverter.convertTo(statisticDataList);

        assertThat(statisticListOutput, is(statisticListExpected));
    }

    /**
     * Тестирование на правильность конвертации в соответствующий экземпляр data слоя
     */
    @Test
    public void convertFrom() {
        List<Statistic> statisticList = generatorData.createStatisticList();
        List<StatisticData> statisticDataListExpected = generatorData.createStatisticDataList();

        List<StatisticData> statisticDataListOutput = statisticListConverter.convertFrom(statisticList);

        assertThat(statisticDataListOutput, is(statisticDataListExpected));
    }
}