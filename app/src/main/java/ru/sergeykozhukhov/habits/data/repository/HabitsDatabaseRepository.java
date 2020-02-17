package ru.sergeykozhukhov.habits.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import ru.sergeykozhukhov.habits.data.converter.HabitConverter;
import ru.sergeykozhukhov.habits.data.converter.HabitListConverter;
import ru.sergeykozhukhov.habits.data.converter.HabitWithProgressesConverter;
import ru.sergeykozhukhov.habits.data.converter.HabitWithProgressesListConverter;
import ru.sergeykozhukhov.habits.data.converter.ProgressConverter;
import ru.sergeykozhukhov.habits.data.converter.ProgressListConverter;
import ru.sergeykozhukhov.habits.data.converter.StatisticListConverter;
import ru.sergeykozhukhov.habits.data.database.HabitDao;
import ru.sergeykozhukhov.habits.data.database.HabitsDatabase;
import ru.sergeykozhukhov.habits.data.database.ProgressDao;
import ru.sergeykozhukhov.habits.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.model.data.HabitWithProgressesData;
import ru.sergeykozhukhov.habits.model.data.ProgressData;
import ru.sergeykozhukhov.habits.model.domain.Habit;
import ru.sergeykozhukhov.habits.model.domain.HabitWithProgresses;
import ru.sergeykozhukhov.habits.model.domain.Progress;
import ru.sergeykozhukhov.habits.model.domain.Statistic;

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


    @NonNull
    @Override
    public Single<Long> insertHabit(@NonNull Habit habit) {
        return habitDao.insertHabit(habitConverter.convertFrom(habit));
    }

    @NonNull
    @Override
    public Completable insertHabitList(@NonNull List<Habit> habitList) {
        return habitDao.insertHabitList(habitListConverter.convertFrom(habitList));
    }

    @NonNull
    @Override
    public Completable insertProgress(@NonNull Progress progress) {
        return progressDao.insertProgress(progressConverter.convertFrom(progress));
    }

    @NonNull
    @Override
    public Completable insertProgressList(@NonNull List<Progress> progressList) {
        return progressDao.insertProgressList(progressListConverter.convertFrom(progressList));
    }


    @NonNull
    @Override
    public Completable insertHabitWithProgressesList(@NonNull List<HabitWithProgresses> habitWithProgressesList) {
        return Completable.fromAction(() -> {
            HabitWithProgressesData habitWithProgressesData;
            for (HabitWithProgresses habitWithProgresses : habitWithProgressesList){
                habitWithProgressesData = habitWithProgressesConverter.convertFrom(habitWithProgresses);
                long idHabit = habitDao.insertHabit(habitWithProgressesData.getHabitData()).blockingGet();
                for (ProgressData progressData : habitWithProgressesData.getProgressDataList()){
                    progressData.setIdHabit(idHabit);
                    Log.d(TAG, progressData.toString());
                }
                progressDao.insertProgressList(habitWithProgressesData.getProgressDataList()).subscribe();
            }
        });
    }


    @NonNull
    @Override
    public Flowable<List<Habit>> loadHabitList() {
        return habitDao.getHabitList()
                .map(habitDataList ->
                        habitListConverter.convertTo(habitDataList));
    }

    @NonNull
    @Override
    public Single<List<Progress>> loadProgressListByIdHabit(long idHabit) {
        return progressDao.getProgressByHabit(idHabit)
                .map(progressDataList -> progressListConverter.convertTo(progressDataList));
    }


    @NonNull
    @Override
    public Single<List<Progress>> loadProgressList() {
        return progressDao.getProgressList()
                .map(progressDataList -> progressListConverter.convertTo(progressDataList));
    }

    @NonNull
    @Override
    public Single<List<Statistic>> loadStatisticList() {
        return habitDao.getStatisticList()
                .map(statisticDataList ->
                        statisticListConverter.convertTo(statisticDataList));
    }

    @NonNull
    @Override
    public Single<List<HabitWithProgresses>> loadHabitWithProgressesList() {
        return habitDao.getHabitWithProgressesList()
                .map(habitWithProgressesDataList ->
                        habitWithProgressesListConverter.convertTo(habitWithProgressesDataList));
    }

    @NonNull
    @Override
    public Completable updateHabit(@NonNull Habit habit) {
        return habitDao.updateHabit(habitConverter.convertFrom(habit));
    }

    @NonNull
    @Override
    public Completable deleteAllHabits() {

        return Completable.fromAction(() -> habitDao.deleteAll());
    }

    @NonNull
    @Override
    public Completable deleteProgressList(@NonNull List<Progress> progressList) {
        return Completable.fromAction(() -> {
            Log.d(TAG, "deleted "+progressList.size());
            progressDao.deleteProgressList(progressListConverter.convertFrom(progressList));
        });
    }


}
