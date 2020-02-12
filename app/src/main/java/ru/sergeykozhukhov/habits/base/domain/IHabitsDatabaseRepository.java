package ru.sergeykozhukhov.habits.base.domain;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.model.data.HabitWithProgressesData;
import ru.sergeykozhukhov.habits.base.model.domain.Habit;
import ru.sergeykozhukhov.habits.base.model.domain.HabitWithProgresses;
import ru.sergeykozhukhov.habits.base.model.domain.Progress;

public interface IHabitsDatabaseRepository {

    Single<Long> insertHabit(Habit habit);
    Single<Long> insertHabitList(List<Habit> habitList);

    Completable insertProgress(Progress progress);
    Completable insertProgressList(List<Progress> progressList);

    Completable insertHabitWithProgressesList(List<HabitWithProgresses> habitWithProgressesList);

    Flowable<List<Habit>> loadHabitList();

    Single<List<Progress>> loadProgressListByHabit(long idHabit);
    Single<List<Progress>> loadProgressList();

    Single<List<HabitWithProgresses>> loadHabitWithProgressesList();

    Habit updateHabit(Habit habit);

    Completable deleteAllHabits();


}

