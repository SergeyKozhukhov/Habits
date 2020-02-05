package ru.sergeykozhukhov.habits.base.domain.IInreractor;

import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.domain.model.Habit;

public interface IInsertHabitWebInteractor {
    Single<Habit> insertHabit(Habit habit, String jwt);
}
