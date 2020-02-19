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

public interface IHabitsWebRepository {

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
     * Добавление списка привычек с соответствующими списками дат выполнения
     * @param habitWithProgressesList
     * @param jwt
     * @return
     */
    @NonNull Completable insertHabitWithProgressesList(List<HabitWithProgresses> habitWithProgressesList, @NonNull String jwt);


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

    void deleteJwt();
}
