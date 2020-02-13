package ru.sergeykozhukhov.habits.base.domain.IInreractor.util;

import io.reactivex.Completable;
import ru.sergeykozhukhov.habits.base.model.domain.Progress;

public interface IInsertProgressDbInteractor {

    Completable insertProgress(Progress progress);

}
