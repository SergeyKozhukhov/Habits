package ru.sergeykozhukhov.habits.presentation.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.SingleLiveEvent;
import ru.sergeykozhukhov.habits.domain.usecaseimpl.ChangeProgressListDbInteractor;
import ru.sergeykozhukhov.habits.model.domain.exception.ChangeProgressException;
import ru.sergeykozhukhov.habits.model.domain.exception.DeleteFromDbException;
import ru.sergeykozhukhov.habits.model.domain.exception.InsertDbException;
import ru.sergeykozhukhov.habits.model.domain.exception.LoadDbException;


/**
 * ViewModel для изменения списка дат выполнения конкретной привычки
 */
public class ProgressViewModel extends ViewModel {

    /**
     * Интерактор по изменению дат выполнения привычки
     */
    private final ChangeProgressListDbInteractor changeProgressListDbInteractor;

    /**
     * LiveData со списком дат выполнения привычки
     */
    private final SingleLiveEvent<List<Date>> dateListLoadedSingleLiveEvent = new SingleLiveEvent<>();

    /**
     * LiveData с идентификаторами строковых ресурсов сообщений о успешном выполненнии операции
     */
    private final SingleLiveEvent<Integer> successSingleLiveEvent = new SingleLiveEvent<>();

    /**
     * LiveData с идентификаторами строковых ресурсов сообщений об ошибках
     */
    private final SingleLiveEvent<Integer> errorSingleLiveEvent = new SingleLiveEvent<>();

    private CompositeDisposable compositeDisposable;

    public ProgressViewModel(
            @NonNull ChangeProgressListDbInteractor changeProgressListDbInteractor) {
        this.changeProgressListDbInteractor = changeProgressListDbInteractor;
        compositeDisposable = new CompositeDisposable();
    }

    public void initChangeProgressList(long idHabit) {
        compositeDisposable.add(
                changeProgressListDbInteractor.getProgressList(idHabit)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(dateListLoadedSingleLiveEvent::postValue, throwable -> {
                            if (throwable instanceof LoadDbException) {
                                errorSingleLiveEvent.postValue((((LoadDbException) throwable).getMessageRes()));
                            }
                        }));
    }

    public void addProgress(Date date) {
        try {
            changeProgressListDbInteractor.addNewDate(date);
        } catch (ChangeProgressException e) {
            errorSingleLiveEvent.postValue(e.getMessageRes());
        }
    }

    public void deleteProgress(Date date) {
        try {
            changeProgressListDbInteractor.deleteDate(date);
        } catch (ChangeProgressException e) {
            errorSingleLiveEvent.postValue(e.getMessageRes());
        }
    }

    public void saveProgressList() {
        Completable completable = changeProgressListDbInteractor.saveProgressList();
        if (completable != null) {
            compositeDisposable.add(completable.subscribeOn(Schedulers.newThread())
                    .subscribe(() ->
                            successSingleLiveEvent.postValue(R.string.change_progress_db_success_message), throwable -> {
                        if (throwable instanceof DeleteFromDbException) {
                            errorSingleLiveEvent.postValue((((DeleteFromDbException) throwable).getMessageRes()));
                        }
                        if (throwable instanceof InsertDbException) {
                            errorSingleLiveEvent.postValue((((InsertDbException) throwable).getMessageRes()));
                        }
                    }));
        }
    }

    public SingleLiveEvent<List<Date>> getDateListLoadedSingleLiveEvent() {
        return dateListLoadedSingleLiveEvent;
    }

    public SingleLiveEvent<Integer> getSuccessSingleLiveEvent() {
        return successSingleLiveEvent;
    }

    public SingleLiveEvent<Integer> getErrorSingleLiveEvent() {
        return errorSingleLiveEvent;
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
    }
}

