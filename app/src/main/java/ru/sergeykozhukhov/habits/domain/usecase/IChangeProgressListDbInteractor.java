package ru.sergeykozhukhov.habits.domain.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.sergeykozhukhov.habits.model.domain.exception.ChangeProgressException;

/**
 * Интерфейс интерактора по изменению дат выполнения конкретной привычки
 */
public interface IChangeProgressListDbInteractor {

    /**
     * Получение списка дат выполнения конкретной привычки
     * @param idHabit
     * @return
     */
    @NonNull
    Single<List<Date>> getProgressList(long idHabit);

    /**
     * Подготовка предоставленной даты для сохранения
     * @param date
     * @throws ChangeProgressException
     */
    void addNewDate(@Nullable Date date) throws ChangeProgressException;

    /**
     * Подготовка предоставленной даты для удаления
     * @param date
     * @throws ChangeProgressException
     */
    void deleteDate(@Nullable Date date) throws ChangeProgressException;

    /**
     * Сохранение изменнной информации по датам выполнения привычки
     */
    @Nullable
    Completable saveProgressList();

}
