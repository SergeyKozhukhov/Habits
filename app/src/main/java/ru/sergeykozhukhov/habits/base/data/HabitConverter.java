package ru.sergeykozhukhov.habits.base.data;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.sergeykozhukhov.habits.base.data.model.HabitData;
import ru.sergeykozhukhov.habits.base.domain.IConverter;
import ru.sergeykozhukhov.habits.base.domain.model.Habit;

public class HabitConverter implements IConverter <List<HabitData>, List<Habit>> {

    @NonNull
    @Override
    public List<Habit> convert(@NonNull List<HabitData> habitDataList) {
        List<Habit> result = new ArrayList<>();
        for (HabitData habitData: habitDataList) {
            result.add(new Habit(
                    habitData.getIdHabit(),
                    habitData.getIdHabitServer(),
                    habitData.getTitle(),
                    habitData.getDescription(),
                    habitData.getStartDate(),
                    habitData.getDuration()
            ));
        }
        return result;
    }
}
