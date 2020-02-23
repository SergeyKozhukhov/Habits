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

public class BuildHabitInstance implements IBuildHabitInstance {

    private static final int titleMin = 2;
    private static final int titleMax = 20;
    private static final int descriptionMin = 10;
    private static final int descriptionMax = 200;
    private static final int durationMin = 5;
    private static final int durationMax = 400;

    private static final String pattern = "dd-MM-yyyy";

    @NonNull
    @Override
    public Habit build(@Nullable String title,
                       @Nullable String description,
                       @Nullable String startDate,
                       @Nullable String duration) throws BuildException {

        if (title == null || description == null || startDate == null || duration == null)
            throw new BuildException(R.string.null_data_build_instance_exception);
        if (title.length() <= titleMin)
            throw new BuildException(R.string.title_min_build_instance_exception);
        if (title.length() >= titleMax)
            throw new BuildException(R.string.title_max_build_instance_exception);
        if (description.length() <= descriptionMin)
            throw new BuildException(R.string.description_min_build_instance_exception);
        if (description.length() >= descriptionMax)
            throw new BuildException(R.string.description_max_build_instance_exception);

        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());

        Date date;
        try {
            date = dateFormat.parse(startDate);
        } catch (ParseException e) {
            throw new BuildException(R.string.parsing_start_date_build_instance_exception, e);
        }

        int durationInt;
        try {
            durationInt = Integer.parseInt(duration);
            if (durationInt <= durationMin)
                throw new BuildException(R.string.duration_min_build_instance_exception);
            if (durationInt >= durationMax)
                throw new BuildException(R.string.duration_max_build_instance_exception);
        } catch (NumberFormatException e) {
            throw new BuildException(R.string.parsing_duration_build_instance_exception, e);
        }

        return new Habit(title, description, date, durationInt);
    }
}
