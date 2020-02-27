package ru.sergeykozhukhov.habits.presentation.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.SingleLiveEvent;
import ru.sergeykozhukhov.habits.domain.usecaseimpl.DeleteAllHabitsDbInteractor;
import ru.sergeykozhukhov.habits.model.domain.exception.DeleteFromDbException;

/**
 * ViewModel для настроек приложения и существенными операциями с данными
 */
public class SettingsViewModel extends ViewModel {

    /**
     * Реализация интерактора удаления всех привычек (и дат выполнения) из базы данных
     */
    private final DeleteAllHabitsDbInteractor deleteAllHabitsInteractor;

    /**
     * LiveData с идентификаторами строковых ресурсов сообщений о успешном выполненнии операции
     */
    private final SingleLiveEvent<Integer> successSingleLiveEvent = new SingleLiveEvent<>();

    /**
     * LiveData с идентификаторами строковых ресурсов сообщений об ошибках
     */
    private final SingleLiveEvent<Integer> errorSingleLiveEvent = new SingleLiveEvent<>();

    private CompositeDisposable compositeDisposable;

    public SettingsViewModel(@NonNull DeleteAllHabitsDbInteractor deleteAllHabitsInteractor) {
        this.deleteAllHabitsInteractor = deleteAllHabitsInteractor;
        compositeDisposable = new CompositeDisposable();
    }

    /**
     * Удаление всех привычек
     */
    public void deleteAllHabits() {

        compositeDisposable.add(deleteAllHabitsInteractor.deleteAllHabits()
                .subscribeOn(Schedulers.newThread())
                .subscribe(() -> successSingleLiveEvent.postValue(R.string.delete_from_db_success_message), throwable -> {
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

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
    }
}
