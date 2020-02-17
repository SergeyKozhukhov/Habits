package ru.sergeykozhukhov.habits.domain.usecase;

import androidx.annotation.NonNull;

import io.reactivex.Completable;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.domain.IInreractor.IDeleteAllHabitsDbInteractor;
import ru.sergeykozhukhov.habits.model.exception.DeleteFromDbException;

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
