package ru.sergeykozhukhov.habits.base.domain;

import java.util.List;

import io.reactivex.Flowable;
import ru.sergeykozhukhov.habits.base.domain.model.Habit;

public interface ILoadHabitsInteractor {
    Flowable<List<Habit>> loadHabits();
}
