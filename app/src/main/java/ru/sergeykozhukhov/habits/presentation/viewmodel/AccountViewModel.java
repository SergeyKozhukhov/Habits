package ru.sergeykozhukhov.habits.presentation.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.SingleLiveEvent;
import ru.sergeykozhukhov.habits.domain.usecase.BackupWebHabitListWebInteractor;
import ru.sergeykozhukhov.habits.domain.usecase.DeleteJwtInteractor;
import ru.sergeykozhukhov.habits.domain.usecase.ReplicationListHabitsWebInteractor;
import ru.sergeykozhukhov.habits.model.domain.exception.DeleteFromDbException;
import ru.sergeykozhukhov.habits.model.domain.exception.GetJwtException;
import ru.sergeykozhukhov.habits.model.domain.exception.BackupException;
import ru.sergeykozhukhov.habits.model.domain.exception.InsertDbException;
import ru.sergeykozhukhov.habits.model.domain.exception.LoadDbException;
import ru.sergeykozhukhov.habits.model.domain.exception.ReplicationException;

/**
 * ViewModel для резервного копирования всей информации
 */
public class AccountViewModel extends ViewModel {

    private static final String TAG = "HabitsListViewModel";

    private final BackupWebHabitListWebInteractor insertWebHabitsInteractor;
    private final ReplicationListHabitsWebInteractor replicationListHabitsWebInteractor;
    private final DeleteJwtInteractor deleteJwtInteractor;

    private CompositeDisposable compositeDisposable;

    private final SingleLiveEvent<Integer> successSingleLiveEvent = new SingleLiveEvent<>();
    private final SingleLiveEvent<Integer> logOutSuccessSingleLiveEvent = new SingleLiveEvent<>();
    private final SingleLiveEvent<Integer> errorSingleLiveEvent = new SingleLiveEvent<>();
    private final MutableLiveData<Boolean> isLoadingMutableLiveData = new MutableLiveData<>();

    public AccountViewModel(
            @NonNull BackupWebHabitListWebInteractor insertWebHabitsInteractor,
            @NonNull ReplicationListHabitsWebInteractor replicationListHabitsWebInteractor,
            @NonNull DeleteJwtInteractor deleteJwtInteractor) {
        this.insertWebHabitsInteractor = insertWebHabitsInteractor;
        this.replicationListHabitsWebInteractor = replicationListHabitsWebInteractor;
        this.deleteJwtInteractor = deleteJwtInteractor;
        compositeDisposable = new CompositeDisposable();
    }

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

    public void cancelSubscritions() {
        compositeDisposable.clear();
    }
}
