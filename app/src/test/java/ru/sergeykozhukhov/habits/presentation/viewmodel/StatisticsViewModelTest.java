package ru.sergeykozhukhov.habits.presentation.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Single;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.RxImmediateSchedulerRule;
import ru.sergeykozhukhov.habits.domain.usecaseimpl.LoadStatisticListInteractor;
import ru.sergeykozhukhov.habits.model.domain.Statistic;
import ru.sergeykozhukhov.habits.model.domain.exception.LoadDbException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsViewModelTest {

    @Rule
    public final RxImmediateSchedulerRule rxImmediateSchedulerRule = new RxImmediateSchedulerRule();
    @Rule
    public final InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    private StatisticsViewModel statisticsViewModel;

    @Mock
    private LoadStatisticListInteractor loadStatisticListInteractor;

    @Before
    public void setUp() {
        statisticsViewModel = new StatisticsViewModel(loadStatisticListInteractor);
    }

    /**
     * Провверка получения данных для отображения графика
     * Классы используемой библиотеки не поддерживают сравнение объектов, поэтому сравнение производится вручную
     * В данном случае проверка осуществляется на основные параметры (положение на оси, цвет, набор меток...)
     */
    @Test
    public void loadGraphDataSuccess() {

        /*
         * Прогресс по привычке
         * */
        Statistic statistic1 = new Statistic(1, "title1", 21, 0);
        Statistic statistic2 = new Statistic(2, "title2", 22, 22);
        Statistic statistic3 = new Statistic(3, "title3", 30, 15);
        List<Statistic> statisticListInput = Arrays.asList(statistic1, statistic2, statistic3);

        /*
         * Числовые данные для построения графика
         * */
        BarEntry barEntry1 = new BarEntry(0f, 0f);
        BarEntry barEntry2 = new BarEntry(1f, 100f);
        BarEntry barEntry3 = new BarEntry(2f, 50f);

        List<BarEntry> barEntryListExpected = Arrays.asList(barEntry1, barEntry2, barEntry3);
        BarDataSet barDataSetExpected = new BarDataSet(barEntryListExpected, null);

        /*
         * Цветовые данные для построения графика
         * */
        int COLOR_YELLOW = 0xFFFDF41C;
        int COLOR_GOLD = 0xFFFFD700;
        int COLOR_ORANGE = 0xFFFFA500;
        List<Integer> colorListExpected = Arrays.asList(COLOR_ORANGE, COLOR_YELLOW, COLOR_GOLD);
        barDataSetExpected.setColors(colorListExpected);

        when(loadStatisticListInteractor.loadStatisticsList()).thenReturn(Single.just(statisticListInput));

        statisticsViewModel.loadGraphData();

        /*
         * Проверка, что числовые и цветовые данные для графика сгенерированы правильно
         * */
        BarData barDataOutput = statisticsViewModel.getBarDataMutableLiveData().getValue();
        List<IBarDataSet> barDataSetsOutput;
        if (barDataOutput != null) {

            // Проверка, что строится только один график
            barDataSetsOutput = barDataOutput.getDataSets();
            assertThat(barDataSetsOutput.size(), is(1));

            BarDataSet barDataSetOutput = (BarDataSet) barDataSetsOutput.get(0);
            List<BarEntry> barEntryListOutput = barDataSetOutput.getValues();

            // Проверка, что кол-во столбцов диаграммы совпадает с входным
            assertThat(barEntryListOutput.size(), is(barEntryListExpected.size()));

            // Провекра, что значения столбцов диаграммы совпадают с входными
            for (int i = 0; i < barEntryListOutput.size(); i++) {
                assertThat(barEntryListOutput.get(i).getX(), is(barEntryListExpected.get(i).getX()));
                assertThat(barEntryListOutput.get(i).getY(), is(barEntryListExpected.get(i).getY()));
            }

            // Проверка, что сгенерированный набор цветов соответствует ожидаемому набору
            List<Integer> colorListOutput = barDataSetOutput.getColors();
            assertThat(colorListExpected, is(colorListOutput));
        } else
            fail();

        /*
         * Проверка, что метки генерируются правильно
         * */
        ValueFormatter valueFormatterOutput = statisticsViewModel.getValueFormatterMutableLiveData().getValue();
        AxisBase axisBase = mock(AxisBase.class);
        for (int i = 0; i < statisticListInput.size(); i++) {
            if (valueFormatterOutput != null) {
                assertThat(valueFormatterOutput.getAxisLabel((float) i, axisBase), is(statisticListInput.get(i).getTitle()));
            } else fail();
        }

        /*
         * Проверка, что получаем соответствующее кол-во меток на оси
         * */
        assertThat(statisticsViewModel.getLabelCountMutableLiveData().getValue(), is(barDataSetExpected.getEntryCount()));

        verify(loadStatisticListInteractor).loadStatisticsList();
    }

    @Test
    public void loadGraphDataError() {

        LoadDbException loadDbException = new LoadDbException(R.string.load_db_exception, new Exception());

        when(loadStatisticListInteractor.loadStatisticsList()).thenReturn(Single.error(loadDbException));

        statisticsViewModel.loadGraphData();

        assertThat(statisticsViewModel.getErrorSingleLiveEvent().getValue(), is(loadDbException.getMessageRes()));
        verify(loadStatisticListInteractor).loadStatisticsList();
    }
}