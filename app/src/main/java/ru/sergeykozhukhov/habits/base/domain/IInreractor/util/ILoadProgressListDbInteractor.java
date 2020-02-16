package ru.sergeykozhukhov.habits.base.domain.IInreractor.util;

import java.util.List;

import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.model.domain.Progress;

/**
 * Интерфейс интерактора получения списка дат выполенения конкретной привычки из базы данных
 */
public interface ILoadProgressListDbInteractor {
    Single<List<Progress>> loadProgressListByHabit(long idHabit);
}
