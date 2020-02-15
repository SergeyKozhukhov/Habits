package ru.sergeykozhukhov.habits.base.data.converter;

import androidx.annotation.NonNull;

import ru.sergeykozhukhov.habits.base.domain.IConverter;
import ru.sergeykozhukhov.habits.base.model.data.StatisticData;
import ru.sergeykozhukhov.habits.base.model.domain.Statistic;

public class StatisticConverter implements IConverter<StatisticData, Statistic> {
    @NonNull
    @Override
    public Statistic convertTo(@NonNull StatisticData statisticData) {
        return new Statistic(
                statisticData.getIdHabit(),
                statisticData.getTitle(),
                statisticData.getDuration(),
                statisticData.getCurrentQuantity()
        );
    }

    @NonNull
    @Override
    public StatisticData convertFrom(@NonNull Statistic statistic) {
        return new StatisticData(
                statistic.getIdHabit(),
                statistic.getTitle(),
                statistic.getDuration(),
                statistic.getCurrentQuantity()
        );
    }
}
