package ru.sergeykozhukhov.habits.base.domain.usecase;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;
import ru.sergeykozhukhov.habits.base.domain.IHabitsRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.ILoadHabitsInteractor;
import ru.sergeykozhukhov.habits.base.domain.model.Habit;

public class LoadHabitsInteractor implements ILoadHabitsInteractor {

    private final IHabitsRepository habitsRepository;

    public LoadHabitsInteractor(@NonNull IHabitsRepository habitsRepository) {
        this.habitsRepository = habitsRepository;
    }

    @Override
    public Flowable<List<Habit>> loadHabits() {
        try {
            return habitsRepository.loadHabits();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
