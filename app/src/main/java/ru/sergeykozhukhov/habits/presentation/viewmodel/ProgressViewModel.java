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
import ru.sergeykozhukhov.habits.domain.usecase.ChangeProgressListDbInteractor;
import ru.sergeykozhukhov.habits.model.exception.ChangeProgressException;
import ru.sergeykozhukhov.habits.model.exception.DeleteFromDbException;
import ru.sergeykozhukhov.habits.model.exception.InsertDbException;
import ru.sergeykozhukhov.habits.model.exception.LoadDbException;


/**
 * ViewModel для изменения списка дат выполнения конкретной привычки
 */
public class ProgressViewModel extends ViewModel {

    private static final String TAG = "ProgressViewModel";

    private final ChangeProgressListDbInteractor changeProgressListDbInteractor;


    private CompositeDisposable compositeDisposable;

    private final SingleLiveEvent<List<Date>> dateListLoadedSingleLiveEvent = new SingleLiveEvent<>();

    private final SingleLiveEvent<Integer> successSingleLiveEvent = new SingleLiveEvent<>();
    private final SingleLiveEvent<Integer> errorSingleLiveEvent = new SingleLiveEvent<>();

    public ProgressViewModel(
            @NonNull ChangeProgressListDbInteractor changeProgressListDbInteractor) {
        this.changeProgressListDbInteractor = changeProgressListDbInteractor;
        initData();
    }

    private void initData() {
        compositeDisposable = new CompositeDisposable();
    }

    public void initChangeProgressList(long idHabit) {
        compositeDisposable.add(
                changeProgressListDbInteractor.getProgressList(idHabit)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(value -> dateListLoadedSingleLiveEvent.postValue(value), throwable -> {
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
        if (completable!=null) {
                compositeDisposable.add(completable.subscribeOn(Schedulers.newThread())
                    .subscribe(() ->
                            successSingleLiveEvent.postValue(R.string.change_progress_db_success_message), throwable -> {
                                if (throwable instanceof DeleteFromDbException) {
                                    errorSingleLiveEvent.postValue((((DeleteFromDbException) throwable).getMessageRes()));
                                }
                                if (throwable instanceof InsertDbException) {
                                    errorSingleLiveEvent.postValue((((InsertDbException)throwable).getMessageRes()));
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
}

