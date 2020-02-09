package ru.sergeykozhukhov.habits.base.presentation;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableContainer;
import ru.sergeykozhukhov.habits.base.domain.SingleLiveEvent;
import ru.sergeykozhukhov.habits.base.domain.usecase.InsertHabitDbInteractor;
import ru.sergeykozhukhov.habits.base.model.domain.Habit;

public class AddHabitViewModel  extends ViewModel {

    private static final String TAG = "AddHabitViewModel";

    private final InsertHabitDbInteractor insertHabitInteractor;


    private CompositeDisposable compositeDisposable;

    private SingleLiveEvent<Boolean> isInsertedSingleLiveEvent;

    public AddHabitViewModel(
            @NonNull InsertHabitDbInteractor insertHabitInteractor) {
        this.insertHabitInteractor = insertHabitInteractor;
        initData();
    }

    private void initData(){
        isInsertedSingleLiveEvent = new SingleLiveEvent<>();
        compositeDisposable = new CompositeDisposable();

    }

    public void insertHabit(Habit habit){

        Disposable habitInsertedDisposable = insertHabitInteractor.insertHabit(habit)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long id) throws Exception {
                        isInsertedSingleLiveEvent.postValue(true);
                        Log.d(TAG, "insert success. id = "+id);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        isInsertedSingleLiveEvent.postValue(false);
                        Log.d(TAG, "insert error");
                    }
                });
        compositeDisposable.add(habitInsertedDisposable);
    }

    public SingleLiveEvent<Boolean> getIsInsertedSingleLiveEvent() {
        return isInsertedSingleLiveEvent;
    }

    public void cancelSubscribe(){
        compositeDisposable.clear();
    }
}
