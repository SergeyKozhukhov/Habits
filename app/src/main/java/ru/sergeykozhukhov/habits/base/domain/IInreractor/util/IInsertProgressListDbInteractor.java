package ru.sergeykozhukhov.habits.base.domain.IInreractor.util;

import java.util.List;

import io.reactivex.Completable;
import ru.sergeykozhukhov.habits.base.model.domain.Progress;

/**
 * Интерфейс интерактора добавления списка дат выполнения привычки в базу данных
 */
public interface IInsertProgressListDbInteractor {
    Completable insertProgressList(List<Progress> progressList);
}
