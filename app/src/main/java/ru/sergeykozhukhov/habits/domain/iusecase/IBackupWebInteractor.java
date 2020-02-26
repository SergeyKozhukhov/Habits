package ru.sergeykozhukhov.habits.domain.iusecase;

import androidx.annotation.NonNull;

import io.reactivex.Completable;

/**
 * Интерейс интерактора сохранения на сервер всех записей из базы данных
 */
public interface IBackupWebInteractor {
    @NonNull
    Completable insertHabitWithProgressesList();
}
