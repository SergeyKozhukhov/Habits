package ru.sergeykozhukhov.habits.base.presentation;

import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habits.base.domain.SingleLiveEvent;
import ru.sergeykozhukhov.habits.base.domain.usecase.LoadStatisticListInteractor;
import ru.sergeykozhukhov.habits.base.model.domain.Statistic;

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
                .subscribe(new Consumer<List<Statistic>>() {
                    @Override
                    public void accept(List<Statistic> statisticList) throws Exception {
                        loadSuccessSingleLiveEvent.postValue(statisticList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //errorSingleLiveEvent.postValue(;
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
