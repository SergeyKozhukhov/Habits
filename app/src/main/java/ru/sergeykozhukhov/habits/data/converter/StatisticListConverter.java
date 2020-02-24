package ru.sergeykozhukhov.habits.data.converter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.sergeykozhukhov.habits.domain.IConverter;
import ru.sergeykozhukhov.habits.model.data.StatisticData;
import ru.sergeykozhukhov.habits.model.domain.Statistic;

public class StatisticListConverter implements IConverter<List<StatisticData>, List<Statistic>> {

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
