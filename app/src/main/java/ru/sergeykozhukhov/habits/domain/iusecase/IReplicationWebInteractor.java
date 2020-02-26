package ru.sergeykozhukhov.habits.domain.iusecase;

import androidx.annotation.NonNull;

import io.reactivex.Completable;

/**
 * Интерейс интерактора сохранения в базу данных всех записей с сервера
 */
public interface IReplicationWebInteractor {

    @NonNull
    Completable loadHabitWithProgressesList();

}
