package ru.sergeykozhukhov.habits.base.domain.usecase.util;

import androidx.annotation.NonNull;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.base.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.provider.IBuildHabitInstance;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.IInsertHabitDbInteractor;
import ru.sergeykozhukhov.habits.base.model.domain.Habit;
import ru.sergeykozhukhov.habits.base.model.exception.BuildException;
import ru.sergeykozhukhov.habits.base.model.exception.InsertDbException;

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
