package ru.sergeykozhukhov.habits.base.presentation;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.CompletableObserver;
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



    public void LoadHabitWithProgressesList(){
        Disposable disposable = replicationListHabitsWebInteractor.loadHabitWithProgressesList()
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<List<HabitWithProgresses>>() {
                    @Override
                    public void accept(List<HabitWithProgresses> habitsWithProgresses) throws Exception {
                        isLoadedSingleLiveEvent.postValue(true);
                        for(HabitWithProgresses habitsWithProgress : habitsWithProgresses){
                            Log.d(TAG, "loadListHabitsWeb"+habitsWithProgress.toString());
                        }
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        isLoadedSingleLiveEvent.postValue(false);
                    }
                });
        compositeDisposable.add(disposable);
    }

    public void insertHabitWithProgressesList(){
        insertWebHabitsInteractor.insertHabitWithProgressesList()
                .subscribeOn(Schedulers.newThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        isInsertedSingleLiveEvent.postValue(true);
                        Log.d(TAG, "insertWebHabits: success");
                        insertWebHabitsInteractor.insertHabitWithProgressesList().subscribe();
                    }

                    @Override
                    public void onError(Throwable e) {
                        isInsertedSingleLiveEvent.postValue(false);
                        Log.d(TAG, "insertWebHabits: error: " + e.getMessage());
                    }
                });

    }

    public SingleLiveEvent<Boolean> getIsInsertedSingleLiveEvent() {
        return isInsertedSingleLiveEvent;
    }

    public SingleLiveEvent<Boolean> getIsLoadedSingleLiveEvent() {
        return isLoadedSingleLiveEvent;
    }
}
