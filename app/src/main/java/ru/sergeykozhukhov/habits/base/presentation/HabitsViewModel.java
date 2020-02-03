package ru.sergeykozhukhov.habits.base.presentation;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habits.base.domain.model.Authentication;
import ru.sergeykozhukhov.habits.base.domain.model.Confidentiality;
import ru.sergeykozhukhov.habits.base.domain.model.Jwt;
import ru.sergeykozhukhov.habits.base.domain.usecase.AuthenticateClientInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.InsertHabitInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.LoadHabitsInteractor;
import ru.sergeykozhukhov.habits.base.domain.model.Habit;
import ru.sergeykozhukhov.habits.base.domain.usecase.LoadJwtInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.SaveJwtInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.UpdateHabitInteractor;

public class HabitsViewModel extends ViewModel {

    private static final String TAG = "HabitsViewModel";

    private final LoadHabitsInteractor loadHabitsInteractor;
    private final InsertHabitInteractor insertHabitInteractor;
    private final UpdateHabitInteractor updateHabitInteractor;

    private final AuthenticateClientInteractor authenticateClientInteractor;

    private final SaveJwtInteractor saveJwtInteractor;
    private final LoadJwtInteractor loadJwtInteractor;


    private Disposable habitsListDisposable;
    private Disposable habitInsertedDisposable;

    private List<Habit> habitList;

    public HabitsViewModel(
            @NonNull LoadHabitsInteractor loadHabitsInteractor,
            @NonNull InsertHabitInteractor insertHabitInteractor,
            @NonNull UpdateHabitInteractor updateHabitInteractor,
            @NonNull AuthenticateClientInteractor authenticateClientInteractor,
            @NonNull SaveJwtInteractor saveJwtInteractor,
            @NonNull LoadJwtInteractor loadJwtInteractor) {
        this.loadHabitsInteractor = loadHabitsInteractor;
        this.insertHabitInteractor = insertHabitInteractor;
        this.updateHabitInteractor = updateHabitInteractor;
        this.authenticateClientInteractor = authenticateClientInteractor;
        this.saveJwtInteractor = saveJwtInteractor;
        this.loadJwtInteractor = loadJwtInteractor;
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

    public void authenticateClient(final Confidentiality confidentiality){

        /*Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Authentication authentication = authenticateClientInteractor.authenticateClient(confidentiality);
                Log.d("auth", "result: " + authentication.getMessage());
            }
        });*/

        Single<Authentication> authenticationSingle = Single.fromCallable(new Callable<Authentication>() {
            @Override
            public Authentication call() throws Exception {
                return authenticateClientInteractor.authenticateClient(confidentiality);
            }
        });

        Disposable disposable = authenticationSingle
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Authentication>() {
                    @Override
                    public void accept(Authentication authentication) throws Exception {
                        Log.d("auth", "result: " + authentication.getMessage());
                    }
                });

    }


    public Single<Authentication> authenticateClientRx(Confidentiality confidentiality) {
        Log.d("view_model", "requset");
        return authenticateClientInteractor.authenticateClientRx(confidentiality)
                .subscribeOn(Schedulers.newThread());
    }

    public void saveJwt(Jwt jwt){
        saveJwtInteractor.saveJwt(jwt);
    }

    public Jwt loadJwt(){
        return loadJwtInteractor.loadJwt();
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
