package ru.sergeykozhukhov.habits.base.domain.usecase;

import androidx.annotation.NonNull;

import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.IInsertHabitInteractor;
import ru.sergeykozhukhov.habits.base.domain.model.Habit;

public class InsertHabitInteractor implements IInsertHabitInteractor {

    private final IHabitsDatabaseRepository habitsRepository;

    public InsertHabitInteractor(@NonNull IHabitsDatabaseRepository habitsRepository) {
        this.habitsRepository = habitsRepository;
    }

    @Override
    public Single<Long> insertHabit(Habit habit) {
        try {
            return habitsRepository.insertHabit(habit);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
