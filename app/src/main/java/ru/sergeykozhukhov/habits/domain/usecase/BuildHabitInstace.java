package ru.sergeykozhukhov.habits.domain.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.IInreractor.IBuildHabitInstance;
import ru.sergeykozhukhov.habits.model.domain.Habit;
import ru.sergeykozhukhov.habits.model.exception.BuildException;

public class BuildHabitInstace implements IBuildHabitInstance {

    private static final int maxTitle = 20;
    private static final int minDuration = 5;
    private static final int maxDuration = 400;

    @NonNull
    @Override
    public Habit build(@Nullable String title,
                       @Nullable String description,
                       @Nullable String startDate,
                       @Nullable String duration) throws BuildException {
        if (title == null || description == null || startDate == null|| duration == null)
            throw new BuildException(R.string.build_instance_exception);
        if (title.length() >maxTitle)
            throw new BuildException(R.string.build_instance_exception);
        if (title.length() < 2 || description.length() < 2){
            throw new BuildException(R.string.build_instance_exception);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy", // шаблон форматирования
                Locale.getDefault() // язык отображения (получение языка по-умолчанию)
        );

        Date date;
        try {
            date = dateFormat.parse(startDate);
        } catch (ParseException e) {
            throw new BuildException(R.string.build_instance_exception, e);
        }

        int durationInt;
        try {
            durationInt = Integer.parseInt(duration);
            if (durationInt > maxDuration || durationInt < minDuration){
                throw new BuildException(R.string.build_instance_exception);
            }
        } catch (NumberFormatException e) {
            throw new BuildException(R.string.build_instance_exception, e);
        }

        Habit habit = new Habit();
        habit.setTitle(title);
        habit.setDescription(description);
        habit.setStartDate(date);
        habit.setDuration(durationInt);

        return habit;
    }
}
