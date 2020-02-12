package ru.sergeykozhukhov.habits.base.domain.IInreractor;

import java.util.List;

import io.reactivex.Flowable;
import ru.sergeykozhukhov.habits.base.model.domain.Habit;

public interface ILoadHabitsDbInteractor {
    Flowable<List<Habit>> loadHabitList();

}
