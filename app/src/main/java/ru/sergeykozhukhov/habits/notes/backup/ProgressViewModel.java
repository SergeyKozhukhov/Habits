package ru.sergeykozhukhov.habits.notes.backup;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habits.base.domain.SingleLiveEvent;
import ru.sergeykozhukhov.habits.base.domain.usecase.ChangeProgressListDbInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.util.InsertProgressDbInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.util.InsertProgressListDbInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.util.LoadProgressListDbInteractor;
import ru.sergeykozhukhov.habits.base.model.domain.Progress;

public class ProgressViewModel extends ViewModel {

    private static final String TAG = "ProgressViewModel";

    private final LoadProgressListDbInteractor loadProgressListDbInteractor;
    private final InsertProgressDbInteractor insertProgressDbInteractor;
    private final InsertProgressListDbInteractor insertProgressListDbInteractor;

    private final ChangeProgressListDbInteractor changeProgressListDbInteractor;


    private CompositeDisposable compositeDisposable;

    private SingleLiveEvent<List<Date>> dateListLoadedSingleLiveEvent;
    private SingleLiveEvent<Boolean> progressInsertedSingleLiveEvent;
    private SingleLiveEvent<Boolean> progressListInsertedSingleLiveEvent;

    public ProgressViewModel(
            @NonNull LoadProgressListDbInteractor loadProgressListDbInteractor,
            @NonNull InsertProgressDbInteractor insertProgressDbInteractor,
            @NonNull InsertProgressListDbInteractor insertProgressListDbInteractor,
            @NonNull ChangeProgressListDbInteractor changeProgressListDbInteractor) {
        this.loadProgressListDbInteractor = loadProgressListDbInteractor;
        this.insertProgressDbInteractor = insertProgressDbInteractor;
        this.insertProgressListDbInteractor = insertProgressListDbInteractor;
        this.changeProgressListDbInteractor = changeProgressListDbInteractor;
        initData();
    }

    private void initData() {
        compositeDisposable = new CompositeDisposable();
        dateListLoadedSingleLiveEvent = new SingleLiveEvent<>();
        progressInsertedSingleLiveEvent = new SingleLiveEvent<>();
        progressListInsertedSingleLiveEvent = new SingleLiveEvent<>();
    }

    public void loadProgressListByHabit(long idHabit) {

        compositeDisposable.add(
                loadProgressListDbInteractor.loadProgressListByHabit(idHabit)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<Progress>>() {
                            @Override
                            public void accept(List<Progress> progresses) throws Exception {
                                // dateListLoadedSingleLiveEvent.postValue(progresses);
                            }
                        }));
    }

    public void insertProgress(Progress progress) {
        insertProgressDbInteractor.insertProgress(progress)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        progressInsertedSingleLiveEvent.postValue(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressInsertedSingleLiveEvent.postValue(false);
                    }
                });
    }

    public void insertProgressList(List<Progress> progressList) {
        insertProgressListDbInteractor.insertProgressList(progressList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        progressListInsertedSingleLiveEvent.postValue(true);
                        Log.d(TAG, "insertedProgressList success");

                    }

                    @Override
                    public void onError(Throwable e) {
                        progressListInsertedSingleLiveEvent.postValue(false);
                        Log.d(TAG, "insertedProgressList fail");
                    }
                });
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

    /*public void addProgress(Date date) {
        changeProgressListDbInteractor.addNewDate(date);
    }

    public void deleteProgress(Date date) {
        changeProgressListDbInteractor.deleteDate(date);
    }*/

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

