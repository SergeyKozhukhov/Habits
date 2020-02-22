package ru.sergeykozhukhov.habits.data.converter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.sergeykozhukhov.habits.domain.IConverter;
import ru.sergeykozhukhov.habits.model.data.StatisticData;
import ru.sergeykozhukhov.habits.model.domain.Chart;
import ru.sergeykozhukhov.habits.model.domain.Statistic;

public class ChartListConverter implements IConverter<List<StatisticData>, List<Chart>> {
    @NonNull
    @Override
    public List<Chart> convertTo(@NonNull List<StatisticData> statisticDataList) {
        List<Chart> chartList = new ArrayList<>(statisticDataList.size());
        for (StatisticData statisticData : statisticDataList){
            chartList.add(new Chart(
                    statisticData.getIdHabit(),
                    statisticData.getTitle(),
                    100f*(float)statisticData.getCurrentQuantity()/(float)statisticData.getDuration()
            ));
        }
        return chartList;
    }

    @NonNull
    @Override
    public List<StatisticData> convertFrom(@NonNull List<Chart> charts) {
        return null;
    }
}
