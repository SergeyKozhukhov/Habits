package ru.sergeykozhukhov.habits.base.domain.IInreractor.builder;

import java.text.ParseException;
import java.util.Date;

import ru.sergeykozhukhov.habits.base.model.domain.Habit;
import ru.sergeykozhukhov.habits.base.model.exception.BuildException;

public interface IBuildHabitInstance {
    Habit build(String title, String description, String startDate, String duration) throws BuildException;
}
