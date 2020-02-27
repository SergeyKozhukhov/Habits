package ru.sergeykozhukhov.habits.domain.usecase;

import androidx.annotation.NonNull;

import io.reactivex.Completable;

/**
 * Интерейс интерактора восстановления данных о привычках с сервера
 */
public interface IReplicationWebInteractor {

    /**
     * Сохранения в базу данных всех записей с сервера
     */
    @NonNull
    Completable loadHabitWithProgressesList();

}
