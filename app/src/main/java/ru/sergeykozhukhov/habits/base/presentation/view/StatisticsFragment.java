package ru.sergeykozhukhov.habits.base.presentation.view;

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
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.base.model.domain.Statistic;
import ru.sergeykozhukhov.habits.base.presentation.StatisticsViewModel;
import ru.sergeykozhukhov.habits.base.presentation.factory.ViewModelFactory;

public class StatisticsFragment extends Fragment{

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
        statisticsTextView = view.findViewById(R.id.statistics_text_view);
        progressHorizontalBarChart = view.findViewById(R.id.progress_horizontal_bar_chart);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
        setupMvvm();
        initListeners();




    }


    private void initListeners(){

    }

    private void initData(){

    }

    private void setupMvvm(){
        statisticsViewModel = ViewModelProviders.of(this, new ViewModelFactory(requireContext()))
                .get(StatisticsViewModel.class);

        statisticsViewModel.getLoadSuccessSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<List<Statistic>>() {
            @Override
            public void onChanged(List<Statistic> statisticList) {
                setData(statisticList, 100.0f);

                progressHorizontalBarChart.getData().notifyDataChanged();
                progressHorizontalBarChart.notifyDataSetChanged();
                StringBuilder stringBuilder = new StringBuilder();
                for (Statistic statistic : statisticList){
                    stringBuilder.append(statistic.toString());
                    stringBuilder.append("\n");
                }
                statisticsTextView.setText(stringBuilder.toString());
                Toast.makeText(requireContext(), String.valueOf(statisticList.size()), Toast.LENGTH_SHORT).show();
            }
        });

        statisticsViewModel.loadStatisticsList();
    }


   private void setData(List<Statistic> statisticList, float range) {

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
    }
}
