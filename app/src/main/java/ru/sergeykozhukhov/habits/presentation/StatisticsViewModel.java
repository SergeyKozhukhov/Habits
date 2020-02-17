package ru.sergeykozhukhov.habits.presentation;

import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habits.domain.SingleLiveEvent;
import ru.sergeykozhukhov.habits.domain.usecase.LoadStatisticListInteractor;
import ru.sergeykozhukhov.habits.model.domain.Statistic;
import ru.sergeykozhukhov.habits.model.exception.LoadDbException;

/**
 * ViewModel для получения статистической информации по прогрессу выполнения привычки
 */
public class StatisticsViewModel extends ViewModel {

    private static final String TAG = "StatiscticsViewModel";

    private final LoadStatisticListInteractor loadStatisticsListInteractor;

    private final SingleLiveEvent<List<Statistic>> loadSuccessSingleLiveEvent = new SingleLiveEvent<>();
    private final SingleLiveEvent<Integer> errorSingleLiveEvent = new SingleLiveEvent<>();

    private CompositeDisposable compositeDisposable;


    public StatisticsViewModel(LoadStatisticListInteractor loadStatisticsListInteractor) {
        this.loadStatisticsListInteractor = loadStatisticsListInteractor;
        initData();
    }

    private void initData() {
        compositeDisposable = new CompositeDisposable();
    }

    public void loadStatisticsList(){
        compositeDisposable.add(loadStatisticsListInteractor.loadStatisticsList()
                .subscribeOn(Schedulers.newThread())
                .subscribe(loadSuccessSingleLiveEvent::postValue, throwable -> {
                    if (throwable instanceof LoadDbException) {
                        errorSingleLiveEvent.postValue((((LoadDbException) throwable).getMessageRes()));
                    }
                }));
    }


    public SingleLiveEvent<List<Statistic>> getLoadSuccessSingleLiveEvent() {
        return loadSuccessSingleLiveEvent;
    }

    public SingleLiveEvent<Integer> getErrorSingleLiveEvent() {
        return errorSingleLiveEvent;
    }
}
