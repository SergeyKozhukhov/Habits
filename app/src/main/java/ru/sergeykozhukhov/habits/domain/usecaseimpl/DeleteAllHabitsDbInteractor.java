package ru.sergeykozhukhov.habits.domain.usecaseimpl;

import androidx.annotation.NonNull;

import io.reactivex.Completable;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.domain.usecase.IDeleteAllHabitsDbInteractor;
import ru.sergeykozhukhov.habits.model.domain.exception.DeleteFromDbException;

/**
 * Реализация интерактора удаления всех привычек (и дат выполнения) из базы данных
 */
public class DeleteAllHabitsDbInteractor implements IDeleteAllHabitsDbInteractor {

    /**
     * Репозиторий (база данных)
     */
    private final IHabitsDatabaseRepository habitsRepository;

    public DeleteAllHabitsDbInteractor(IHabitsDatabaseRepository habitsRepository) {
        this.habitsRepository = habitsRepository;
    }

    /**
     * Удаление всех записей о привычках
     */
    @NonNull
    @Override
    public Completable deleteAllHabits() {
        return habitsRepository.deleteAllHabits()
                .onErrorResumeNext(throwable ->
                        Completable.error(new DeleteFromDbException(R.string.delete_from_db_exception, throwable)));
    }
}
