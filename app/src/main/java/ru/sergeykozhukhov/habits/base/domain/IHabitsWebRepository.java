package ru.sergeykozhukhov.habits.base.domain;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.model.data.HabitWithProgressesData;
import ru.sergeykozhukhov.habits.base.model.domain.Confidentiality;
import ru.sergeykozhukhov.habits.base.model.domain.Habit;
import ru.sergeykozhukhov.habits.base.model.domain.HabitWithProgresses;
import ru.sergeykozhukhov.habits.base.model.domain.Jwt;
import ru.sergeykozhukhov.habits.base.model.domain.Progress;
import ru.sergeykozhukhov.habits.base.model.domain.Registration;

public interface IHabitsWebRepository extends IRepository {

    /**
     * Регистрирование нового пользователя
     * @param registration
     * @return
     */
    @NonNull Completable registerClient(@NonNull Registration registration);

    /**
     * Вход пользователя в свой аккаунт
     * @param confidentiality
     * @return
     */
    @NonNull Single<Jwt> authenticateClient(@NonNull Confidentiality confidentiality);

    /**
     * Добавление привычки
     * @param habit
     * @param jwt
     * @return
     */
    @NonNull Single<Habit> insertHabit(Habit habit, @NonNull String jwt);

    /**
     * Добавление списка привычек
     * @param habitList
     * @param jwt
     * @return
     */
    @NonNull Completable insertHabits(List<Habit> habitList, @NonNull String jwt);

    /**
     * Добавление списка дат выполения
     * @param progressList
     * @param jwt
     * @return
     */
    @NonNull Completable insertProgressList(List<Progress> progressList, @NonNull String jwt);

    /**
     * Добавление списка привычек с соответствующими списками дат выполнения
     * @param habitWithProgressesList
     * @param jwt
     * @return
     */
    @NonNull Completable insertHabitWithProgressesList(List<HabitWithProgresses> habitWithProgressesList, @NonNull String jwt);

    /**
     * Получение списка всех привычек
     * @param jwt
     * @return
     */
    @NonNull Single<List<Habit>> loadHabitList(@NonNull String jwt);

    /**
     * Получение списка всех привычек с соответствующими списками дат выполнения
     * @param jwt
     * @return
     */
    @NonNull Single<List<HabitWithProgresses>> loadHabitWithProgressesList(@NonNull String jwt);

    /**
     * Сохранение в памяти токена
     * @param jwt
     */
    void setJwt(@NonNull Jwt jwt);

    /**
     * Получение из памяти токена
     * @return
     */
    Jwt getJwt();
}
