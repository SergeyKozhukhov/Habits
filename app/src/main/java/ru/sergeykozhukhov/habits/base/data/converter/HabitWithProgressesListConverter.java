package ru.sergeykozhukhov.habits.base.data.converter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.sergeykozhukhov.habits.base.domain.IConverter;
import ru.sergeykozhukhov.habits.base.model.data.HabitWithProgressesData;
import ru.sergeykozhukhov.habits.base.model.domain.HabitWithProgresses;

public class HabitWithProgressesListConverter implements IConverter<List<HabitWithProgressesData>, List<HabitWithProgresses>> {

    private final HabitWithProgressesConverter habitWithProgressesConverter;

    public HabitWithProgressesListConverter(@NonNull HabitWithProgressesConverter habitWithProgressesConverter) {
        this.habitWithProgressesConverter = habitWithProgressesConverter;
    }

    @NonNull
    @Override
    public List<HabitWithProgresses> convertTo(@NonNull List<HabitWithProgressesData> habitWithProgressesDataList) {

        List<HabitWithProgresses> habitWithProgressesList = new ArrayList<>(habitWithProgressesDataList.size());
        for (HabitWithProgressesData habitWithProgressesData : habitWithProgressesDataList){
            habitWithProgressesList.add(habitWithProgressesConverter.convertTo(habitWithProgressesData));
        }
        return habitWithProgressesList;

    }

    @NonNull
    @Override
    public List<HabitWithProgressesData> convertFrom(@NonNull List<HabitWithProgresses> habitWithProgressesList) {

        List<HabitWithProgressesData> habitWithProgressesDataList = new ArrayList<>(habitWithProgressesList.size());
        for (HabitWithProgresses habitWithProgresses : habitWithProgressesList){
            habitWithProgressesDataList.add(habitWithProgressesConverter.convertFrom(habitWithProgresses));
        }
        return habitWithProgressesDataList;
    }
}
