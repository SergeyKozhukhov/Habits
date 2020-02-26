package ru.sergeykozhukhov.habits;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import ru.sergeykozhukhov.habits.model.data.HabitData;
import ru.sergeykozhukhov.habits.model.data.HabitWithProgressesData;
import ru.sergeykozhukhov.habits.model.data.ProgressData;
import ru.sergeykozhukhov.habits.model.data.StatisticData;
import ru.sergeykozhukhov.habits.model.domain.Habit;
import ru.sergeykozhukhov.habits.model.domain.HabitWithProgresses;
import ru.sergeykozhukhov.habits.model.domain.Progress;
import ru.sergeykozhukhov.habits.model.domain.Statistic;

public class GeneratorData {

    private static final long startIdServer = 100L;
    private static final long startMilliseconds = 10L;
    private static final int startDuration = 10;


    public Habit createHabit(long idHabit) {
        return new Habit(idHabit,
                startIdServer +idHabit,
                "title"+idHabit,
                "description"+idHabit,
                new Date(startMilliseconds+idHabit),
                startDuration+(int)idHabit);
    }

    public HabitData createHabitData(long idHabit) {
        return new HabitData(idHabit,
                startIdServer +idHabit,
                "title"+idHabit,
                "description"+idHabit,
                new Date(startMilliseconds+idHabit),
                startDuration+(int)idHabit);
    }

    public List<Habit> createHabitList(long startIdHabit, int size){
        List<Habit> habitList = new ArrayList<>(size);
        for (int i = 0; i <size; i++){
            habitList.add(createHabit(startIdHabit+i));
        }
        return habitList;
    }

    public List<HabitData> createHabitDataList(long startIdHabit, int size){
        List<HabitData> habitDataList = new ArrayList<>(size);
        for (int i = 0; i <size; i++){
            habitDataList.add(createHabitData(startIdHabit+i));
        }
        return habitDataList;
    }

    public List<Progress> createProgressList(long startIdProgress, long idHabit, int size) {

        List<Progress> progressList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            progressList.add(new Progress(
                    startIdProgress + i,
                    startIdServer + startIdProgress,
                    idHabit,
                    new Date(startMilliseconds + startIdProgress)));
        }

        return progressList;
    }

    public List<ProgressData> createProgressDataList(int startIdProgress, int idHabit, int size) {
        List<ProgressData> progressDataList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            progressDataList.add(new ProgressData(
                    startIdProgress + i,
                    startIdServer + startIdProgress,
                    idHabit,
                    new Date(startMilliseconds + startIdProgress)));
        }
        return progressDataList;
    }

    public List<HabitWithProgresses> createHabitWithProgressesList() {
        Habit habit1 = createHabit(1);
        Habit habit2 = createHabit(2);

        HabitWithProgresses habitWithProgresses1 = new HabitWithProgresses(habit1, createProgressList(1, 1, 3));
        HabitWithProgresses habitWithProgresses2 = new HabitWithProgresses(habit2, createProgressList(4, 2, 3));

        return Arrays.asList(habitWithProgresses1, habitWithProgresses2);
    }

    public List<HabitWithProgressesData> createHabitWithProgressesDataList() {
        HabitData habitData1 = createHabitData(1);
        HabitData habitData2 = createHabitData(2);

        HabitWithProgressesData habitWithProgressesData1 = new HabitWithProgressesData(habitData1, createProgressDataList(1, 1, 3));
        HabitWithProgressesData habitWithProgressesData2 = new HabitWithProgressesData(habitData2, createProgressDataList(4, 2, 3));

        return Arrays.asList(habitWithProgressesData1, habitWithProgressesData2);
    }

    public List<Statistic> createStatisticList(){
        Statistic statistic1 = new Statistic(1,"title1",21,11);
        Statistic statistic2 = new Statistic(2,"title2",22,12);
        return Arrays.asList(statistic1, statistic2);
    }

    public List<StatisticData> createStatisticDataList(){
        StatisticData statisticData1 = new StatisticData(1,"title1",21,11);
        StatisticData statisticData2 = new StatisticData(2,"title2",22,12);
        return Arrays.asList(statisticData1, statisticData2);
    }
}
