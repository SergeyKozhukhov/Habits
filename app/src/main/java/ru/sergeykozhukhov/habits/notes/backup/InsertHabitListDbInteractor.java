package ru.sergeykozhukhov.habits.notes.backup;

import java.util.List;

import io.reactivex.Completable;
import ru.sergeykozhukhov.habits.base.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.model.domain.Habit;

public class InsertHabitListDbInteractor implements IInsertHabitListDbInteractor {

    private final IHabitsDatabaseRepository habitsRepository;

    public InsertHabitListDbInteractor(IHabitsDatabaseRepository habitsRepository) {
        this.habitsRepository = habitsRepository;
    }

    @Override
    public Completable insertListHabitsDb(List<Habit> habitList) {
        return habitsRepository.insertHabitList(habitList);
    }
}
