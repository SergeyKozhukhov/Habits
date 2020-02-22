package ru.sergeykozhukhov.habits.presentation.viewmodel;

import android.graphics.Color;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
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

    private static final int COLOR_YELLOW = 0xFFFFFF00;
    private static final int COLOR_GOLD = 0xFFFFD700;
    private static final int COLOR_ORANGE = 0xFFFFA500;

    private final LoadStatisticListInteractor loadStatisticsListInteractor;

    private final MutableLiveData<BarData> loadBarDataSuccessMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<ValueFormatter> loadValueFormatterSuccessMutableLiveData = new MutableLiveData<>();
    private final SingleLiveEvent<Integer> errorSingleLiveEvent = new SingleLiveEvent<>();


    private CompositeDisposable compositeDisposable;


    public StatisticsViewModel(LoadStatisticListInteractor loadStatisticsListInteractor) {
        this.loadStatisticsListInteractor = loadStatisticsListInteractor;
        initData();
    }

    private void initData() {
        compositeDisposable = new CompositeDisposable();
    }

    public void loadGraphData() {
        compositeDisposable.add(loadStatisticsListInteractor.loadStatisticsList()
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<List<Statistic>>() {
                    @Override
                    public void accept(List<Statistic> statisticList) throws Exception {

                        List<BarEntry> entries = new ArrayList<>();
                        List<Integer> listColor = new ArrayList<>(statisticList.size());
                        String[] strings = new String[statisticList.size()];
                        List<String> stringList = new ArrayList<>(statisticList.size());
                        float indexX = 0f;
                        for (Statistic statistic : statisticList) {
                            float percent = 100f * (float) statistic.getCurrentQuantity() / (float) (statistic.getDuration());
                            entries.add(new BarEntry(indexX, percent));
                            int color;
                            if (percent < 33.3f) {
                                color = COLOR_ORANGE;
                            } else if (percent > 66.6f) {
                                color = COLOR_YELLOW;
                            } else {
                                color = COLOR_GOLD;
                            }
                            listColor.add(color);
                            strings[(int)indexX] = String.valueOf(statistic.getTitle());
                            stringList.add(String.valueOf(statistic.getIdHabit()));
                            indexX = indexX + 1.0f;
                        }

                        BarDataSet barDataSet = new BarDataSet(entries, "Bar Data Set");
                        //barDataSet.setStackLabels(strings);
                        barDataSet.setColors(listColor);
                        barDataSet.setValueTextSize(9f);

                        //barDataSet.setBarShadowColor(Color.argb(40, 150, 150, 150));
                        BarData data = new BarData(barDataSet);

                        //Set the bar width
                        //Note : To increase the spacing between the bars set the value of barWidth to < 1f
                        //data.setBarWidth(0.9f);

                        ValueFormatter valueFormatter = new ValueFormatter() {
                            @Override
                            public String getAxisLabel(float value, AxisBase axis) {
                                return String.valueOf(statisticList.get((int) value).getTitle());
                            }

                            /*@Override
                            public String getBarLabel(BarEntry barEntry) {
                                return super.getBarLabel(barEntry);
                            }*/
                        };

                       loadBarDataSuccessMutableLiveData.postValue(data);
                       loadValueFormatterSuccessMutableLiveData.postValue(valueFormatter);
                    }
                }, throwable -> {
                    if (throwable instanceof LoadDbException) {
                        errorSingleLiveEvent.postValue((((LoadDbException) throwable).getMessageRes()));
                    }
                }));
    }


    public MutableLiveData<BarData> getLoadBarDataSuccessMutableLiveData() {
        return loadBarDataSuccessMutableLiveData;
    }

    public MutableLiveData<ValueFormatter> getLoadValueFormatterSuccessMutableLiveData() {
        return loadValueFormatterSuccessMutableLiveData;
    }

    public SingleLiveEvent<Integer> getErrorSingleLiveEvent() {
        return errorSingleLiveEvent;
    }
}
