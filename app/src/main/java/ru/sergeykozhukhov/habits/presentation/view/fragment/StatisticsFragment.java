package ru.sergeykozhukhov.habits.presentation.view.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.formatter.ValueFormatter;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.presentation.viewmodel.StatisticsViewModel;
import ru.sergeykozhukhov.habits.presentation.factory.ViewModelFactory;

/**
 * Fragment для отображения общего графика выполнения привычек
 */
public class StatisticsFragment extends Fragment {

    private static final float Y_AXIS_MIN = 0f;
    private static final float Y_AXIS_MAX = 100f;
    private static final int Y_ANIMATION_DURATION = 1500;

    private static final float X_AXIS_TEXT_SIZE = 15f;
    private static final float X_VISIBLE_RANGE_MIN = 15f;
    private static final float X_VISIBLE_RANGE_MAX = 15f;

    private static final float EXTRA_RIGHT_OFFSET = 45f;

    private StatisticsViewModel statisticsViewModel;

    private HorizontalBarChart progressHorizontalBarChart;


    public static Fragment newInstance() {
        return new StatisticsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.statistics_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressHorizontalBarChart = view.findViewById(R.id.progress_horizontal_bar_chart);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupMvvm();
    }

    private void setupMvvm() {

        statisticsViewModel = new ViewModelProvider(this, new ViewModelFactory(requireContext())).get(StatisticsViewModel.class);

        statisticsViewModel.getValueFormatterMutableLiveData().observe(getViewLifecycleOwner(), this::initBarChartView);

        statisticsViewModel.getLabelCountMutableLiveData().observe(getViewLifecycleOwner(), this::setBarChartLabelCount);

        statisticsViewModel.getBarDataMutableLiveData().observe(getViewLifecycleOwner(), this::setBarChartData);

        statisticsViewModel.getErrorSingleLiveEvent().observe(getViewLifecycleOwner(),
                idRes -> Toast.makeText(requireContext(), getString(idRes), Toast.LENGTH_SHORT).show());

        setNoDataChartView();
        statisticsViewModel.loadGraphData();
    }

    private void initBarChartView(@NonNull ValueFormatter valueFormatter) {
        progressHorizontalBarChart.getDescription().setEnabled(false);
        progressHorizontalBarChart.getLegend().setEnabled(false);
        progressHorizontalBarChart.setPinchZoom(false);
        progressHorizontalBarChart.setDrawValueAboveBar(true);
        progressHorizontalBarChart.setAutoScaleMinMaxEnabled(true);
        progressHorizontalBarChart.setVisibleXRangeMinimum(X_VISIBLE_RANGE_MIN);
        progressHorizontalBarChart.setVisibleXRangeMaximum(X_VISIBLE_RANGE_MAX);
        progressHorizontalBarChart.setExtraRightOffset(EXTRA_RIGHT_OFFSET);


        XAxis xAxis = progressHorizontalBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setEnabled(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawLabels(true);
        xAxis.setValueFormatter(valueFormatter);
        xAxis.setTextSize(X_AXIS_TEXT_SIZE);

        YAxis yLeft = progressHorizontalBarChart.getAxisLeft();
        yLeft.setAxisMaximum(Y_AXIS_MAX);
        yLeft.setAxisMinimum(Y_AXIS_MIN);
        yLeft.setEnabled(false);
        yLeft.setDrawLabels(true);

        progressHorizontalBarChart.animateY(Y_ANIMATION_DURATION);
    }

    private void setNoDataChartView() {
        progressHorizontalBarChart.setNoDataTextColor(R.color.green);
        progressHorizontalBarChart.setNoDataText("Слишком мало данных для построения графика");
    }

    private void setBarChartLabelCount(int labelCount) {
        XAxis xAxis = progressHorizontalBarChart.getXAxis();
        xAxis.setLabelCount(labelCount);
    }

    private void setBarChartData(BarData barData) {
        progressHorizontalBarChart.setData(barData);
        progressHorizontalBarChart.invalidate();
    }
}
