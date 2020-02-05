package ru.sergeykozhukhov.habits.base.domain.usecase;

import java.util.List;

import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.base.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.ILoadListHabitsWebInteractor;
import ru.sergeykozhukhov.habits.base.domain.model.Habit;

public class LoadListHabitsWebInteractor implements ILoadListHabitsWebInteractor {

    private IHabitsWebRepository habitsWebRepository;

    public LoadListHabitsWebInteractor(IHabitsWebRepository habitsWebRepository) {
        this.habitsWebRepository = habitsWebRepository;
    }

    @Override
    public Single<List<Habit>> loadListHabits(String jwt) {
        return habitsWebRepository.loadListHabits(jwt);
    }
}
