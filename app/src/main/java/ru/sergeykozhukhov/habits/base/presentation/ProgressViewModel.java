package ru.sergeykozhukhov.habits.base.presentation;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habits.base.domain.SingleLiveEvent;
import ru.sergeykozhukhov.habits.base.domain.usecase.InsertProgressDbInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.InsertProgressListDbInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.LoadProgressListDbInteractor;
import ru.sergeykozhukhov.habits.base.model.domain.Progress;

public class ProgressViewModel extends ViewModel {

    private static final String TAG = "ProgressViewModel";

    private final LoadProgressListDbInteractor loadProgressListDbInteractor;
    private final InsertProgressDbInteractor insertProgressDbInteractor;
    private final InsertProgressListDbInteractor insertProgressListDbInteractor;

    private CompositeDisposable compositeDisposable;

    private SingleLiveEvent<List<Progress>> progressListLoadedSingleLiveEvent;
    private SingleLiveEvent<Boolean> progressInsertedSingleLiveEvent;
    private SingleLiveEvent<Boolean> progressListInsertedSingleLiveEvent;

    public ProgressViewModel(
            @NonNull LoadProgressListDbInteractor loadProgressListDbInteractor,
            @NonNull InsertProgressDbInteractor insertProgressDbInteractor,
            @NonNull InsertProgressListDbInteractor insertProgressListDbInteractor) {
        this.loadProgressListDbInteractor = loadProgressListDbInteractor;
        this.insertProgressDbInteractor = insertProgressDbInteractor;
        this.insertProgressListDbInteractor = insertProgressListDbInteractor;
        initData();
    }

    private void initData(){
        compositeDisposable = new CompositeDisposable();
        progressListLoadedSingleLiveEvent = new SingleLiveEvent<>();
        progressInsertedSingleLiveEvent = new SingleLiveEvent<>();
        progressListInsertedSingleLiveEvent = new SingleLiveEvent<>();
    }

    public void loadProgressListByHabit(long idHabit){

        compositeDisposable.add(
                loadProgressListDbInteractor.loadProgressListByHabit(idHabit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Progress>>() {
                    @Override
                    public void accept(List<Progress> progresses) throws Exception {
                        progressListLoadedSingleLiveEvent.postValue(progresses);
                    }
                }));
    }

    public void insertProgress(Progress progress){
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


    public void insertProgressList(List<Progress> progressList){
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


    public SingleLiveEvent<Boolean> getProgressListInsertedSingleLiveEvent(){
        return progressListInsertedSingleLiveEvent;
    }


    public SingleLiveEvent<Boolean> getProgressInsertedSingleLiveEvent() {
        return progressInsertedSingleLiveEvent;
    }

    public SingleLiveEvent<List<Progress>> getProgressListLoadedSingleLiveEvent() {
        return progressListLoadedSingleLiveEvent;
    }
}

