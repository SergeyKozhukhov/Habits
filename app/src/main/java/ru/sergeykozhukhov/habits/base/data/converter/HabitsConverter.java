package ru.sergeykozhukhov.habits.base.data.converter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.sergeykozhukhov.habits.base.data.model.HabitData;
import ru.sergeykozhukhov.habits.base.domain.IConverter;
import ru.sergeykozhukhov.habits.base.domain.model.Habit;

public class HabitsConverter implements IConverter <List<HabitData>, List<Habit>> {

    @NonNull
    @Override
    public List<Habit> convertTo(@NonNull List<HabitData> habitDataList) {
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

    @NonNull
    @Override
    public List<HabitData> convertFrom(@NonNull List<Habit> habitList) {
        List<HabitData> result = new ArrayList<>();
        for (Habit habit: habitList) {
            result.add(new HabitData(
                    habit.getIdHabit(),
                    habit.getIdHabitServer(),
                    habit.getTitle(),
                    habit.getDescription(),
                    habit.getStartDate(),
                    habit.getDuration()
            ));
        }
        return result;
    }
}
