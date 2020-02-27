package ru.sergeykozhukhov.habits.domain;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.sergeykozhukhov.habits.model.domain.Confidentiality;
import ru.sergeykozhukhov.habits.model.domain.Habit;
import ru.sergeykozhukhov.habits.model.domain.HabitWithProgresses;
import ru.sergeykozhukhov.habits.model.domain.Jwt;
import ru.sergeykozhukhov.habits.model.domain.Progress;
import ru.sergeykozhukhov.habits.model.domain.Registration;

/**
 * Итерфейс репозитория (сервер)
 */
public interface IHabitsWebRepository {

    /**
     * Регистрация нового клиента
     *
     * @param registration данные регистрации (имя, фамилия, email, пароль) (domain слой)
     */
    @NonNull
    Completable registerClient(@NonNull Registration registration);

    /**
     * Аутентификация пользователя
     *
     * @param confidentiality данные, подвергающиеся проверке (email, пароль) (domain слой)
     * @return single с token (jwt) (domain слой)
     */
    @NonNull
    Single<Jwt> authenticateClient(@NonNull Confidentiality confidentiality);


    /**
     * Добавление списка привычек с датами выполнения на сервер
     *
     * @param habitWithProgressesList список привычек с датами выполнения (domain слой)
     * @param jwt                     строковое представление token (jwt)
     */
    @NonNull
    Completable insertHabitWithProgressesList(List<HabitWithProgresses> habitWithProgressesList, @NonNull String jwt);

    /**
     * Загрузка списка привычек с датами выполнениям с сервера
     *
     * @param jwt строковое представление token (jwt)
     * @return single со списком привычек и датами их выполнения (domain слой)
     */
    @NonNull
    Single<List<HabitWithProgresses>> loadHabitWithProgressesList(@NonNull String jwt);

    /**
     * Сохранение token (jwt) в памяти
     *
     * @param jwt token (jwt) (domain слой)
     */
    void setJwt(@NonNull Jwt jwt);

    /**
     * Получение сохранненого в памяти token (jwt)
     *
     * @return token (jwt) (domain слой)
     * @throws NullPointerException token (jwt) является null
     */
    @NonNull
    Jwt getJwt() throws NullPointerException;

    /**
     * Обнуление token (jwt) в памяти (= null)
     */
    void deleteJwt();
}
