package ru.sergeykozhukhov.habits.notes.backup;

import java.util.List;

import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.model.domain.Habit;

public interface IInsertHabitListDbInteractor {
    Single<Long> insertListHabitsDb(List<Habit> habitList);
}
