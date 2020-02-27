package ru.sergeykozhukhov.habits.domain;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import ru.sergeykozhukhov.habits.model.domain.Habit;
import ru.sergeykozhukhov.habits.model.domain.HabitWithProgresses;
import ru.sergeykozhukhov.habits.model.domain.Progress;
import ru.sergeykozhukhov.habits.model.domain.Statistic;

/**
 * Итерфейс репозитория (база данных)
 */
public interface IHabitsDatabaseRepository {

    /**
     * Добавление записи о привычки
     *
     * @param habit - привычка (domain слой)
     * @return single с id добавленной привычки
     */
    @NonNull
    Single<Long> insertHabit(@NonNull Habit habit);

    /**
     * Добавление списка записей дат выполнения
     *
     * @param progressList список записей дат выполнения (domain слой)
     */
    @NonNull
    Completable insertProgressList(@NonNull List<Progress> progressList);

    /**
     * Добаление списка привычек с соответствующими датами выполнения
     *
     * @param habitWithProgressesList список привычек с соответствующими датами выполнения (domain слой)
     */
    @NonNull
    Completable insertHabitWithProgressesList(@NonNull List<HabitWithProgresses> habitWithProgressesList);

    /**
     * Загрузка списка привычек
     *
     * @return
     */
    @NonNull
    Flowable<List<Habit>> loadHabitList();

    /**
     * Получение записей всех дат выполнения опредленной привычки
     *
     * @param idHabit id привычки
     * @return single со списком дат выполнения (domain слой)
     */
    @NonNull
    Single<List<Progress>> loadProgressListByIdHabit(long idHabit);

    /**
     * Получение списка данных по привычкам с указанием количества выполненных дней
     *
     * @return single со списком данных по привычкам и указанием количества выполненных дней (domain слой)
     */
    @NonNull
    Single<List<Statistic>> loadStatisticList();

    /**
     * Получение списка всех привычек с соответствующими датами выполнения
     *
     * @return single со списком всех привычек и соответствующих дат выполнения (domain слой)
     */
    @NonNull
    Single<List<HabitWithProgresses>> loadHabitWithProgressesList();

    /**
     * Удаление всех привычек
     */
    @NonNull
    Completable deleteAllHabits();

    /**
     * Удаление указанного списка дат выполнения
     *
     * @param progressList список дат выполнения (domain слой)
     */
    @NonNull
    Completable deleteProgressList(@NonNull List<Progress> progressList);

}

