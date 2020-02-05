package ru.sergeykozhukhov.habits.base.domain.usecase;

import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.IInsertHabitWebInteractor;
import ru.sergeykozhukhov.habits.base.domain.model.Habit;

public class InsertWebHabitInteractor implements IInsertHabitWebInteractor {

    private final IHabitsWebRepository habitsWebRepository;

    public InsertWebHabitInteractor(IHabitsWebRepository habitsWebRepository) {
        this.habitsWebRepository = habitsWebRepository;
    }

    @Override
    public Single<Habit> insertHabit(Habit habit, String jwt) {
        return habitsWebRepository.insertHabit(habit, jwt);
    }
}
