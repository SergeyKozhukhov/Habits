package ru.sergeykozhukhov.habits.base.presentation;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habits.base.domain.SingleLiveEvent;
import ru.sergeykozhukhov.habits.base.domain.usecase.ChangeProgressListDbInteractor;

public class ProgressViewModel extends ViewModel {

    private static final String TAG = "ProgressViewModel";

    private final ChangeProgressListDbInteractor changeProgressListDbInteractor;


    private CompositeDisposable compositeDisposable;

    private SingleLiveEvent<List<Date>> dateListLoadedSingleLiveEvent;
    private SingleLiveEvent<Boolean> progressInsertedSingleLiveEvent;
    private SingleLiveEvent<Boolean> progressListInsertedSingleLiveEvent;

    public ProgressViewModel(
            @NonNull ChangeProgressListDbInteractor changeProgressListDbInteractor) {
        this.changeProgressListDbInteractor = changeProgressListDbInteractor;
        initData();
    }

    private void initData() {
        compositeDisposable = new CompositeDisposable();
        dateListLoadedSingleLiveEvent = new SingleLiveEvent<>();
        progressInsertedSingleLiveEvent = new SingleLiveEvent<>();
        progressListInsertedSingleLiveEvent = new SingleLiveEvent<>();
    }

    public void initChangeProgressList(long idHabit) {
        compositeDisposable.add(
                changeProgressListDbInteractor.getProgressList(idHabit)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<Date>>() {
                            @Override
                            public void accept(List<Date> dates) throws Exception {
                                dateListLoadedSingleLiveEvent.postValue(dates);
                            }
                        }));
    }

    public void addProgress(Date date) {
        changeProgressListDbInteractor.addNewDate(date);
    }

    public void deleteProgress(Date date) {
        changeProgressListDbInteractor.deleteDate(date);
    }

    public void saveProgressList() {
        Completable completable = changeProgressListDbInteractor.saveProgressList();
        if (completable!=null) {
                completable.subscribeOn(Schedulers.newThread())
                    .subscribe();
        }
    }

    public SingleLiveEvent<Boolean> getProgressListInsertedSingleLiveEvent() {
        return progressListInsertedSingleLiveEvent;
    }

    public SingleLiveEvent<Boolean> getProgressInsertedSingleLiveEvent() {
        return progressInsertedSingleLiveEvent;
    }

    public SingleLiveEvent<List<Date>> getDateListLoadedSingleLiveEvent() {
        return dateListLoadedSingleLiveEvent;
    }
}

