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

public interface IHabitsDatabaseRepository {

    /**
     * Добавление привычки
     * @param habit
     * @return
     */
    @NonNull Single<Long> insertHabit(@NonNull Habit habit);

    /**
     * Добавление списка привычек
     * @param habitList
     * @return
     */
    @NonNull Completable insertHabitList(@NonNull List<Habit> habitList);

    /**
     * Добавление даты выполнения
     * @param progress
     * @return
     */
    @NonNull Completable insertProgress(@NonNull Progress progress);

    /**
     * Добавление списка дат выполнения
     * @param progressList
     * @return
     */
    @NonNull Completable insertProgressList(@NonNull List<Progress> progressList);

    /**
     * Добавление списка привычек с соответствующими датами выполнения
     * @param habitWithProgressesList
     * @return
     */
    @NonNull Completable insertHabitWithProgressesList(@NonNull List<HabitWithProgresses> habitWithProgressesList);

    /**
     * Загрузка списка привычек
     * @return
     */
    @NonNull Flowable<List<Habit>> loadHabitList();

    /**
     * Загрузка дат выполнения определенной привычки
     * @param idHabit
     * @return
     */
    @NonNull Single<List<Progress>> loadProgressListByIdHabit(long idHabit);

    /**
     * Загрузка списка дат выполнения всех привычек
     * @return
     */
    @NonNull Single<List<Progress>> loadProgressList();

    /**
     * Загрузка списка привычек с колличетсвом выполнныех дней
     * @return
     */
    @NonNull Single<List<Statistic>> loadStatisticList();

    /**
     * Загрузка списка всех привычек с соответствующими списками дат выполнения
     * @return
     */
    @NonNull Single<List<HabitWithProgresses>> loadHabitWithProgressesList();

    /**
     * Обновление информации о привычке
     * @param habit
     * @return
     */
    @NonNull Completable updateHabit(@NonNull Habit habit);

    /**
     * Удаление всех привычек
     * @return
     */
    @NonNull Completable deleteAllHabits();

    /**
     * Удаление списка привычек
     * @param progressList
     * @return
     */
    @NonNull Completable deleteProgressList(@NonNull List<Progress> progressList);

}

