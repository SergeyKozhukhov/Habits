package ru.sergeykozhukhov.habits.base.domain;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.domain.model.Authentication;
import ru.sergeykozhukhov.habits.base.domain.model.Confidentiality;
import ru.sergeykozhukhov.habits.base.domain.model.Habit;

public interface IHabitsDatabaseRepository {

    List<Habit> loadAll();
    Flowable<List<Habit>> loadHabits();

    Single<Long> insertHabit(Habit habit);
    Single<Long> insertListHabits(List<Habit> habitList);

    Habit update(Habit habit);

    Completable deleteAllHabits();
}

