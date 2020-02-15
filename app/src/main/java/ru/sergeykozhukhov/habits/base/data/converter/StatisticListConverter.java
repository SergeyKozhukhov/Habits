package ru.sergeykozhukhov.habits.base.data.converter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.sergeykozhukhov.habits.base.domain.IConverter;
import ru.sergeykozhukhov.habits.base.model.data.StatisticData;
import ru.sergeykozhukhov.habits.base.model.domain.Statistic;

public class StatisticListConverter implements IConverter<List<StatisticData>, List<Statistic>> {

    private final StatisticConverter statisticConverter;

    public StatisticListConverter(@NonNull StatisticConverter statisticConverter) {
        this.statisticConverter = statisticConverter;
    }


    @NonNull
    @Override
    public List<Statistic> convertTo(@NonNull List<StatisticData> statisticDataList) {
        List<Statistic> statisticList = new ArrayList<>(statisticDataList.size());
        for (StatisticData statisticData : statisticDataList){
            statisticList.add(statisticConverter.convertTo(statisticData));
        }
        return statisticList;
    }

    @NonNull
    @Override
    public List<StatisticData> convertFrom(@NonNull List<Statistic> statisticList) {
        List<StatisticData> statisticDataList = new ArrayList<>(statisticList.size());
        for (Statistic statistic : statisticList){
            statisticDataList.add(statisticConverter.convertFrom(statistic));
        }
        return statisticDataList;
    }
}
