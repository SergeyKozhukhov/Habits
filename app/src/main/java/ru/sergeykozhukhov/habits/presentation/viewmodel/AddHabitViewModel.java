package ru.sergeykozhukhov.habits.presentation.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.SingleLiveEvent;
import ru.sergeykozhukhov.habits.domain.usecaseimpl.InsertHabitDbInteractor;
import ru.sergeykozhukhov.habits.model.domain.exception.BuildException;
import ru.sergeykozhukhov.habits.model.domain.exception.InsertDbException;

/**
 * ViewModel для добавления новой привычки в базу данных
 */
public class AddHabitViewModel extends ViewModel {

    /**
     * Интерактор добавления привычки в базу данных
     */
    private final InsertHabitDbInteractor insertHabitInteractor;

    /**
     * LiveData с идентификаторами строковых ресурсов сообщений о успешном выполненнии операции
     */
    private final SingleLiveEvent<Integer> successSingleLiveEvent = new SingleLiveEvent<>();

    /**
     * LiveData с идентификаторами строковых ресурсов сообщений об ошибках
     */
    private final SingleLiveEvent<Integer> errorSingleLiveEvent = new SingleLiveEvent<>();

    private CompositeDisposable compositeDisposable;

    public AddHabitViewModel(@NonNull InsertHabitDbInteractor insertHabitInteractor) {
        this.insertHabitInteractor = insertHabitInteractor;
        compositeDisposable = new CompositeDisposable();
    }

    /**
     * Добавление новой привычки
     *
     * @param title       название
     * @param description описание
     * @param startDate   дата начала
     * @param duration    продолжительность в кол-ве дней
     */
    public void insertHabit(String title, String description, String startDate, String duration) {

        compositeDisposable.add(insertHabitInteractor.insertHabit(title, description, startDate, duration)
                .subscribeOn(Schedulers.io())
                .subscribe(id -> successSingleLiveEvent.postValue(R.string.habit_success_inserted_db_message), throwable -> {
                    if (throwable instanceof BuildException)
                        errorSingleLiveEvent.postValue((((BuildException) throwable).getMessageRes()));
                    else if (throwable instanceof InsertDbException)
                        errorSingleLiveEvent.postValue((((InsertDbException) throwable).getMessageRes()));
                }));
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
