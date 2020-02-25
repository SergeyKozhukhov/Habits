package ru.sergeykozhukhov.habits.presentation.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.SingleLiveEvent;
import ru.sergeykozhukhov.habits.domain.usecase.InsertHabitDbInteractor;
import ru.sergeykozhukhov.habits.model.domain.exception.BuildException;
import ru.sergeykozhukhov.habits.model.domain.exception.InsertDbException;

/**
 * ViewModel для добавления новой привычки в базу данных
 */
public class AddHabitViewModel extends ViewModel {

    private static final String TAG = "AddHabitViewModel";

    private final InsertHabitDbInteractor insertHabitInteractor;
    private final SingleLiveEvent<Integer> insertedSuccessSingleLiveEvent = new SingleLiveEvent<>();
    private final SingleLiveEvent<Integer> errorSingleLiveEvent = new SingleLiveEvent<>();

    private CompositeDisposable compositeDisposable;

    public AddHabitViewModel(@NonNull InsertHabitDbInteractor insertHabitInteractor) {
        this.insertHabitInteractor = insertHabitInteractor;
        compositeDisposable = new CompositeDisposable();
    }

    public void insertHabit(String title, String description, String startDate, String duration) {

        compositeDisposable.add(insertHabitInteractor.insertHabit(title, description, startDate, duration)
                .subscribeOn(Schedulers.io())
                .subscribe(id -> insertedSuccessSingleLiveEvent.postValue(R.string.habit_success_inserted_db_message), new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        if (throwable instanceof BuildException)
                            errorSingleLiveEvent.postValue((((BuildException) throwable).getMessageRes()));
                        else if (throwable instanceof InsertDbException)
                            errorSingleLiveEvent.postValue((((InsertDbException) throwable).getMessageRes()));
                    }
                }));
    }

    public SingleLiveEvent<Integer> getInsertedSuccessSingleLiveEvent() {
        return insertedSuccessSingleLiveEvent;
    }

    public SingleLiveEvent<Integer> getErrorSingleLiveEvent() {
        return errorSingleLiveEvent;
    }

    public void cancelSubscritions() {
        compositeDisposable.clear();
    }
}
