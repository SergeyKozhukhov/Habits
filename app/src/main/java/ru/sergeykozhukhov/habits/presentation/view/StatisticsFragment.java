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
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.model.domain.Statistic;
import ru.sergeykozhukhov.habits.presentation.viewmodel.HabitsListViewModel;
import ru.sergeykozhukhov.habits.presentation.viewmodel.StatisticsViewModel;
import ru.sergeykozhukhov.habits.presentation.factory.ViewModelFactory;

public class StatisticsFragment extends Fragment {

    private StatisticsViewModel statisticsViewModel;

    private HorizontalBarChart progressHorizontalBarChart;
    private TextView statisticsTextView;

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

        initData();
        // setSkillGraph();
        setupMvvm();
        initListeners();


    }


    private void initListeners() {

    }

    private void initData() {

    }

    private void setupMvvm() {

        statisticsViewModel = new ViewModelProvider(this, new ViewModelFactory(requireContext())).get(StatisticsViewModel.class);

        statisticsViewModel.getLoadSuccessSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<List<Statistic>>() {
            @Override
            public void onChanged(List<Statistic> statisticList) {
                // setData(statisticList, 100.0f);

                setSkillGraph(statisticList);

                //setSkillGraph();

                Toast.makeText(requireContext(), String.valueOf(statisticList.size()), Toast.LENGTH_SHORT).show();
            }
        });

        statisticsViewModel.getErrorSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer idRes) {
                Toast.makeText(requireContext(), getString(idRes), Toast.LENGTH_SHORT).show();
            }
        });


        statisticsViewModel.loadStatisticsList();
    }


    private void setSkillGraph(List<Statistic> statisticList) {
        //skill_rating_chart is the id of the XML layout

        /*String description = Description()
        description.text = ""
        skillRatingChart.description = description*
         */
        /*Description description = new Description();
        description.setText("Процент выполнения");
        description.setPosition(-10.0f, 0);
        progressHorizontalBarChart.setDescription(description);*/
        progressHorizontalBarChart.getDescription().setEnabled(false);
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
        yLeft.setDrawLabels(true);

        //Set label count to 5 as we are displaying 5 star rating
        xAxis.setLabelCount(statisticList.size());

//Now add the labels to be added on the vertical axis


        ValueFormatter valueFormatter = new ValueFormatter() {

            @Override
            public String getFormattedValue(float value) {
                return String.valueOf(statisticList.get((int) value).getIdHabit());
            }
        };

        xAxis.setValueFormatter(valueFormatter);

        YAxis yRight = progressHorizontalBarChart.getAxisRight();
        yRight.setDrawAxisLine(true);
        yRight.setDrawGridLines(false);
        yRight.setEnabled(false);

        //Set bar entries and add necessary formatting
        setGraphData(statisticList);

        Legend legend = progressHorizontalBarChart.getLegend();

        //Add animation to the graph
        progressHorizontalBarChart.animateY(2000);

        legend.setTextSize(20f);
        legend.setTextColor(Color.BLACK);
        legend.setForm(Legend.LegendForm.CIRCLE);
    }


    private void setGraphData(List<Statistic> statisticList) {

        //Add a list of bar entries
        List<BarEntry> entries = new ArrayList<>();

        List<Integer> listColor = new ArrayList<>(statisticList.size());

        float indexX = 0f;
        for (Statistic statistic : statisticList) {
            float percent = 100f * (float) statistic.getCurrentQuantity() / (float) (statistic.getDuration());
            entries.add(new BarEntry(indexX, percent));
            int color = 0;
            if (percent < 33.3f) {
                color = R.color.orange;
            } else if (percent > 66.6f) {
                color = R.color.green;
            } else {
                color = R.color.yellow;
            }
            listColor.add(requireContext().getResources().getColor(color));
            indexX = indexX + 1.0f;
        }

        //Note : These entries can be replaced by real-time data, say, from an API

        BarDataSet barDataSet = new BarDataSet(entries, "Bar Data Set");

        //barDataSet.setDrawValues(true);
      /*  barDataSet.setValueTextSize(20.0f);
        barDataSet.setBarBorderWidth(0.1f);*/

        barDataSet.setColors(listColor);


        /*barDataSet.setColors(
                ContextCompat.getColor(progressHorizontalBarChart.getContext(), R.color.colorAccent),
                ContextCompat.getColor(progressHorizontalBarChart.getContext(), R.color.calendar_bg),
                ContextCompat.getColor(progressHorizontalBarChart.getContext(), R.color.colorAccent),
                ContextCompat.getColor(progressHorizontalBarChart.getContext(), R.color.calendar_inactive_month_bg),
                ContextCompat.getColor(progressHorizontalBarChart.getContext(), R.color.colorAccent));*/


        progressHorizontalBarChart.setDrawBarShadow(true);
        barDataSet.setBarShadowColor(Color.argb(40, 150, 150, 150));
        BarData data = new BarData(barDataSet);

        //Set the bar width
        //Note : To increase the spacing between the bars set the value of barWidth to < 1f
        data.setBarWidth(0.9f);

        //Finally set the data and refresh the graph
        progressHorizontalBarChart.setData(data);
        progressHorizontalBarChart.setExtraBottomOffset(30.0f);
        progressHorizontalBarChart.invalidate();

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
}


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
