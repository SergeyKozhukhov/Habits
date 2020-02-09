package ru.sergeykozhukhov.habits.base.domain.IInreractor;

import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.model.domain.Habit;

public interface IUpdateHabitInreractor {
    Single<Habit> updateHabit(Habit habit);
}
