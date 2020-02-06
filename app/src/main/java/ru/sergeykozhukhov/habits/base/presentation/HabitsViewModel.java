package ru.sergeykozhukhov.habits.base.presentation;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.CompletableObserver;
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
import ru.sergeykozhukhov.habits.base.domain.usecase.DeleteAllHabitsInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.GetJwtInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.InsertHabitInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.InsertListHabitsDBInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.InsertWebHabitInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.InsertWebHabitsInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.LoadHabitsInteractor;
import ru.sergeykozhukhov.habits.base.domain.model.Habit;
import ru.sergeykozhukhov.habits.base.domain.usecase.LoadJwtInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.LoadListHabitsWebInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.SaveJwtInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.SetJwtInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.UpdateHabitInteractor;

public class HabitsViewModel extends ViewModel {

    private static final String TAG = "HabitsViewModel";

    private final LoadHabitsInteractor loadHabitsInteractor;
    private final InsertHabitInteractor insertHabitInteractor;
    private final InsertListHabitsDBInteractor insertListHabitsDBInteractor;
    private final UpdateHabitInteractor updateHabitInteractor;
    private final DeleteAllHabitsInteractor deleteAllHabitsInteractor;

    private final AuthenticateClientInteractor authenticateClientInteractor;
    private final InsertWebHabitInteractor insertWebHabitInteractor;
    private final InsertWebHabitsInteractor insertWebHabitsInteractor;
    private final LoadListHabitsWebInteractor loadListHabitsWebInteractor;

    private final SaveJwtInteractor saveJwtInteractor;
    private final LoadJwtInteractor loadJwtInteractor;
    private final SetJwtInteractor setJwtInteractor;
    private final GetJwtInteractor getJwtInteractor;

    private Disposable disposableLoadHabits;
    private Disposable habitInsertedDisposable;
    private Disposable disposableHabitsListInserted;
    private Disposable disposableUpdatedHabit;
    private Disposable disposableAuthenticated;
    private Disposable disposableInsertedWeb;
    private Disposable disposableListInsertedWeb;
    private Disposable disposableListHabitsLoadedWeb;

    private MutableLiveData<List<Habit>> habitListLiveData = new MutableLiveData<>();

    public HabitsViewModel(
            @NonNull LoadHabitsInteractor loadHabitsInteractor,
            @NonNull InsertHabitInteractor insertHabitInteractor,
            @NonNull InsertListHabitsDBInteractor insertListHabitsDBInteractor,
            @NonNull UpdateHabitInteractor updateHabitInteractor,
            @NonNull DeleteAllHabitsInteractor deleteAllHabitsInteractor,
            @NonNull AuthenticateClientInteractor authenticateClientInteractor,
            @NonNull InsertWebHabitInteractor insertWebHabitInteractor,
            @NonNull InsertWebHabitsInteractor insertWebHabitsInteractor,
            @NonNull LoadListHabitsWebInteractor loadListHabitsWebInteractor,
            @NonNull SaveJwtInteractor saveJwtInteractor,
            @NonNull LoadJwtInteractor loadJwtInteractor,
            @NonNull SetJwtInteractor setJwtInteractor,
            @NonNull GetJwtInteractor getJwtInteractor) {
        this.loadHabitsInteractor = loadHabitsInteractor;
        this.insertHabitInteractor = insertHabitInteractor;
        this.insertListHabitsDBInteractor = insertListHabitsDBInteractor;
        this.updateHabitInteractor = updateHabitInteractor;
        this.deleteAllHabitsInteractor = deleteAllHabitsInteractor;
        this.authenticateClientInteractor = authenticateClientInteractor;
        this.insertWebHabitInteractor = insertWebHabitInteractor;
        this.insertWebHabitsInteractor = insertWebHabitsInteractor;
        this.loadListHabitsWebInteractor = loadListHabitsWebInteractor;
        this.saveJwtInteractor = saveJwtInteractor;
        this.loadJwtInteractor = loadJwtInteractor;
        this.setJwtInteractor = setJwtInteractor;
        this.getJwtInteractor = getJwtInteractor;
    }

    public void loadHabits(){

        disposableLoadHabits = loadHabitsInteractor.loadHabits()
                .subscribe(new Consumer<List<Habit>>() {
                    @Override
                    public void accept(List<Habit> habitList) throws Exception {
                        habitListLiveData.postValue(habitList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    public void insertHabit(Habit habit){
        habitInsertedDisposable = insertHabitInteractor.insertHabit(habit)
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

    public void insertListHabitsDbInteractor(List<Habit> habitList){

        disposableHabitsListInserted = insertListHabitsDBInteractor.insertListHabitsDb(habitList)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d(TAG, "insert list success");
                    }
                });
    }

    public void updateHabit(Habit habit){

         disposableUpdatedHabit = updateHabitInteractor.updateHabit(habit)
                 .subscribe(new Consumer<Habit>() {
                     @Override
                     public void accept(Habit habit) throws Exception {
                         Log.d(TAG, "updete success. id = "+habit.getIdHabit());
                     }
                 }, new Consumer<Throwable>() {
                     @Override
                     public void accept(Throwable throwable) throws Exception {
                         Log.d(TAG, "update error");
                     }
                 });

    }

    public void deleteAllHabits(){
        deleteAllHabitsInteractor.deleteAllHabits()
                .subscribeOn(Schedulers.newThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }


    public void authenticateClient(final Confidentiality confidentiality){

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

    public void authenticateClientRx(Confidentiality confidentiality) {

        disposableAuthenticated = authenticateClientInteractor.authenticateClientRx(confidentiality)
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<Authentication>() {
                    @Override
                    public void accept(Authentication authentication) throws Exception {
                        setJwtInteractor.setJwt(new Jwt(authentication.getJwt()));
                        saveJwt(new Jwt(authentication.getJwt()));
                        Log.d(TAG, "authenticateClientRx: "+authentication.getMessage());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    public void insertWebHabit(Habit habit){
        String jwt = getJwtInteractor.getJwt().getJwt();
        if (jwt == null || jwt.equals("")){
            Log.d(TAG, "insertWebHabit - fail jwt: "+jwt);
            return;
        }
        disposableInsertedWeb = insertWebHabitInteractor.insertHabit(habit, jwt)
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<Habit>() {
                    @Override
                    public void accept(Habit habit) throws Exception {
                        Log.d(TAG, "insertWebHabit: "+habit.getIdHabitServer());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    public void insertWebHabits(List<Habit> habitList){
        String jwt = getJwtInteractor.getJwt().getJwt();
        if (jwt == null || jwt.equals("")){
            Log.d(TAG, "insertWebHabits - fail jwt: "+jwt);
            return;
        }

        disposableListInsertedWeb = insertWebHabitsInteractor.insertHabits(habitList, jwt)
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        //Log.d(TAG, "insertWebHabits: "+aLong);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    public void loadListHabitsWeb(){
        Log.d(TAG, "loadListHabitsWeb start");
        String jwt = getJwtInteractor.getJwt().getJwt();
        if (jwt == null || jwt.equals("")){
            Log.d(TAG, "loadListHabitsWeb - fail jwt: "+jwt);
            return;
        }

        disposableListHabitsLoadedWeb = loadListHabitsWebInteractor.loadListHabits(jwt)
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<List<Habit>>() {
                    @Override
                    public void accept(List<Habit> habitList) throws Exception {
                        insertListHabitsDbInteractor(habitList);
                        for(Habit habit : habitList){
                            Log.d(TAG, "loadListHabitsWeb"+habit.toString());
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

    }

    private void saveJwt(Jwt jwt){
        saveJwtInteractor.saveJwt(jwt);
    }

    public void loadJwt(){
        loadJwtInteractor.loadJwt();
    }

    private void setJwt(Jwt jwt){
        setJwtInteractor.setJwt(jwt);
    }

    private Jwt getJwt(){
        return getJwtInteractor.getJwt();
    }


    @NonNull
    public MutableLiveData<List<Habit>> getHabitListLiveData(){
        return habitListLiveData;
    }
}
