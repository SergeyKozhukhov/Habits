package ru.sergeykozhukhov.habits.base.domain;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.model.domain.Habit;
import ru.sergeykozhukhov.habits.base.model.domain.HabitWithProgresses;
import ru.sergeykozhukhov.habits.base.model.domain.Progress;
import ru.sergeykozhukhov.habits.base.model.domain.Statistic;

public interface IHabitsDatabaseRepository extends IRepository{

    @NonNull Single<Long> insertHabit(@NonNull Habit habit);
    @NonNull Completable insertHabitList(@NonNull List<Habit> habitList);

    @NonNull Completable insertProgress(@NonNull Progress progress);
    @NonNull Completable insertProgressList(@NonNull List<Progress> progressList);

    @NonNull Completable insertHabitWithProgressesList(@NonNull List<HabitWithProgresses> habitWithProgressesList);

    @NonNull Flowable<List<Habit>> loadHabitList();

    @NonNull Single<List<Progress>> loadProgressListByIdHabit(@NonNull long idHabit);
    @NonNull Single<List<Progress>> loadProgressList();

    @NonNull Single<List<Statistic>> loadStatisticList();

    @NonNull Single<List<HabitWithProgresses>> loadHabitWithProgressesList();

    @NonNull Habit updateHabit(@NonNull Habit habit);

    @NonNull Completable deleteAllHabits();
    @NonNull Completable deleteProgressList(@NonNull List<Progress> progressList);

}

