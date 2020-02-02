package ru.sergeykozhukhov.habits.base.data.repository;

import android.content.Context;

import androidx.annotation.NonNull;

import org.reactivestreams.Publisher;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habits.base.data.HabitConverter;
import ru.sergeykozhukhov.habits.base.data.database.HabitDao;
import ru.sergeykozhukhov.habits.base.data.database.HabitsDatabase;
import ru.sergeykozhukhov.habits.base.data.model.HabitData;
import ru.sergeykozhukhov.habits.base.domain.IHabitsRepository;
import ru.sergeykozhukhov.habits.base.domain.model.Habit;

public class HabitsRepository implements IHabitsRepository {

    private Context context;

    private HabitsDatabase habitsDatabase;
    private HabitDao habitDao;

    private HabitConverter habitConverter;

    private ExecutorService executorService;

    public HabitsRepository(@NonNull Context context, @NonNull ExecutorService executorService, @NonNull HabitConverter habitConverter) {

        this.context = context;
        this.habitConverter = habitConverter;

        habitsDatabase = HabitsDatabase.getInstance(context);
        habitDao = habitsDatabase.getHabitDao();

        this.executorService = executorService;
    }


    @Override
    public List<Habit> loadAll() {

        Future<List<HabitData>> future = executorService.submit(new Callable<List<HabitData>>() {

            @Override
            public List<HabitData> call() throws Exception {
                return habitDao.getAll();
            }
        });

        List<Habit> habitList = null;
        try {
            habitList = habitConverter.convert(future.get());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        executorService.shutdown();

        return habitList;
    }

    @Override
    public Flowable<List<Habit>> loadHabits() {

        return habitDao.getHabits()
                .map(new Function<List<HabitData>, List<Habit>>() {
                    @Override
                    public List<Habit> apply(List<HabitData> habitDataList) throws Exception {
                        return habitConverter.convert(habitDataList);
                    }
                });
    }

}
