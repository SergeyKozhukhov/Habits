package ru.sergeykozhukhov.habits.domain.IInreractor;

import java.util.function.Consumer;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public interface INetworkControllerInteractor {

    void put(Boolean isConnect);
    Disposable subscribe(io.reactivex.functions.Consumer<? super Boolean> onNext, io.reactivex.functions.Consumer<? super Throwable> onError);
}
