package ru.sergeykozhukhov.habits.base.presentation;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import ru.sergeykozhukhov.habits.base.domain.LoadHabitsInteractor;
import ru.sergeykozhukhov.habits.base.domain.model.Habit;

public class HabitsPresenterViewModel {

    private final LoadHabitsInteractor loadHabitsInteractor;

    @NonNull
    private LoadHabitsView loadHabitsView;

    private Disposable habitsListDisposable;
    private Flowable<List<Habit>> habitsListFlowable;

    public HabitsPresenterViewModel(LoadHabitsInteractor loadHabitsInteractor) {
        this.loadHabitsInteractor = loadHabitsInteractor;
        loadHabitsView = new LoadHabitsView.Empty();
    }

    public void loadHabits() {
        habitsListDisposable = loadHabitsInteractor.loadHabits()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Habit>>() {
                    @Override
                    public void accept(List<Habit> habits) throws Exception {
                        loadHabitsView.onHabitsLoaded(habits);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        loadHabitsView.onError();
                    }
                });
    }

    public void setLoadHabitsView(@NonNull LoadHabitsView loadHabitsView){
        this.loadHabitsView = loadHabitsView;
    }
}
