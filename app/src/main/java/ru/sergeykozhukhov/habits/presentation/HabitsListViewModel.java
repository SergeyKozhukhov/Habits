package ru.sergeykozhukhov.habits.presentation;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ru.sergeykozhukhov.habits.domain.SingleLiveEvent;
import ru.sergeykozhukhov.habits.domain.usecase.LoadHabitListDbInteractor;
import ru.sergeykozhukhov.habits.model.domain.Habit;
import ru.sergeykozhukhov.habits.model.exception.LoadDbException;

/**
 * ViewModel для получения списка всех привычек из базы данных
 */
public class HabitsListViewModel extends ViewModel {

    private static final String TAG = "HabitsListViewModel";

    private final LoadHabitListDbInteractor loadHabitsInteractor;

    private CompositeDisposable compositeDisposable;

    private final MutableLiveData<List<Habit>> habitListLiveData = new MutableLiveData<>();
    private final SingleLiveEvent<Integer> errorSingleLiveEvent = new SingleLiveEvent<>();

    public HabitsListViewModel(@NonNull LoadHabitListDbInteractor loadHabitsInteractor) {
        this.loadHabitsInteractor = loadHabitsInteractor;


        compositeDisposable = new CompositeDisposable();
    }

    public void loadHabitList(){

        Disposable disposable = loadHabitsInteractor.loadHabitList()
                .subscribe(value -> habitListLiveData.postValue(value), throwable -> {
                    if (throwable instanceof LoadDbException) {
                        errorSingleLiveEvent.postValue((((LoadDbException) throwable).getMessageRes()));
                    }
                });
        compositeDisposable.add(disposable);
    }


    @NonNull
    public MutableLiveData<List<Habit>> getHabitListLiveData(){
        return habitListLiveData;
    }

    public SingleLiveEvent<Integer> getErrorSingleLiveEvent() {
        return errorSingleLiveEvent;
    }
}
