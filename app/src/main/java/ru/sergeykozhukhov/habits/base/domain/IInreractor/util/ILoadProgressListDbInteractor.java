package ru.sergeykozhukhov.habits.base.domain.IInreractor.util;

import java.util.List;

import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.model.domain.Progress;

public interface ILoadProgressListDbInteractor {
    Single<List<Progress>> loadProgressListByHabit(long idHabit);
}
