package ru.sergeykozhukhov.habits.data.converter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import ru.sergeykozhukhov.habits.GeneratorData;
import ru.sergeykozhukhov.habits.model.data.StatisticData;
import ru.sergeykozhukhov.habits.model.domain.Statistic;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class StatisticListConverterTest {

    private GeneratorData generatorData;
    private StatisticListConverter statisticListConverter;

    @Before
    public void setUp() throws Exception {
        statisticListConverter = new StatisticListConverter();
        generatorData = new GeneratorData();
    }

    @Test
    public void convertTo() {
        List<StatisticData> statisticDataList = generatorData.createStatisticDataList();
        List<Statistic> statisticListExpected = generatorData.createStatisticList();

        List<Statistic> statisticListOutput = statisticListConverter.convertTo(statisticDataList);

        assertThat(statisticListOutput, is(statisticListExpected));
    }

    @Test
    public void convertFrom() {
        List<Statistic> statisticList = generatorData.createStatisticList();
        List<StatisticData> statisticDataListExpected = generatorData.createStatisticDataList();

        List<StatisticData> statisticDataListOutput = statisticListConverter.convertFrom(statisticList);

        assertThat(statisticDataListOutput, is(statisticDataListExpected));
    }
}