package ru.sergeykozhukhov.habits.base.domain;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.domain.model.Habit;

public interface IHabitsRepository {

    List<Habit> loadAll();
    Flowable<List<Habit>> loadHabits();

    Single<Long> insertHabit(Habit habit);

    Habit update(Habit habit);
}
