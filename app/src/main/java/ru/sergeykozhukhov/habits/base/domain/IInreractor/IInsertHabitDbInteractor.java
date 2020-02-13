package ru.sergeykozhukhov.habits.base.domain.IInreractor;

import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.model.domain.Habit;
import ru.sergeykozhukhov.habits.base.model.exception.BuildException;

public interface IInsertHabitDbInteractor {
    Single<Long> insertHabit(String title, String description, String startDate, String duration) throws BuildException;
}
