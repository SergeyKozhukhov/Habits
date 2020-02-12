package ru.sergeykozhukhov.habits.notes.backup;

import java.util.List;

import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.model.domain.Habit;
import ru.sergeykozhukhov.habits.base.model.domain.HabitWithProgresses;

public interface IReplicationWebInteractor {

    Single<List<Habit>> loadListHabits();
    Single<List<HabitWithProgresses>> loadHabitsWithProgress();

}
