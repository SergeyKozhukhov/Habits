package ru.sergeykozhukhov.habits.notes.backup;

import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.model.domain.Habit;

public interface IInsertHabitWebInteractor {
    Single<Habit> insertHabit(Habit habit, String jwt);
}
