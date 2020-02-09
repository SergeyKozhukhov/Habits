package ru.sergeykozhukhov.habits.base.domain.usecase;

import io.reactivex.Completable;
import ru.sergeykozhukhov.habits.base.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.IDeleteAllHabitsDbInteractor;

public class DeleteAllHabitsDbInteractor implements IDeleteAllHabitsDbInteractor {
    private final IHabitsDatabaseRepository habitsRepository;

    public DeleteAllHabitsDbInteractor(IHabitsDatabaseRepository habitsRepository) {
        this.habitsRepository = habitsRepository;
    }

    @Override
    public Completable deleteAllHabits() {
        return habitsRepository.deleteAllHabits();
    }
}
