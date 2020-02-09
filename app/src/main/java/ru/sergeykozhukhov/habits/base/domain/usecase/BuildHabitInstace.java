package ru.sergeykozhukhov.habits.base.domain.usecase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.sergeykozhukhov.habits.base.domain.IInreractor.IBuildHabitInstance;
import ru.sergeykozhukhov.habits.base.model.domain.Habit;

public class BuildHabitInstace implements IBuildHabitInstance {

    @Override
    public Habit build(String title, String description, String startDate, String duration) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy", // шаблон форматирования
                Locale.getDefault() // язык отображения (получение языка по-умолчанию)
        );
        Date date = null;
        try {
            date = dateFormat.parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

        int durationInt;
        try {
            durationInt = Integer.valueOf(duration);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }

        Habit habit = new Habit();
        habit.setTitle(title);
        habit.setDescription(description);
        habit.setStartDate(date);
        habit.setDuration(durationInt);

        return habit;
    }
}
