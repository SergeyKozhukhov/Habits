package ru.sergeykozhukhov.habits.presentation.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.SingleLiveEvent;
import ru.sergeykozhukhov.habits.domain.usecase.DeleteAllHabitsDbInteractor;
import ru.sergeykozhukhov.habits.model.domain.exception.DeleteFromDbException;

/**
 * ViewModel для резервного копирования всей информации
 */
public class SettingsViewModel extends ViewModel {

    private static final String TAG = "SettingsViewModel";

    private final DeleteAllHabitsDbInteractor deleteAllHabitsInteractor;

    private CompositeDisposable compositeDisposable;

    private final SingleLiveEvent<Integer> successSingleLiveEvent = new SingleLiveEvent<>();
    private final SingleLiveEvent<Integer> errorSingleLiveEvent = new SingleLiveEvent<>();

    public SettingsViewModel(@NonNull DeleteAllHabitsDbInteractor deleteAllHabitsInteractor) {
        this.deleteAllHabitsInteractor = deleteAllHabitsInteractor;
        compositeDisposable = new CompositeDisposable();
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


    public SingleLiveEvent<Integer> getErrorSingleLiveEvent() {
        return errorSingleLiveEvent;
    }

    public SingleLiveEvent<Integer> getSuccessSingleLiveEvent() {
        return successSingleLiveEvent;
    }

    public void cancelSubscriptions() {
        compositeDisposable.clear();
    }

}
