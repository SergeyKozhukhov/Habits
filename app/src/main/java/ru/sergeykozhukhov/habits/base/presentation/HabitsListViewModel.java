package ru.sergeykozhukhov.habits.base.presentation;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habits.base.domain.usecase.DeleteAllHabitsDbInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.LoadHabitsDbInteractor;
import ru.sergeykozhukhov.habits.base.model.domain.Habit;

public class HabitsListViewModel extends ViewModel {

    private static final String TAG = "HabitsListViewModel";

    private final LoadHabitsDbInteractor loadHabitsInteractor;
    private final DeleteAllHabitsDbInteractor deleteAllHabitsInteractor;

    private Disposable disposableLoadHabits;

    private MutableLiveData<List<Habit>> habitListLiveData = new MutableLiveData<>();

    public HabitsListViewModel(
            @NonNull LoadHabitsDbInteractor loadHabitsInteractor,
            @NonNull DeleteAllHabitsDbInteractor deleteAllHabitsInteractor) {
        this.loadHabitsInteractor = loadHabitsInteractor;
        this.deleteAllHabitsInteractor = deleteAllHabitsInteractor;
    }

    public void loadHabits(){

        disposableLoadHabits = loadHabitsInteractor.loadHabits()
                .subscribe(new Consumer<List<Habit>>() {
                    @Override
                    public void accept(List<Habit> habitList) throws Exception {
                        habitListLiveData.postValue(habitList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    public void deleteAllHabits(){
        deleteAllHabitsInteractor.deleteAllHabits()
                .subscribeOn(Schedulers.newThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }


    @NonNull
    public MutableLiveData<List<Habit>> getHabitListLiveData(){
        return habitListLiveData;
    }
}
