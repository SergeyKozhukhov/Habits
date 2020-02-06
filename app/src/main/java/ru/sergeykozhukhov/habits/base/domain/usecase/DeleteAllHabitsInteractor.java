package ru.sergeykozhukhov.habits.base.domain.usecase;

import io.reactivex.Completable;
import ru.sergeykozhukhov.habits.base.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.IDeleteAllHabitsInteractor;

public class DeleteAllHabitsInteractor implements IDeleteAllHabitsInteractor {
    private final IHabitsDatabaseRepository habitsRepository;

    public DeleteAllHabitsInteractor(IHabitsDatabaseRepository habitsRepository) {
        this.habitsRepository = habitsRepository;
    }

    @Override
    public Completable deleteAllHabits() {
        return habitsRepository.deleteAllHabits();
    }
}
