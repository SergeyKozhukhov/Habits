package ru.sergeykozhukhov.habits.domain.usecase;

/**
 * Интерфейс интерактора выхода пользователя из аккаунта
 */
public interface IDeleteJwtInteractor {

    /**
     * Обнуление/удаление сохраненного token (jwt)
     */
    void deleteJwt();
}
