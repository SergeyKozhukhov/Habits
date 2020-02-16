package ru.sergeykozhukhov.habits.base.domain.IInreractor.util;

import io.reactivex.Completable;
import ru.sergeykozhukhov.habits.base.model.domain.Progress;

/**
 * Интерфейс интерактора добавления даты выполнения привычки в базу данных
 */
public interface IInsertProgressDbInteractor {

    Completable insertProgress(Progress progress);

}
