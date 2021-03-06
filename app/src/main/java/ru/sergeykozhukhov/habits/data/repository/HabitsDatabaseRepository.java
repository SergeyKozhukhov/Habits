package ru.sergeykozhukhov.habits.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import ru.sergeykozhukhov.habits.data.converter.HabitConverter;
import ru.sergeykozhukhov.habits.data.converter.HabitListConverter;
import ru.sergeykozhukhov.habits.data.converter.HabitWithProgressesConverter;
import ru.sergeykozhukhov.habits.data.converter.HabitWithProgressesListConverter;
import ru.sergeykozhukhov.habits.data.converter.ProgressListConverter;
import ru.sergeykozhukhov.habits.data.converter.StatisticListConverter;
import ru.sergeykozhukhov.habits.data.database.HabitDao;
import ru.sergeykozhukhov.habits.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.model.data.HabitWithProgressesData;
import ru.sergeykozhukhov.habits.model.data.ProgressData;
import ru.sergeykozhukhov.habits.model.domain.Habit;
import ru.sergeykozhukhov.habits.model.domain.HabitWithProgresses;
import ru.sergeykozhukhov.habits.model.domain.Progress;
import ru.sergeykozhukhov.habits.model.domain.Statistic;

/**
 * Реализация репозитория (база данных)
 */
public class HabitsDatabaseRepository implements IHabitsDatabaseRepository {

    private static final String TAG = "DatabaseRepository";

    /**
     * Интерфейс определяющий возможные операции с базой данных
     */
    private HabitDao habitDao;

    /**
     * Конвертер Habit модели между data и domain слоями
     */
    private HabitConverter habitConverter;

    /**
     * Конвертер списка Habit моделей между data и domain слоями
     */
    private HabitListConverter habitListConverter;

    /**
     * Конвертер списка Progresses моделей между data и domain слоями
     */
    private ProgressListConverter progressListConverter;

    /**
     * Конвертер HabitWithProgresses модели между data и domain слоями
     */
    private HabitWithProgressesConverter habitWithProgressesConverter;

    /**
     * Конвертер списка HabitWithProgresses моделей между data и domain слоями
     */
    private HabitWithProgressesListConverter habitWithProgressesListConverter;

    /**
     * Конвертер списка Statistic моделей между data и domain слоями
     */
    private StatisticListConverter statisticListConverter;

    /**
     * Список дат выполнения конкретной привычки
     */
    private List<ProgressData> savedProgressDataList;

    public HabitsDatabaseRepository(@NonNull HabitDao habitDao,
                                    @NonNull HabitConverter habitConverter,
                                    @NonNull HabitListConverter habitListConverter,
                                    @NonNull ProgressListConverter progressListConverter,
                                    @NonNull HabitWithProgressesConverter habitWithProgressesConverter,
                                    @NonNull HabitWithProgressesListConverter habitWithProgressesListConverter,
                                    @NonNull StatisticListConverter statisticListConverter) {
        this.habitDao = habitDao;
        this.habitConverter = habitConverter;
        this.habitListConverter = habitListConverter;
        this.progressListConverter = progressListConverter;
        this.habitWithProgressesConverter = habitWithProgressesConverter;
        this.habitWithProgressesListConverter = habitWithProgressesListConverter;
        this.statisticListConverter = statisticListConverter;
    }


    /**
     * Добавление записи о привычки
     *
     * @param habit - привычка (domain слой)
     * @return single с id добавленной привычки
     */
    @NonNull
    @Override
    public Single<Long> insertHabit(@NonNull Habit habit) {
        return habitDao.insertHabit(habitConverter.convertFrom(habit));
    }

    /**
     * Добавление списка записей дат выполнения
     *
     * @param progressList список записей дат выполнения (domain слой)
     */
    @NonNull
    @Override
    public Completable insertProgressList(@NonNull List<Progress> progressList) {
        return habitDao.insertProgressList(progressListConverter.convertFrom(progressList));
    }

    /**
     * Добаление списка привычек с соответствующими датами выполнения
     *
     * @param habitWithProgressesList список привычек с соответствующими датами выполнения (domain слой)
     */
    @NonNull
    @Override
    public Completable insertHabitWithProgressesList(@NonNull List<HabitWithProgresses> habitWithProgressesList) {
        return Observable.fromIterable(habitWithProgressesList)
                .flatMapCompletable(habitWithProgresses -> {
                    HabitWithProgressesData habitWithProgressesData = habitWithProgressesConverter.convertFrom(habitWithProgresses);
                    return habitDao.insertHabit(habitWithProgressesData.getHabitData())
                            .flatMapCompletable(idHabitInserted -> {
                                for (ProgressData progressData : habitWithProgressesData.getProgressDataList()) {
                                    progressData.setIdHabit(idHabitInserted);
                                    Log.d(TAG, progressData.toString());
                                }
                                return habitDao.insertProgressList(habitWithProgressesData.getProgressDataList());
                            });
                });
    }

    /**
     * Получение записей всех привычек
     *
     * @return flowable со списком привычек
     */
    @NonNull
    @Override
    public Flowable<List<Habit>> loadHabitList() {
        return habitDao.getHabitList()
                .map(habitDataList ->
                        habitListConverter.convertTo(habitDataList));
    }


    /**
     * Получение записей всех дат выполнения опредленной привычки
     *
     * @param idHabit id привычки
     * @return single со списком дат выполнения (domain слой)
     */
    @NonNull
    @Override
    public Single<List<Progress>> loadProgressListByIdHabit(long idHabit) {
        return habitDao.getProgressByHabit(idHabit)
                .doOnSuccess(progressData -> savedProgressDataList = progressData)
                .map(progressDataList -> progressListConverter.convertTo(progressDataList));
    }


    /**
     * Получение списка данных по привычкам с указанием количества выполненных дней
     *
     * @return single со списком данных по привычкам и указанием количества выполненных дней (domain слой)
     */
    @NonNull
    @Override
    public Single<List<Statistic>> loadStatisticList() {
        return habitDao.getStatisticList()
                .map(statisticDataList ->
                        statisticListConverter.convertTo(statisticDataList));
    }

    /**
     * Получение списка всех привычек с соответствующими датами выполнения
     *
     * @return single со списком всех привычек и соответствующих дат выполнения (domain слой)
     */
    @NonNull
    @Override
    public Single<List<HabitWithProgresses>> loadHabitWithProgressesList() {
        return habitDao.getHabitWithProgressesList()
                .map(habitWithProgressesDataList ->
                        habitWithProgressesListConverter.convertTo(habitWithProgressesDataList));
    }

    /**
     * Удаление всех привычек
     */
    @NonNull
    @Override
    public Completable deleteAllHabits() {
        return habitDao.deleteAll();
    }

    /**
     * Удаление указанного списка дат выполнения
     *
     * @param progressList список дат выполнения (domain слой)
     */
    @NonNull
    @Override
    public Completable deleteProgressList(@NonNull List<Progress> progressList) {
        return habitDao.deleteProgressList(progressListConverter.convertFrom(progressList));
    }

    /**
     * Получение сохранненого списка дат выполнения конкретной привычки
     *
     * @return список дат выполнения конкретной привычки
     * @throws NullPointerException - данный список не сохранен
     */
    @NonNull
    @Override
    public List<Progress> getSavedProgressList() throws NullPointerException {
        if (savedProgressDataList == null)
            throw new NullPointerException();
        return progressListConverter.convertTo(savedProgressDataList);
    }

    /**
     * Сбрасывания сохранненного списка дат выполнения конкретной привычки
     */
    @Override
    public void resetSavedProgressList() {
        savedProgressDataList = null;
    }

}
