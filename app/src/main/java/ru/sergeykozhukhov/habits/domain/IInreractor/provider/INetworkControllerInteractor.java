package ru.sergeykozhukhov.habits.domain.IInreractor.provider;

import java.util.function.Consumer;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public interface INetworkControllerInteractor {

    void put(Boolean isConnect);
    Disposable subscribe(Consumer<Boolean> onNext, Consumer<Throwable> onError);
    void unsubscribe();

}
