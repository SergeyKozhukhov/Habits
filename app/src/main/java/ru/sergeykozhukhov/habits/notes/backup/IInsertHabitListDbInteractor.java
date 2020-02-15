package ru.sergeykozhukhov.habits.notes.backup;

import java.util.List;

import io.reactivex.Completable;
import ru.sergeykozhukhov.habits.base.model.domain.Habit;

public interface IInsertHabitListDbInteractor {
    Completable insertListHabitsDb(List<Habit> habitList);
}
