package ru.sergeykozhukhov.habits.domain.usecase;

import androidx.annotation.NonNull;

import io.reactivex.Single;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.domain.iusecase.IBuildHabitInstance;
import ru.sergeykozhukhov.habits.domain.iusecase.IInsertHabitDbInteractor;
import ru.sergeykozhukhov.habits.model.domain.Habit;
import ru.sergeykozhukhov.habits.model.domain.exception.BuildException;
import ru.sergeykozhukhov.habits.model.domain.exception.InsertDbException;

public class InsertHabitDbInteractor implements IInsertHabitDbInteractor {

    private final IHabitsDatabaseRepository habitsRepository;
    private final IBuildHabitInstance buildHabitInstance;

    public InsertHabitDbInteractor(
            @NonNull IHabitsDatabaseRepository habitsRepository,
            @NonNull IBuildHabitInstance buildHabitInstance) {
        this.habitsRepository = habitsRepository;
        this.buildHabitInstance = buildHabitInstance;
    }

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
