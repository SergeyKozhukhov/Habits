package ru.sergeykozhukhov.habits.presentation.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Collections;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import ru.sergeykozhukhov.habits.domain.SingleLiveEvent;
import ru.sergeykozhukhov.habits.domain.usecaseimpl.LoadHabitListDbInteractor;
import ru.sergeykozhukhov.habits.model.domain.Habit;
import ru.sergeykozhukhov.habits.model.domain.exception.LoadDbException;

/**
 * ViewModel для получения списка всех привычек из базы данных
 */
public class HabitsListViewModel extends ViewModel {

    /**
     * Интерактор получения списка привычек из базы данных
     */
    private final LoadHabitListDbInteractor loadHabitsInteractor;

    /**
     * LiveData со списком привычек
     */
    private final MutableLiveData<List<Habit>> habitListLiveData = new MutableLiveData<>();

    /**
     * LiveData с идентификаторами строковых ресурсов сообщений о успешном выполненнии операции
     */
    private final SingleLiveEvent<Integer> errorSingleLiveEvent = new SingleLiveEvent<>();

    /**
     * LiveData с идентификаторами строковых ресурсов сообщений об ошибках
     */
    private final MutableLiveData<Boolean> isLoadingMutableLiveData = new MutableLiveData<>();

    private CompositeDisposable compositeDisposable;

    public HabitsListViewModel(@NonNull LoadHabitListDbInteractor loadHabitsInteractor) {
        this.loadHabitsInteractor = loadHabitsInteractor;
        compositeDisposable = new CompositeDisposable();
    }

    /**
     * Загрузка всех привычек из базы данных
     */
    public void loadHabitList() {
        isLoadingMutableLiveData.setValue(true);
        compositeDisposable.add(loadHabitsInteractor.loadHabitList()
                .doOnTerminate(() -> isLoadingMutableLiveData.postValue(false))
                .subscribe(value -> {
                    Collections.reverse(value);
                    habitListLiveData.postValue(value);
                }, throwable -> {
                    if (throwable instanceof LoadDbException) {
                        errorSingleLiveEvent.postValue((((LoadDbException) throwable).getMessageRes()));
                    }
                }));
    }


    @NonNull
    public MutableLiveData<List<Habit>> getHabitListLiveData() {
        return habitListLiveData;
    }

    public SingleLiveEvent<Integer> getErrorSingleLiveEvent() {
        return errorSingleLiveEvent;
    }

    public MutableLiveData<Boolean> getIsLoadingMutableLiveData() {
        return isLoadingMutableLiveData;
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
    }
}
