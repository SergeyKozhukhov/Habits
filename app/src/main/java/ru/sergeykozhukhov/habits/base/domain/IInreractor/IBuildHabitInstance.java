package ru.sergeykozhukhov.habits.base.domain.IInreractor;

import java.text.ParseException;
import java.util.Date;

import ru.sergeykozhukhov.habits.base.model.domain.Habit;

public interface IBuildHabitInstance {
    Habit build(String title, String description, String startDate, String duration);
}
