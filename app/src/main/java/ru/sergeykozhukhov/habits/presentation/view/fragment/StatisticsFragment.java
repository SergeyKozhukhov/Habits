package ru.sergeykozhukhov.habits.presentation.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
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

        statisticsViewModel.getLoadValueFormatterSuccessMutableLiveData().observe(getViewLifecycleOwner(), new Observer<ValueFormatter>() {
            @Override
            public void onChanged(ValueFormatter valueFormatter) {

                progressHorizontalBarChart.getDescription().setEnabled(false);
                progressHorizontalBarChart.getLegend().setEnabled(false);
                progressHorizontalBarChart.setPinchZoom(false);
                progressHorizontalBarChart.setAutoScaleMinMaxEnabled(true);
                progressHorizontalBarChart.setDrawValueAboveBar(false);

                /*progressHorizontalBarChart.setVisibleXRangeMinimum(25f);
                progressHorizontalBarChart.setVisibleXRangeMaximum(50f);*/

                //Display the axis on the left (contains the labels 1*, 2* and so on)
                XAxis xAxis = progressHorizontalBarChart.getXAxis();
                //xAxis.setDrawGridLines(false);
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setEnabled(true);
                xAxis.setDrawAxisLine(true);
                xAxis.setDrawLabels(true);

                YAxis yLeft = progressHorizontalBarChart.getAxisLeft();

                //Set the minimum and maximum bar lengths as per the values that they represent
                yLeft.setAxisMaximum(100f);
                yLeft.setAxisMinimum(0f);
                yLeft.setEnabled(false);
                yLeft.setDrawLabels(true);
                yLeft.setDrawGridLinesBehindData(true);

                //Now add the labels to be added on the vertical axis
                xAxis.setValueFormatter(valueFormatter);


                YAxis yRight = progressHorizontalBarChart.getAxisRight();
                //yRight.setDrawAxisLine(true);
                yRight.setDrawGridLines(false);
                yRight.setEnabled(false);

                Legend legend = progressHorizontalBarChart.getLegend();

                //Add animation to the graph
                progressHorizontalBarChart.animateY(2000);

                legend.setTextSize(20f);
                legend.setTextColor(Color.BLACK);
                legend.setForm(Legend.LegendForm.CIRCLE);
            }
        });

        statisticsViewModel.getLoadBarDataSuccessMutableLiveData().observe(getViewLifecycleOwner(), new Observer<BarData>() {
            @Override
            public void onChanged(BarData barData) {

                XAxis xAxis = progressHorizontalBarChart.getXAxis();
                xAxis.setLabelCount(barData.getEntryCount());

                //progressHorizontalBarChart.setDrawBarShadow(true);
                progressHorizontalBarChart.setData(barData);
                //progressHorizontalBarChart.setExtraLeftOffset(20f);
                //progressHorizontalBarChart.setExtraBottomOffset(30.0f);
                progressHorizontalBarChart.invalidate();
            }
        });

        statisticsViewModel.getErrorSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer idRes) {
                Toast.makeText(requireContext(), getString(idRes), Toast.LENGTH_SHORT).show();
            }
        });

        statisticsViewModel.loadGraphData();
    }
}
