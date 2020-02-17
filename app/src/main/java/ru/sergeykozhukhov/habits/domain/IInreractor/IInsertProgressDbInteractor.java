package ru.sergeykozhukhov.habits.domain.IInreractor;

import io.reactivex.Completable;
import ru.sergeykozhukhov.habits.model.domain.Progress;

/**
 * Интерфейс интерактора добавления даты выполнения привычки в базу данных
 */
public interface IInsertProgressDbInteractor {

    Completable insertProgress(Progress progress);

}
