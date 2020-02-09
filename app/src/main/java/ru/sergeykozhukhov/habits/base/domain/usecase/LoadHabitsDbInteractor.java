package ru.sergeykozhukhov.habits.base.domain.usecase;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;
import ru.sergeykozhukhov.habits.base.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.ILoadHabitsDbInteractor;
import ru.sergeykozhukhov.habits.base.model.domain.Habit;

public class LoadHabitsDbInteractor implements ILoadHabitsDbInteractor {

    private final IHabitsDatabaseRepository habitsRepository;

    public LoadHabitsDbInteractor(@NonNull IHabitsDatabaseRepository habitsRepository) {
        this.habitsRepository = habitsRepository;
    }

    @Override
    public Flowable<List<Habit>> loadHabits() {
        return habitsRepository.loadHabits();
    }

}
