package ru.sergeykozhukhov.habits.presentation.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.model.GradientColor;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habits.domain.SingleLiveEvent;
import ru.sergeykozhukhov.habits.domain.usecaseimpl.LoadStatisticListInteractor;
import ru.sergeykozhukhov.habits.model.domain.Statistic;
import ru.sergeykozhukhov.habits.model.domain.exception.LoadDbException;

/**
 * ViewModel для отображения общего графика выполнения привычек
 */
public class StatisticsViewModel extends ViewModel {

    /*private static final int COLOR_YELLOW = 0xFFfde910;
    private static final int COLOR_YANDEX = 0xFFffba00;
    //private static final int COLOR_YELLOW = 0xFFE7E700;
    private static final int COLOR_GOLD = 0xFFFFD700;
    private static final int COLOR_ORANGE = 0xFFFFA500;*/

    private static final int COLOR_YELLOW = 0xFFFDF41C;
    private static final int COLOR_GOLD = 0xFFFFD700;
    private static final int COLOR_ORANGE = 0xFFFFA500;



    GradientColor gradientColor = new GradientColor(COLOR_ORANGE, COLOR_GOLD);

    /**
     * Интерактора получения списка минимальной информации о привычках с соответствующим количеством выполненных дней
     */
    private final LoadStatisticListInteractor loadStatisticsListInteractor;

    private final MutableLiveData<BarData> loadBarDataSuccessMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<ValueFormatter> loadValueFormatterSuccessMutableLiveData = new MutableLiveData<>();

    /**
     * LiveData с идентификаторами строковых ресурсов сообщений об ошибках
     */
    private final SingleLiveEvent<Integer> errorSingleLiveEvent = new SingleLiveEvent<>();

    private CompositeDisposable compositeDisposable;

    public StatisticsViewModel(LoadStatisticListInteractor loadStatisticsListInteractor) {
        this.loadStatisticsListInteractor = loadStatisticsListInteractor;
        compositeDisposable = new CompositeDisposable();
    }

    public void loadGraphData() {
        compositeDisposable.add(loadStatisticsListInteractor.loadStatisticsList()
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<List<Statistic>>() {
                    @Override
                    public void accept(List<Statistic> statisticList) {

                        List<BarEntry> entries = new ArrayList<>();
                        List<GradientColor> gradientColorList = new ArrayList<>(statisticList.size());
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
                            gradientColorList.add(gradientColor);
                            strings[(int) indexX] = String.valueOf(statistic.getTitle());
                            stringList.add(String.valueOf(statistic.getIdHabit()));
                            indexX = indexX + 1.0f;
                        }

                        BarDataSet barDataSet = new BarDataSet(entries, "Bar Data Set");
                        //barDataSet.setStackLabels(strings);
                        barDataSet.setColors(listColor);
                        barDataSet.setValueTextSize(15f);

                        //barDataSet.setBarShadowColor(Color.argb(40, 150, 150, 150));
                        BarData data = new BarData(barDataSet);

                        //Set the bar width
                        //Note : To increase the spacing between the bars set the value of barWidth to < 1f
                        //data.setBarWidth(0.9f);

                        ValueFormatter valueFormatter = new ValueFormatter() {
                            @Override
                            public String getAxisLabel(float value, AxisBase axis) {
                                return String.valueOf(statisticList.get((int) value).getIdHabit());
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

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
    }
}
