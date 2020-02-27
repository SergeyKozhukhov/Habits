package ru.sergeykozhukhov.habits.presentation.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.SingleLiveEvent;
import ru.sergeykozhukhov.habits.domain.usecaseimpl.BackupWebHabitListWebInteractor;
import ru.sergeykozhukhov.habits.domain.usecaseimpl.DeleteJwtInteractor;
import ru.sergeykozhukhov.habits.domain.usecaseimpl.ReplicationListHabitsWebInteractor;
import ru.sergeykozhukhov.habits.model.domain.exception.DeleteFromDbException;
import ru.sergeykozhukhov.habits.model.domain.exception.GetJwtException;
import ru.sergeykozhukhov.habits.model.domain.exception.BackupException;
import ru.sergeykozhukhov.habits.model.domain.exception.InsertDbException;
import ru.sergeykozhukhov.habits.model.domain.exception.LoadDbException;
import ru.sergeykozhukhov.habits.model.domain.exception.ReplicationException;

/**
 * ViewModel для операций доступных в аккаунте (резервное копирования, восстановление информации)
 */
public class AccountViewModel extends ViewModel {

    /**
     * Интерактор создания резервной копии данных о привычках на сервере
     */
    private final BackupWebHabitListWebInteractor insertWebHabitsInteractor;

    /**
     * Интерактор восстановления данных о привычках с сервера
     */
    private final ReplicationListHabitsWebInteractor replicationListHabitsWebInteractor;

    /**
     * Интерактор выхода пользователя из аккаунта
     */
    private final DeleteJwtInteractor deleteJwtInteractor;

    /**
     * LiveData с идентификаторами строковых ресурсов сообщений о успешном выполненнии операций (резервное копирование, восстановение данных)
     */
    private final SingleLiveEvent<Integer> successSingleLiveEvent = new SingleLiveEvent<>();

    /**
     * LiveData с идентификаторами строковых ресурсов сообщений о успешном выполненнии операции выхода из аккаунта
     */
    private final SingleLiveEvent<Integer> logOutSuccessSingleLiveEvent = new SingleLiveEvent<>();

    /**
     * LiveData с идентификаторами строковых ресурсов сообщений об ошибках
     */
    private final SingleLiveEvent<Integer> errorSingleLiveEvent = new SingleLiveEvent<>();

    /**
     * LiveData с состоянием выполнения операций (true - операция выполняется, false - операция закончена)
     */
    private final MutableLiveData<Boolean> isLoadingMutableLiveData = new MutableLiveData<>();

    private CompositeDisposable compositeDisposable;

    public AccountViewModel(
            @NonNull BackupWebHabitListWebInteractor insertWebHabitsInteractor,
            @NonNull ReplicationListHabitsWebInteractor replicationListHabitsWebInteractor,
            @NonNull DeleteJwtInteractor deleteJwtInteractor) {
        this.insertWebHabitsInteractor = insertWebHabitsInteractor;
        this.replicationListHabitsWebInteractor = replicationListHabitsWebInteractor;
        this.deleteJwtInteractor = deleteJwtInteractor;
        compositeDisposable = new CompositeDisposable();
    }

    /**
     * Восстановление данных с сервера
     */
    public void replication() {
        isLoadingMutableLiveData.setValue(true);
        compositeDisposable.add(replicationListHabitsWebInteractor.loadHabitWithProgressesList()
                .subscribeOn(Schedulers.newThread())
                .doOnTerminate(() -> isLoadingMutableLiveData.postValue(false))
                .subscribe(() -> successSingleLiveEvent.postValue(R.string.replication_success_message), throwable -> {
                    if (throwable instanceof GetJwtException)
                        errorSingleLiveEvent.postValue((((GetJwtException) throwable).getMessageRes()));
                    else if (throwable instanceof ReplicationException)
                        errorSingleLiveEvent.postValue(((ReplicationException) throwable).getMessageRes());
                    else if (throwable instanceof DeleteFromDbException)
                        errorSingleLiveEvent.postValue(((DeleteFromDbException) throwable).getMessageRes());
                    else if (throwable instanceof InsertDbException)
                        errorSingleLiveEvent.postValue(((InsertDbException) throwable).getMessageRes());
                }));
    }

    /**
     * Создание резервной копии данных на сервере
     */
    public void backup() {
        isLoadingMutableLiveData.setValue(true);
        compositeDisposable.add(insertWebHabitsInteractor.insertHabitWithProgressesList()
                .subscribeOn(Schedulers.newThread())
                .doOnTerminate(() -> isLoadingMutableLiveData.postValue(false))
                .subscribe(() -> successSingleLiveEvent.postValue(R.string.backup_success_message), throwable -> {
                    if (throwable instanceof GetJwtException) {
                        errorSingleLiveEvent.postValue((((GetJwtException) throwable).getMessageRes()));
                    } else if (throwable instanceof LoadDbException) {
                        errorSingleLiveEvent.postValue(((LoadDbException) throwable).getMessageRes());
                    } else if (throwable instanceof BackupException) {
                        errorSingleLiveEvent.postValue(((BackupException) throwable).getMessageRes());
                    }
                }));

    }

    /**
     * Выход пользователя из аккаунта
     */
    public void logout() {
        deleteJwtInteractor.deleteJwt();
        logOutSuccessSingleLiveEvent.postValue(R.string.log_out_success_message);
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

    public MutableLiveData<Boolean> getIsLoadingMutableLiveData() {
        return isLoadingMutableLiveData;
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
    }
}
