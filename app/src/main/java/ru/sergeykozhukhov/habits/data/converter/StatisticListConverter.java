package ru.sergeykozhukhov.habits.data.converter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.sergeykozhukhov.habits.domain.IConverter;
import ru.sergeykozhukhov.habits.model.data.StatisticData;
import ru.sergeykozhukhov.habits.model.domain.Statistic;

/**
 * Конвертер списка Statistic моделей между data и domain слоями
 */
public class StatisticListConverter implements IConverter<List<StatisticData>, List<Statistic>> {

    /**
     * Конвертирование в список моделей domain слоя
     *
     * @param statisticDataList список Statistic моделей data слоя
     * @return список Statistic моделей domain слоя
     */
    @NonNull
    @Override
    public List<Statistic> convertTo(@NonNull List<StatisticData> statisticDataList) {
        List<Statistic> statisticList = new ArrayList<>(statisticDataList.size());
        for (StatisticData statisticData : statisticDataList) {
            statisticList.add(new Statistic(
                    statisticData.getIdHabit(),
                    statisticData.getTitle(),
                    statisticData.getDuration(),
                    statisticData.getCurrentQuantity()));
        }
        return statisticList;
    }

    /**
     * Конвертирование в список моделей data слоя
     *
     * @param statisticList список Statistic моделей domain слоя
     * @return список Statistic моделей data слоя
     */
    @NonNull
    @Override
    public List<StatisticData> convertFrom(@NonNull List<Statistic> statisticList) {
        List<StatisticData> statisticDataList = new ArrayList<>(statisticList.size());
        for (Statistic statistic : statisticList) {
            statisticDataList.add(new StatisticData(
                    statistic.getIdHabit(),
                    statistic.getTitle(),
                    statistic.getDuration(),
                    statistic.getCurrentQuantity()));
        }
        return statisticDataList;
    }
}
