package ru.sergeykozhukhov.habits.presentation.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.SingleLiveEvent;
import ru.sergeykozhukhov.habits.domain.usecase.BackupWebHabitListWebInteractor;
import ru.sergeykozhukhov.habits.domain.usecase.DeleteAllHabitsDbInteractor;
import ru.sergeykozhukhov.habits.domain.usecase.DeleteJwtInteractor;
import ru.sergeykozhukhov.habits.domain.usecase.ReplicationListHabitsWebInteractor;
import ru.sergeykozhukhov.habits.model.domain.HabitWithProgresses;
import ru.sergeykozhukhov.habits.model.exception.DeleteFromDbException;
import ru.sergeykozhukhov.habits.model.exception.GetJwtException;
import ru.sergeykozhukhov.habits.model.exception.InsertWebException;
import ru.sergeykozhukhov.habits.model.exception.LoadDbException;

/**
 * ViewModel для резервного копирования всей информации
 */
public class AccountViewModel extends ViewModel {

    private static final String TAG = "HabitsListViewModel";

    private final BackupWebHabitListWebInteractor insertWebHabitsInteractor;
    private final ReplicationListHabitsWebInteractor replicationListHabitsWebInteractor;
    private final DeleteJwtInteractor deleteJwtInteractor;

    private CompositeDisposable compositeDisposable;

    private SingleLiveEvent<Boolean> isInsertedSingleLiveEvent;
    private SingleLiveEvent<Boolean> isLoadedSingleLiveEvent;


    private final SingleLiveEvent<Integer> successSingleLiveEvent = new SingleLiveEvent<>();
    private final SingleLiveEvent<Integer> logOutSuccessSingleLiveEvent = new SingleLiveEvent<>();
    private final SingleLiveEvent<Integer> errorSingleLiveEvent = new SingleLiveEvent<>();

    public AccountViewModel(
            @NonNull BackupWebHabitListWebInteractor insertWebHabitsInteractor,
            @NonNull ReplicationListHabitsWebInteractor replicationListHabitsWebInteractor,
            @NonNull DeleteJwtInteractor deleteJwtInteractor) {
        this.insertWebHabitsInteractor = insertWebHabitsInteractor;
        this.replicationListHabitsWebInteractor = replicationListHabitsWebInteractor;
        this.deleteJwtInteractor = deleteJwtInteractor;

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


    public void logout(){
        deleteJwtInteractor.deleteJwt();
        logOutSuccessSingleLiveEvent.postValue(R.string.log_out__success_message);
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

    public SingleLiveEvent<Integer> getLogOutSuccessSingleLiveEvent() {
        return logOutSuccessSingleLiveEvent;
    }
}
