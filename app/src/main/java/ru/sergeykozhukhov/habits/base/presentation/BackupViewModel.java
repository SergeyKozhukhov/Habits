package ru.sergeykozhukhov.habits.base.presentation;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.base.domain.SingleLiveEvent;
import ru.sergeykozhukhov.habits.base.domain.usecase.BackupWebHabitListWebInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.DeleteAllHabitsDbInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.ReplicationListHabitsWebInteractor;
import ru.sergeykozhukhov.habits.base.model.domain.HabitWithProgresses;
import ru.sergeykozhukhov.habits.base.model.exception.DeleteFromDbException;
import ru.sergeykozhukhov.habits.base.model.exception.GetJwtException;
import ru.sergeykozhukhov.habits.base.model.exception.InsertWebException;
import ru.sergeykozhukhov.habits.base.model.exception.LoadDbException;

/**
 * ViewModel для резервного копирования всей информации
 */
public class BackupViewModel extends ViewModel {

    private static final String TAG = "HabitsListViewModel";

    private final BackupWebHabitListWebInteractor insertWebHabitsInteractor;
    private final ReplicationListHabitsWebInteractor replicationListHabitsWebInteractor;
    private final DeleteAllHabitsDbInteractor deleteAllHabitsInteractor;

    private CompositeDisposable compositeDisposable;

    private SingleLiveEvent<Boolean> isInsertedSingleLiveEvent;
    private SingleLiveEvent<Boolean> isLoadedSingleLiveEvent;


    private final SingleLiveEvent<Integer> successSingleLiveEvent = new SingleLiveEvent<>();
    private final SingleLiveEvent<Integer> errorSingleLiveEvent = new SingleLiveEvent<>();

    public BackupViewModel(
            @NonNull BackupWebHabitListWebInteractor insertWebHabitsInteractor,
            @NonNull ReplicationListHabitsWebInteractor replicationListHabitsWebInteractor,
            @NonNull DeleteAllHabitsDbInteractor deleteAllHabitsInteractor) {
        this.insertWebHabitsInteractor = insertWebHabitsInteractor;
        this.replicationListHabitsWebInteractor = replicationListHabitsWebInteractor;
        this.deleteAllHabitsInteractor = deleteAllHabitsInteractor;

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
                .subscribe(habitsWithProgresses -> {
                    isLoadedSingleLiveEvent.postValue(true);
                    for (HabitWithProgresses habitsWithProgress : habitsWithProgresses) {
                        Log.d(TAG, "loadListHabitsWeb" + habitsWithProgress.toString());
                    }
                }, throwable -> {
                    if (throwable instanceof GetJwtException) {
                        errorSingleLiveEvent.postValue((((GetJwtException) throwable).getMessageRes()));
                    }
                });
        compositeDisposable.add(disposable);
    }

    public void insertHabitWithProgressesList(){
        compositeDisposable.add(insertWebHabitsInteractor.insertHabitWithProgressesList()
                .subscribeOn(Schedulers.newThread())
                .subscribe(() -> {
                    isInsertedSingleLiveEvent.postValue(true);
                    Log.d(TAG, "insertWebHabits: success");
                    insertWebHabitsInteractor.insertHabitWithProgressesList().subscribe();
                }, throwable -> {
                    if (throwable instanceof GetJwtException) {
                        errorSingleLiveEvent.postValue((((GetJwtException) throwable).getMessageRes()));
                    }
                    else if (throwable instanceof LoadDbException){
                        errorSingleLiveEvent.postValue(((LoadDbException) throwable).getMessageRes());
                    }
                    else if (throwable instanceof InsertWebException){
                        errorSingleLiveEvent.postValue(((InsertWebException)throwable).getMessageRes());
                    }
                }));

    }

    public void deleteAllHabits(){
        compositeDisposable.add(deleteAllHabitsInteractor.deleteAllHabits()
                .subscribeOn(Schedulers.newThread())
                .subscribe(() -> {
                    successSingleLiveEvent.postValue(R.string.delete_from_db_success_message);
                }, throwable -> {
                    if (throwable instanceof DeleteFromDbException) {
                        errorSingleLiveEvent.postValue((((DeleteFromDbException) throwable).getMessageRes()));
                    }
                }));
    }

    public SingleLiveEvent<Boolean> getIsInsertedSingleLiveEvent() {
        return isInsertedSingleLiveEvent;
    }

    public SingleLiveEvent<Boolean> getIsLoadedSingleLiveEvent() {
        return isLoadedSingleLiveEvent;
    }

    public SingleLiveEvent<Integer> getErrorSingleLiveEvent() {
        return errorSingleLiveEvent;
    }

    public SingleLiveEvent<Integer> getSuccessSingleLiveEvent() {
        return successSingleLiveEvent;
    }
}
