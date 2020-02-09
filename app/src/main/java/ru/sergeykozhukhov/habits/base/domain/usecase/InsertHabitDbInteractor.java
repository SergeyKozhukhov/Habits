package ru.sergeykozhukhov.habits.base.domain.usecase;

import androidx.annotation.NonNull;

import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.IInsertHabitDbInteractor;
import ru.sergeykozhukhov.habits.base.model.domain.Habit;

public class InsertHabitDbInteractor implements IInsertHabitDbInteractor {

    private final IHabitsDatabaseRepository habitsRepository;

    public InsertHabitDbInteractor(@NonNull IHabitsDatabaseRepository habitsRepository) {
        this.habitsRepository = habitsRepository;
    }

    @Override
    public Single<Long> insertHabit(Habit habit) {
        return habitsRepository.insertHabit(habit);
    }
}
