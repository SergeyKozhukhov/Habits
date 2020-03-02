package ru.sergeykozhukhov.habits.presentation.viewmodel;

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
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habits.domain.SingleLiveEvent;
import ru.sergeykozhukhov.habits.domain.usecaseimpl.LoadStatisticListInteractor;
import ru.sergeykozhukhov.habits.model.domain.Statistic;
import ru.sergeykozhukhov.habits.model.domain.exception.LoadDbException;

/**
 * ViewModel для отображения общего графика выполнения привычек
 */
public class StatisticsViewModel extends ViewModel {

    /**
     * Желтый цвет
     */
    private static final int COLOR_YELLOW = 0xFFFDF41C;

    /**
     * Цвет между желтым и оранжевым
     */
    private static final int COLOR_GOLD = 0xFFFFD700;

    /**
     * Оранжевый цвет
     */
    private static final int COLOR_ORANGE = 0xFFFFA500;

    /**
     * Размер подписей
     */
    private static final float VALUE_TEXT_SIZE = 15f;

    /**
     * Максимальное число подписей на на оси в графике
     */
    private static final int LABEL_COUNT_MAX = 15;

    /**
     * Конец первого интервала для цвета
     */
    private static final float END_LEFT_GAP = 33.3f;

    /**
     * Начало третьего интервала для цвета
     */
    private static final float START_RIGHT_GAP = 66.6f;

    /**
     * Интерактора получения списка минимальной информации о привычках с соответствующим количеством выполненных дней
     */
    private final LoadStatisticListInteractor loadStatisticsListInteractor;

    /**
     * LiveData с данными для построения графика
     */
    private final MutableLiveData<BarData> barDataMutableLiveData = new MutableLiveData<>();

    /**
     * LiveData с определителем подписи для горизонтального столбца
     */
    private final MutableLiveData<ValueFormatter> valueFormatterMutableLiveData = new MutableLiveData<>();

    /**
     * LiveData с кол-во подписей на оси
     */
    private final MutableLiveData<Integer> labelCountMutableLiveData = new MutableLiveData<>();

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
                .subscribe(statisticList -> {

                    List<BarEntry> entries = new ArrayList<>();
                    List<Integer> listColor = new ArrayList<>(statisticList.size());

                    float indexX = 0f;
                    for (Statistic statistic : statisticList) {
                        float percent = 100f * (float) statistic.getCurrentQuantity() / (float) (statistic.getDuration());
                        int color;
                        if (percent < END_LEFT_GAP) {
                            color = COLOR_ORANGE;
                        } else if (percent > START_RIGHT_GAP) {
                            color = COLOR_YELLOW;
                        } else {
                            color = COLOR_GOLD;
                        }
                        entries.add(new BarEntry(indexX, percent));
                        listColor.add(color);
                        indexX = indexX + 1.0f;
                    }

                    BarDataSet barDataSet = new BarDataSet(entries, null);
                    barDataSet.setColors(listColor);
                    barDataSet.setValueTextSize(VALUE_TEXT_SIZE);

                    BarData barData = new BarData(barDataSet);

                    ValueFormatter valueFormatter = new ValueFormatter() {
                        @Override
                        public String getAxisLabel(float value, AxisBase axis) {
                            return String.valueOf(statisticList.get((int) value).getTitle());
                        }
                    };

                    if (barDataSet.getEntryCount() > 1) {
                        barDataMutableLiveData.postValue(barData);
                        labelCountMutableLiveData.postValue(barData.getEntryCount() < LABEL_COUNT_MAX ? barData.getEntryCount() : LABEL_COUNT_MAX);
                        valueFormatterMutableLiveData.postValue(valueFormatter);
                    }

                }, throwable -> {
                    if (throwable instanceof LoadDbException) {
                        errorSingleLiveEvent.postValue((((LoadDbException) throwable).getMessageRes()));
                    }
                }));
    }

    public MutableLiveData<BarData> getBarDataMutableLiveData() {
        return barDataMutableLiveData;
    }

    public MutableLiveData<ValueFormatter> getValueFormatterMutableLiveData() {
        return valueFormatterMutableLiveData;
    }

    public MutableLiveData<Integer> getLabelCountMutableLiveData() {
        return labelCountMutableLiveData;
    }

    public SingleLiveEvent<Integer> getErrorSingleLiveEvent() {
        return errorSingleLiveEvent;
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
    }
}
