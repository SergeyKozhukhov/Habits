package ru.sergeykozhukhov.habits.base.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habits.base.data.converter.HabitConverter;
import ru.sergeykozhukhov.habits.base.data.converter.HabitsConverter;
import ru.sergeykozhukhov.habits.base.data.converter.HabitWithProgressesConverter;
import ru.sergeykozhukhov.habits.base.data.converter.ProgressConverter;
import ru.sergeykozhukhov.habits.base.data.converter.ProgressesConverter;
import ru.sergeykozhukhov.habits.base.data.database.HabitDao;
import ru.sergeykozhukhov.habits.base.data.database.HabitsDatabase;
import ru.sergeykozhukhov.habits.base.data.database.ProgressDao;
import ru.sergeykozhukhov.habits.base.model.data.HabitData;
import ru.sergeykozhukhov.habits.base.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.model.data.HabitWithProgressesData;
import ru.sergeykozhukhov.habits.base.model.data.ProgressData;
import ru.sergeykozhukhov.habits.base.model.domain.Habit;
import ru.sergeykozhukhov.habits.base.model.domain.HabitWithProgresses;
import ru.sergeykozhukhov.habits.base.model.domain.Progress;

public class HabitsDatabaseRepository implements IHabitsDatabaseRepository {

    private static final String TAG = "DatabaseRepository";

    private HabitsDatabase habitsDatabase;
    private HabitDao habitDao;
    private ProgressDao progressDao;

    private HabitConverter habitConverter;
    private HabitsConverter habitsConverter;

    private ProgressConverter progressConverter;
    private ProgressesConverter progressesConverter;

    private HabitWithProgressesConverter habitWithProgressesConverter;

    public HabitsDatabaseRepository(@NonNull HabitsDatabase habitsDatabase,
                                    @NonNull HabitConverter habitConverter,
                                    @NonNull HabitsConverter habitsConverter,
                                    @NonNull ProgressConverter progressConverter,
                                    @NonNull ProgressesConverter progressesConverter,
                                    @NonNull HabitWithProgressesConverter habitWithProgressesConverter) {

        this.habitsDatabase = habitsDatabase;

        this.habitConverter = habitConverter;
        this.habitsConverter = habitsConverter;

        this.progressConverter = progressConverter;
        this.progressesConverter = progressesConverter;

        this.habitWithProgressesConverter = habitWithProgressesConverter;

        habitDao = habitsDatabase.getHabitDao();
        progressDao = habitsDatabase.getProgressDao();
    }


    @Override
    public Flowable<List<Habit>> loadHabitList() {

        return habitDao.getHabitListFlowable()
                .map(new Function<List<HabitData>, List<Habit>>() {
                    @Override
                    public List<Habit> apply(List<HabitData> habitDataList) throws Exception {
                        return habitsConverter.convertTo(habitDataList);
                    }
                });
    }

    @Override
    public Single<Long> insertHabit(final Habit habit) {

         return Single.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return habitDao.insertHabit(habitConverter.convertFrom(habit));
            }
        });

    }

    @Override
    public Single<Long> insertHabitList(List<Habit> habitList) {
        final List<HabitData> habitDataList = new ArrayList<>(habitList.size());
        for (Habit habit : habitList){
            habitDataList.add(habitConverter.convertFrom(habit));
        }
        return Single.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                habitDao.insertHabitList(habitDataList);
                Log.d(TAG, "inserted list");
                return 1L;
            }
        });
    }

    @Override
    public Habit updateHabit(Habit habit) {
        habitDao.updateHabit(habitConverter.convertFrom(habit));
        return habit.copy();
    }

    @Override
    public Completable deleteAllHabits() {

        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                habitDao.deleteAll();
            }
        });
    }

    @Override
    public Single<List<Progress>> loadProgressListByHabit(long idHabit) {
        return progressDao.getProgressByHabit(idHabit)
                .map(new Function<List<ProgressData>, List<Progress>>() {
                    @Override
                    public List<Progress> apply(List<ProgressData> progressDataList) throws Exception {

                        List<Progress> progresses = new ArrayList<>(progressDataList.size());
                        for (ProgressData progressData : progressDataList){
                            progresses.add(progressConverter.convertTo(progressData));
                        }
                        return progresses;

                    }
                });
    }

    @Override
    public Single<List<Progress>> loadProgressList() {
        return progressDao.getProgressList()
                .map(new Function<List<ProgressData>, List<Progress>>() {
                    @Override
                    public List<Progress> apply(List<ProgressData> progressDataList) throws Exception {
                        return progressesConverter.convertTo(progressDataList);
                    }
                });
    }

    @Override
    public Completable insertProgress(Progress progress) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                progressDao.insertProgress(progressConverter.convertFrom(progress));
            }
        });
    }

    @Override
    public Completable insertProgressList(List<Progress> progressList) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                progressDao.insertProgressList(progressesConverter.convertFrom(progressList));
            }
        });
    }

    @Override
    public Single<List<HabitWithProgresses>> loadHabitWithProgressesList() {
        return habitDao.getHabitWithProgressesList()
                .map(new Function<List<HabitWithProgressesData>, List<HabitWithProgresses>>() {
                    @Override
                    public List<HabitWithProgresses> apply(List<HabitWithProgressesData> habitWithProgressesDataList) throws Exception {
                        List<HabitWithProgresses> habitWithProgressesList = new ArrayList<>(habitWithProgressesDataList.size());
                        for (HabitWithProgressesData habitWithProgressesData : habitWithProgressesDataList){
                            habitWithProgressesList.add(habitWithProgressesConverter.convertTo(habitWithProgressesData));
                        }

                        return habitWithProgressesList;
                    }
                });
    }

    @Override
    public Completable insertHabitWithProgressesList(List<HabitWithProgresses> habitWithProgressesList) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                HabitWithProgressesData habitWithProgressesData;
                for (HabitWithProgresses habitWithProgresses : habitWithProgressesList){
                    habitWithProgressesData = habitWithProgressesConverter.convertFrom(habitWithProgresses);
                    long idHabit = habitDao.insertHabit(habitWithProgressesData.getHabitData());
                    for (ProgressData progressData : habitWithProgressesData.getProgressDataList()){
                        progressData.setIdHabit(idHabit);
                    }
                    progressDao.insertProgressList(habitWithProgressesData.getProgressDataList());
                }
            }
        });
    }


}
