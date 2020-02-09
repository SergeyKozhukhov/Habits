package ru.sergeykozhukhov.habits.base.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habits.base.data.converter.HabitConverter;
import ru.sergeykozhukhov.habits.base.data.converter.HabitsConverter;
import ru.sergeykozhukhov.habits.base.data.database.HabitDao;
import ru.sergeykozhukhov.habits.base.data.database.HabitsDatabase;
import ru.sergeykozhukhov.habits.base.model.data.HabitData;
import ru.sergeykozhukhov.habits.base.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.model.domain.Habit;

public class HabitsDatabaseRepository implements IHabitsDatabaseRepository {

    private static final String TAG = "DatabaseRepository";

    private HabitsDatabase habitsDatabase;
    private HabitDao habitDao;

    private HabitConverter habitConverter;
    private HabitsConverter habitsConverter;

    public HabitsDatabaseRepository(@NonNull HabitsDatabase habitsDatabase,
                                    @NonNull HabitConverter habitConverter,
                                    @NonNull HabitsConverter habitsConverter) {

        this.habitsDatabase = habitsDatabase;

        this.habitConverter = habitConverter;
        this.habitsConverter = habitsConverter;

        habitDao = habitsDatabase.getHabitDao();
    }


    @Override
    public Flowable<List<Habit>> loadHabits() {

        return habitDao.getHabits()
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
        }).subscribeOn(Schedulers.io());

        /*
        return habitDao.insertHabits(habitConverter.convertFrom(habit))
                ;*/
    }

    @Override
    public Single<Long> insertListHabits(List<Habit> habitList) {
        final List<HabitData> habitDataList = new ArrayList<>(habitList.size());
        for (Habit habit : habitList){
            habitDataList.add(habitConverter.convertFrom(habit));
        }
        return Single.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                habitDao.insert(habitDataList);
                Log.d(TAG, "inserted list");
                return 1L;
            }
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Habit update(Habit habit) {
        habitDao.update(habitConverter.convertFrom(habit));
        return habit.copy();
    }

    @Override
    public Completable deleteAllHabits() {

        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                habitDao.deleteAllHabits();
            }
        });
    }


}
