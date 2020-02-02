package ru.sergeykozhukhov.habits.base.domain;

import java.util.List;

import io.reactivex.Flowable;
import ru.sergeykozhukhov.habits.base.domain.model.Habit;

public interface IHabitsRepository {

    List<Habit> loadAll();
    Flowable<List<Habit>> loadHabits();
}
