package ru.sergeykozhukhov.habits.base.domain.usecase;

import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habits.base.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.IUpdateHabitInreractor;
import ru.sergeykozhukhov.habits.base.model.domain.Habit;

public class UpdateHabitInteractor implements IUpdateHabitInreractor {

    private final IHabitsDatabaseRepository habitsRepository;

    public UpdateHabitInteractor(IHabitsDatabaseRepository habitsRepository) {
        this.habitsRepository = habitsRepository;
    }

    @Override
    public Single<Habit> updateHabit(final Habit habit) {
        Single<Habit> result = Single.fromCallable(new Callable<Habit>() {
            @Override
            public Habit call() throws Exception {
                return habitsRepository.updateHabit(habit);
            }
        }).subscribeOn(Schedulers.io());

        return result;
    }
}
