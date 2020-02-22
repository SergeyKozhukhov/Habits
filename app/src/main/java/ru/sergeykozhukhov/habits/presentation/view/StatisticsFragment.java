package ru.sergeykozhukhov.habits.presentation.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.List;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.model.domain.Statistic;
import ru.sergeykozhukhov.habits.presentation.viewmodel.StatisticsViewModel;
import ru.sergeykozhukhov.habits.presentation.factory.ViewModelFactory;

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

























    /*private void setSkillGraph() {
        //skill_rating_chart is the id of the XML layout

        progressHorizontalBarChart.setDrawBarShadow(false);
        //*String description = Description()
        *//*description.text = ""
        skillRatingChart.description = description*//*
        progressHorizontalBarChart.getLegend().setEnabled(false);
        progressHorizontalBarChart.setPinchZoom(false);
        progressHorizontalBarChart.setDrawValueAboveBar(false);

        //Display the axis on the left (contains the labels 1*, 2* and so on)
        XAxis xAxis = progressHorizontalBarChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setEnabled(true);
        xAxis.setDrawAxisLine(false);


        YAxis yLeft = progressHorizontalBarChart.getAxisLeft();

//Set the minimum and maximum bar lengths as per the values that they represent
        yLeft.setAxisMaximum(100f);
        yLeft.setAxisMinimum(0f);
        yLeft.setEnabled(false);

        //Set label count to 5 as we are displaying 5 star rating
        xAxis.setLabelCount(5);

//Now add the labels to be added on the vertical axis


        ValueFormatter valueFormatter = new ValueFormatter() {
            String[] values = {"1 *", "2 *", "3 *", "4 *", "5 *"};

            @Override
            public String getFormattedValue(float value) {
                return this.values[(int) value];
            }
        };

        xAxis.setValueFormatter(valueFormatter);

        YAxis yRight = progressHorizontalBarChart.getAxisRight();
        yRight.setDrawAxisLine(true);
        yRight.setDrawGridLines(false);
        yRight.setEnabled(false);

        //Set bar entries and add necessary formatting
        setGraphData();

        //Add animation to the graph
        progressHorizontalBarChart.animateY(2000);
    }*/



   /*private void setData(List<Statistic> statisticList, float range) {

        int count = statisticList.size();

        float barWidth = 80f;
        float spaceForBar = 150f;

       // Create the labels for the bars
       final ArrayList<String> xVals = new ArrayList<>();
       ArrayList<String> labels = new ArrayList<String>();

        ArrayList<BarEntry> values = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float val = ((float) statisticList.get(i).getCurrentQuantity()/(float) statisticList.get(i).getDuration()*range);
            values.add(new BarEntry(i * spaceForBar, val, statisticList.get(i).getTitle()));
            xVals.add(statisticList.get(i).getTitle());
            labels.add(statisticList.get(i).getTitle());
        }



        BarDataSet set1;


        if (progressHorizontalBarChart.getData() != null &&
                progressHorizontalBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) progressHorizontalBarChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            progressHorizontalBarChart.getData().notifyDataChanged();
            progressHorizontalBarChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "Progress %");

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(barWidth);
            set1.setColors(ColorTemplate.JOYFUL_COLORS);


            progressHorizontalBarChart.setDrawValueAboveBar(true);
            progressHorizontalBarChart.setPinchZoom(false);
            progressHorizontalBarChart.setDrawGridBackground(false);
            progressHorizontalBarChart.animateY(2500);
            progressHorizontalBarChart.setData(data);
            progressHorizontalBarChart.getAxisRight().setValueFormatter(new IndexAxisValueFormatter(xVals));
            progressHorizontalBarChart.invalidate();
        }
    }*/



/*private void setGraphData() {

        //Add a list of bar entries
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 27f));
        entries.add(new BarEntry(1f, 45f));
        entries.add(new BarEntry(2f, 65f));
        entries.add(new BarEntry(3f, 77f));
        entries.add(new BarEntry(4f, 93f));

        //Note : These entries can be replaced by real-time data, say, from an API

        BarDataSet barDataSet = new BarDataSet(entries, "Bar Data Set");

        barDataSet.setColors(
                ContextCompat.getColor(progressHorizontalBarChart.getContext(), R.color.colorAccent),
                ContextCompat.getColor(progressHorizontalBarChart.getContext(), R.color.calendar_bg),
                ContextCompat.getColor(progressHorizontalBarChart.getContext(), R.color.colorAccent),
                ContextCompat.getColor(progressHorizontalBarChart.getContext(), R.color.calendar_inactive_month_bg),
                ContextCompat.getColor(progressHorizontalBarChart.getContext(), R.color.colorAccent));



        progressHorizontalBarChart.setDrawBarShadow(true);
        barDataSet.setBarShadowColor(Color.argb(40, 150, 150, 150));
        BarData data = new BarData(barDataSet);

        //Set the bar width
        //Note : To increase the spacing between the bars set the value of barWidth to < 1f
        data.setBarWidth(0.9f);

        //Finally set the data and refresh the graph
        progressHorizontalBarChart.setData(data);
        progressHorizontalBarChart.invalidate();

    }*/
