package ru.sergeykozhukhov.habits.domain.usecaseimpl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.usecase.IBuildHabitInteractor;
import ru.sergeykozhukhov.habits.model.domain.Habit;
import ru.sergeykozhukhov.habits.model.domain.exception.BuildException;

/**
 * Реализация интерактора получения нового экземпляра класса Habit
 */
public class BuildHabitInteractor implements IBuildHabitInteractor {

    /**
     * Минимальная длинна названия
     */
    private static final int TITLE_MIN = 2;

    /**
     * Максимальная длинна названия
     */
    private static final int TITLE_MAX = 20;

    /**
     * Минимальная длинна описания
     */
    private static final int DESCRIPTION_MIN = 10;

    /**
     * Максимальная длинна описания
     */
    private static final int DESCRIPTION_MAX = 200;

    /**
     * Минимальная продолжительность (в кол-ве дней)
     */
    private static final int DURATION_MIN = 5;

    /**
     * Максимальная продолжительность (в кол-ве дней)
     */
    private static final int DURATION_MAX = 400;

    /**
     * Шаблон для получения даты из строки
     */
    private static final String PATTERN = "dd-MM-yyyy";

    /**
     * Получение нового экземпляра класса Habit
     *
     * @param title       название
     * @param description описание
     * @param startDate   дата начала
     * @param duration    продолжительность кол-ве в дней
     * @return экземляр класса Habit
     * @throws BuildException исключение при ошибке создания экземпляра из-за неверных парметров
     */
    @NonNull
    @Override
    public Habit build(@Nullable String title,
                       @Nullable String description,
                       @Nullable String startDate,
                       @Nullable String duration) throws BuildException {

        if (title == null || description == null || startDate == null || duration == null)
            throw new BuildException(R.string.null_data_build_instance_exception);
        if (title.length() <= TITLE_MIN)
            throw new BuildException(R.string.title_min_build_instance_exception);
        if (title.length() >= TITLE_MAX)
            throw new BuildException(R.string.title_max_build_instance_exception);
        if (description.length() <= DESCRIPTION_MIN)
            throw new BuildException(R.string.description_min_build_instance_exception);
        if (description.length() >= DESCRIPTION_MAX)
            throw new BuildException(R.string.description_max_build_instance_exception);

        SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN, Locale.getDefault());
        Date date;
        try {
            date = dateFormat.parse(startDate);
        } catch (ParseException e) {
            throw new BuildException(R.string.parsing_start_date_build_instance_exception, e);
        }

        int durationInt;
        try {
            durationInt = Integer.parseInt(duration);
            if (durationInt <= DURATION_MIN)
                throw new BuildException(R.string.duration_min_build_instance_exception);
            if (durationInt >= DURATION_MAX)
                throw new BuildException(R.string.duration_max_build_instance_exception);
        } catch (NumberFormatException e) {
            throw new BuildException(R.string.parsing_duration_build_instance_exception, e);
        }

        return new Habit(title, description, date, durationInt);
    }
}
