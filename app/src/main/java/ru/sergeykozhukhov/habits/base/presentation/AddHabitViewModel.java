package ru.sergeykozhukhov.habits.base.presentation;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.base.domain.SingleLiveEvent;
import ru.sergeykozhukhov.habits.base.domain.usecase.util.InsertHabitDbInteractor;
import ru.sergeykozhukhov.habits.base.model.exception.BuildException;

public class AddHabitViewModel extends ViewModel {

    private static final String TAG = "AddHabitViewModel";

    private final InsertHabitDbInteractor insertHabitInteractor;
    private final SingleLiveEvent<Integer> insertedSuccessSingleLiveEvent = new SingleLiveEvent<>();
    private final SingleLiveEvent<Integer> errorSingleLiveEvent = new SingleLiveEvent<>();

    private CompositeDisposable compositeDisposable;

    public AddHabitViewModel(
            @NonNull InsertHabitDbInteractor insertHabitInteractor) {
        this.insertHabitInteractor = insertHabitInteractor;
        initData();
    }

    private void initData() {
        compositeDisposable = new CompositeDisposable();
    }

    public void insertHabit(String title, String description, String startDate, String duration) {

        compositeDisposable.add(insertHabitInteractor.insertHabit(title, description, startDate, duration)
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long id) throws Exception {
                        insertedSuccessSingleLiveEvent.postValue(R.string.habit_success_inserted_db_message);
                        Log.d(TAG, "insertHabitList success. id = " + id);
                    }
                }, throwable -> {
                    if (throwable instanceof BuildException) {
                        errorSingleLiveEvent.postValue((((BuildException) throwable).getMessageRes()));
                    }
                    Log.d(TAG, "insertHabitList error");
                }));
    }

    public SingleLiveEvent<Integer> getInsertedSuccessSingleLiveEvent() {
        return insertedSuccessSingleLiveEvent;
    }

    public SingleLiveEvent<Integer> getErrorSingleLiveEvent() {
        return errorSingleLiveEvent;
    }

    public void cancelSubscribe() {
        compositeDisposable.clear();
    }
}
