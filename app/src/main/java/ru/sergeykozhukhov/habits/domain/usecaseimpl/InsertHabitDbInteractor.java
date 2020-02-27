package ru.sergeykozhukhov.habits.domain.usecaseimpl;

import androidx.annotation.NonNull;

import io.reactivex.Single;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.domain.usecase.IBuildHabitInteractor;
import ru.sergeykozhukhov.habits.domain.usecase.IInsertHabitDbInteractor;
import ru.sergeykozhukhov.habits.model.domain.Habit;
import ru.sergeykozhukhov.habits.model.domain.exception.BuildException;
import ru.sergeykozhukhov.habits.model.domain.exception.InsertDbException;

/**
 * Реализация интерактора добавления привычки в базу данных
 */
public class InsertHabitDbInteractor implements IInsertHabitDbInteractor {

    /**
     * Репозиторий (база данных)
     */
    private final IHabitsDatabaseRepository habitsRepository;

    /**
     * Интерактор получения нового экземпляра класса Habit
     */
    private final IBuildHabitInteractor buildHabitInstance;

    public InsertHabitDbInteractor(
            @NonNull IHabitsDatabaseRepository habitsRepository,
            @NonNull IBuildHabitInteractor buildHabitInstance) {
        this.habitsRepository = habitsRepository;
        this.buildHabitInstance = buildHabitInstance;
    }

    /**
     * Добавление привычки в список сохраненных привычек
     *
     * @param title       название
     * @param description описание
     * @param startDate   дата начала
     * @param duration    продолжительность в кол-ве дней
     * @return single с id добавленной привычки
     */
    @NonNull
    @Override
    public Single<Long> insertHabit(String title, String description, String startDate, String duration) {
        Habit habit;
        try {
            habit = buildHabitInstance.build(title, description, startDate, duration);
        } catch (BuildException e) {
            return Single.error(e);
        }
        return habitsRepository.insertHabit(habit)
                .onErrorResumeNext(throwable ->
                        Single.error(new InsertDbException(R.string.insert_db_exception, throwable)));
    }
}
