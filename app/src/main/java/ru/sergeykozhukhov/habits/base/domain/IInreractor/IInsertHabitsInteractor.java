package ru.sergeykozhukhov.habits.base.domain.IInreractor;

import java.util.List;

import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.domain.model.Habit;

public interface IInsertHabitsInteractor {
    Single<Long> insertHabits(List<Habit> habitList, String jwt);
}
