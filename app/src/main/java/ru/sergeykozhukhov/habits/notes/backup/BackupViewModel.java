package ru.sergeykozhukhov.habits.notes.backup;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habits.base.domain.SingleLiveEvent;
import ru.sergeykozhukhov.habits.base.domain.usecase.BackupWebHabitListWebInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.ReplicationListHabitsWebInteractor;
import ru.sergeykozhukhov.habits.base.model.domain.HabitWithProgresses;

public class BackupViewModel extends ViewModel {

    private static final String TAG = "HabitsListViewModel";

    private final BackupWebHabitListWebInteractor insertWebHabitsInteractor;
    private final ReplicationListHabitsWebInteractor replicationListHabitsWebInteractor;

    private Disposable disposableListInsertedWeb;
    private Disposable disposableListHabitsLoadedWeb;

    private CompositeDisposable compositeDisposable;

    private SingleLiveEvent<Boolean> isInsertedSingleLiveEvent;
    private SingleLiveEvent<Boolean> isLoadedSingleLiveEvent;

    public BackupViewModel(
            @NonNull BackupWebHabitListWebInteractor insertWebHabitsInteractor,
            @NonNull ReplicationListHabitsWebInteractor replicationListHabitsWebInteractor) {
        this.insertWebHabitsInteractor = insertWebHabitsInteractor;
        this.replicationListHabitsWebInteractor = replicationListHabitsWebInteractor;

        initData();
    }


    private void initData(){
        isInsertedSingleLiveEvent = new SingleLiveEvent<>();
        isLoadedSingleLiveEvent = new SingleLiveEvent<>();

        compositeDisposable = new CompositeDisposable();
    }

    /*public void insertWebHabits(){

        insertWebHabitsInteractor.insertHabit()
                .subscribeOn(Schedulers.newThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        isInsertedSingleLiveEvent.postValue(true);
                        Log.d(TAG, "insertWebHabits: success");
                        insertWebHabitsInteractor.insertProgressList().subscribe();
                    }

                    @Override
                    public void onError(Throwable e) {
                        isInsertedSingleLiveEvent.postValue(false);
                        Log.d(TAG, "insertWebHabits: error: " + e.getMessage());
                    }
                });

    }

    public void loadListHabitsWeb(){

        disposableListHabitsLoadedWeb = replicationListHabitsWebInteractor.loadHabitList()
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<List<Habit>>() {
                    @Override
                    public void accept(List<Habit> habitList) throws Exception {
                        isLoadedSingleLiveEvent.postValue(true);
                        for(Habit habit : habitList){
                            Log.d(TAG, "loadListHabitsWeb"+habit.toString());
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        isLoadedSingleLiveEvent.postValue(false);
                    }
                });
    }*/

    public void LoadHabitsWithProgress(){
        Disposable d = replicationListHabitsWebInteractor.loadHabitWithProgressesList()
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<List<HabitWithProgresses>>() {
                    @Override
                    public void accept(List<HabitWithProgresses> habitsWithProgresses) throws Exception {
                        Log.d(TAG, "load habitsWithProgress: " + habitsWithProgresses.size());
                    }
                });
    }

    public void tryTransaction(){
        insertWebHabitsInteractor.insertHabitWithProgressesList()
                .subscribeOn(Schedulers.newThread())
                .subscribe();

    }

    public SingleLiveEvent<Boolean> getIsInsertedSingleLiveEvent() {
        return isInsertedSingleLiveEvent;
    }

    public SingleLiveEvent<Boolean> getIsLoadedSingleLiveEvent() {
        return isLoadedSingleLiveEvent;
    }
}
