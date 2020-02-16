package ru.sergeykozhukhov.habits.base.domain.usecase;

import androidx.annotation.NonNull;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.functions.Function;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.base.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.IDeleteAllHabitsDbInteractor;
import ru.sergeykozhukhov.habits.base.model.exception.DeleteFromDbException;

public class DeleteAllHabitsDbInteractor implements IDeleteAllHabitsDbInteractor {
    private final IHabitsDatabaseRepository habitsRepository;

    public DeleteAllHabitsDbInteractor(IHabitsDatabaseRepository habitsRepository) {
        this.habitsRepository = habitsRepository;
    }

    @NonNull
    @Override
    public Completable deleteAllHabits() {

        return habitsRepository.deleteAllHabits()
                .onErrorResumeNext(throwable ->
                        Completable.error(new DeleteFromDbException(R.string.delete_from_db_exception, throwable)));
    }
}
