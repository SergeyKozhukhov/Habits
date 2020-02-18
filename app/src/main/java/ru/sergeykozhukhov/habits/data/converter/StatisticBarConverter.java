package ru.sergeykozhukhov.habits.data.converter;

import androidx.annotation.NonNull;

import com.github.mikephil.charting.data.BarData;

import ru.sergeykozhukhov.habits.domain.IConverter;
import ru.sergeykozhukhov.habits.model.data.StatisticData;

public class StatisticBarConverter implements IConverter<StatisticData, BarData> {

    @NonNull
    @Override
    public BarData convertTo(@NonNull StatisticData statisticData) {
        return null;
    }

    @NonNull
    @Override
    public StatisticData convertFrom(@NonNull BarData barData) {
        return null;
    }
}
