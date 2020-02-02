package ru.sergeykozhukhov.habits.base.presentation;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import ru.sergeykozhukhov.habits.base.domain.usecase.InsertHabitInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.LoadHabitsInteractor;
import ru.sergeykozhukhov.habits.base.domain.model.Habit;
import ru.sergeykozhukhov.habits.base.domain.usecase.UpdateHabitInteractor;

public class HabitsViewModel extends ViewModel {

    private static final String TAG = "HabitsViewModel";

    private final LoadHabitsInteractor loadHabitsInteractor;
    private final InsertHabitInteractor insertHabitInteractor;
    private final UpdateHabitInteractor updateHabitInteractor;

    private Disposable habitsListDisposable;
    private Disposable habitInsertedDisposable;

    private List<Habit> habitList;

    public HabitsViewModel(
            @NonNull LoadHabitsInteractor loadHabitsInteractor,
            @NonNull InsertHabitInteractor insertHabitInteractor,
            @NonNull UpdateHabitInteractor updateHabitInteractor) {
        this.loadHabitsInteractor = loadHabitsInteractor;
        this.insertHabitInteractor = insertHabitInteractor;
        this.updateHabitInteractor = updateHabitInteractor;
    }

    public Flowable<List<Habit>> loadHabits(){
        return loadHabitsInteractor.loadHabits();
    }
/*
    public void loadHabits() {
        List<Habit> list = null;
        habitsListDisposable = loadHabitsInteractor.loadHabits()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Habit>>() {
                    @Override
                    public void accept(List<Habit> habits) throws Exception {
                        habitList = new ArrayList<>(habits);

                        list = habitList;
                        Log.d(TAG, "load success" + habits.get(0).getTitle());
                        Log.d(TAG, "size habitlist: " + habitList.size());
                        Log.d(TAG, "size habits: " + habits.size());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        Log.d(TAG, "load error");
                    }
                });
    }*/

    public void insertHabit(Habit habit){
        habitInsertedDisposable = insertHabitInteractor.insertHabit(habit)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long id) throws Exception {

                        Log.d(TAG, "insert success. id = "+id);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        Log.d(TAG, "insert error");
                    }
                });
    }

    public Single<Habit> updateHabit(Habit habit){
        return updateHabitInteractor.updateHabit(habit);
    }


    public Disposable getHabitsListDisposable() {
        return habitsListDisposable;
    }

    public Disposable getHabitInsertedDisposable() {
        return habitInsertedDisposable;
    }

    public List<Habit> getHabitList() {
        return habitList;
    }
}
