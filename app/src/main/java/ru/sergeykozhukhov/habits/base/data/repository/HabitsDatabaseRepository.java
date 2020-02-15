package ru.sergeykozhukhov.habits.base.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.data.converter.HabitConverter;
import ru.sergeykozhukhov.habits.base.data.converter.HabitListConverter;
import ru.sergeykozhukhov.habits.base.data.converter.HabitWithProgressesConverter;
import ru.sergeykozhukhov.habits.base.data.converter.HabitWithProgressesListConverter;
import ru.sergeykozhukhov.habits.base.data.converter.ProgressConverter;
import ru.sergeykozhukhov.habits.base.data.converter.ProgressListConverter;
import ru.sergeykozhukhov.habits.base.data.converter.StatisticListConverter;
import ru.sergeykozhukhov.habits.base.data.database.HabitDao;
import ru.sergeykozhukhov.habits.base.data.database.HabitsDatabase;
import ru.sergeykozhukhov.habits.base.data.database.ProgressDao;
import ru.sergeykozhukhov.habits.base.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.model.data.HabitWithProgressesData;
import ru.sergeykozhukhov.habits.base.model.data.ProgressData;
import ru.sergeykozhukhov.habits.base.model.domain.Habit;
import ru.sergeykozhukhov.habits.base.model.domain.HabitWithProgresses;
import ru.sergeykozhukhov.habits.base.model.domain.Progress;
import ru.sergeykozhukhov.habits.base.model.domain.Statistic;

public class HabitsDatabaseRepository implements IHabitsDatabaseRepository {

    private static final String TAG = "DatabaseRepository";

    private HabitsDatabase habitsDatabase;
    private HabitDao habitDao;
    private ProgressDao progressDao;

    private HabitConverter habitConverter;
    private HabitListConverter habitListConverter;

    private ProgressConverter progressConverter;
    private ProgressListConverter progressListConverter;

    private HabitWithProgressesConverter habitWithProgressesConverter;
    private HabitWithProgressesListConverter habitWithProgressesListConverter;

    private StatisticListConverter statisticListConverter;

    public HabitsDatabaseRepository(@NonNull HabitsDatabase habitsDatabase,
                                    @NonNull HabitConverter habitConverter,
                                    @NonNull HabitListConverter habitListConverter,
                                    @NonNull ProgressConverter progressConverter,
                                    @NonNull ProgressListConverter progressListConverter,
                                    @NonNull HabitWithProgressesConverter habitWithProgressesConverter,
                                    @NonNull HabitWithProgressesListConverter habitWithProgressesListConverter,
                                    @NonNull StatisticListConverter statisticListConverter) {

        this.habitsDatabase = habitsDatabase;

        this.habitConverter = habitConverter;
        this.habitListConverter = habitListConverter;

        this.progressConverter = progressConverter;
        this.progressListConverter = progressListConverter;

        this.habitWithProgressesConverter = habitWithProgressesConverter;
        this.habitWithProgressesListConverter = habitWithProgressesListConverter;

        this.statisticListConverter = statisticListConverter;

        habitDao = habitsDatabase.getHabitDao();
        progressDao = habitsDatabase.getProgressDao();
    }


    @Override
    public Single<Long> insertHabit(final Habit habit) {
         return Single.fromCallable(() ->
                 habitDao.insertHabit(habitConverter.convertFrom(habit)));
    }

    @Override
    public Completable insertHabitList(List<Habit> habitList) {
        return habitDao.insertHabitList(habitListConverter.convertFrom(habitList));
    }

    @Override
    public Completable insertProgress(Progress progress) {
        return Completable.fromAction(() ->
                progressDao.insertProgress(progressConverter.convertFrom(progress)));
    }

    @Override
    public Completable insertProgressList(List<Progress> progressList) {
        return Completable.fromAction(() -> {
            Log.d(TAG, "insert progress list, size: "+progressList.size());
            progressDao.insertProgressList(progressListConverter.convertFrom(progressList));
        });
    }

    @Override
    public Completable insertHabitWithProgressesList(List<HabitWithProgresses> habitWithProgressesList) {
        return Completable.fromAction(() -> {
            HabitWithProgressesData habitWithProgressesData;
            for (HabitWithProgresses habitWithProgresses : habitWithProgressesList){
                habitWithProgressesData = habitWithProgressesConverter.convertFrom(habitWithProgresses);
                long idHabit = habitDao.insertHabit(habitWithProgressesData.getHabitData());
                for (ProgressData progressData : habitWithProgressesData.getProgressDataList()){
                    progressData.setIdHabit(idHabit);
                }
                progressDao.insertProgressList(habitWithProgressesData.getProgressDataList());
            }
        });
    }

    @Override
    public Flowable<List<Habit>> loadHabitList() {
        return habitDao.getHabitList()
                .map(habitDataList ->
                        habitListConverter.convertTo(habitDataList));
    }

    @Override
    public Single<List<Progress>> loadProgressListByIdHabit(long idHabit) {
        return progressDao.getProgressByHabit(idHabit)
                .map(progressDataList -> progressListConverter.convertTo(progressDataList));
    }

    @Override
    public Single<List<Progress>> loadProgressList() {
        return progressDao.getProgressList()
                .map(progressDataList -> progressListConverter.convertTo(progressDataList));
    }

    @Override
    public Single<List<Statistic>> loadStatisticList() {
        return habitDao.getStatisticList()
                .map(statisticDataList ->
                        statisticListConverter.convertTo(statisticDataList));
    }

    @Override
    public Single<List<HabitWithProgresses>> loadHabitWithProgressesList() {
        return habitDao.getHabitWithProgressesList()
                .map(habitWithProgressesDataList ->
                        habitWithProgressesListConverter.convertTo(habitWithProgressesDataList));
    }

    @Override
    public Habit updateHabit(Habit habit) {
        habitDao.updateHabit(habitConverter.convertFrom(habit));
        return habit.copy();
    }

    @Override
    public Completable deleteAllHabits() {

        return Completable.fromAction(() -> habitDao.deleteAll());
    }

    @Override
    public Completable deleteProgressList(List<Progress> progressList) {
        return Completable.fromAction(() -> {
            Log.d(TAG, "deleted "+progressList.size());
            progressDao.deleteProgressList(progressListConverter.convertFrom(progressList));
        });
    }


}
