package ru.sergeykozhukhov.habits.domain.usecase;

import android.util.Log;

import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import ru.sergeykozhukhov.habits.domain.IInreractor.INetworkControllerInteractor;

public class NetworkControllerInteractor implements INetworkControllerInteractor {

    private static NetworkControllerInteractor networkControllerInteractor;

    private final BehaviorSubject<Boolean> networkConnectionStatus;


    public static NetworkControllerInteractor getInstance() {

        if (networkControllerInteractor == null){
            networkControllerInteractor = new NetworkControllerInteractor(BehaviorSubject.create());
        }
        return networkControllerInteractor;
    }

    private NetworkControllerInteractor(BehaviorSubject<Boolean> networkConnectionStatus) {
        this.networkConnectionStatus = networkConnectionStatus;
    }

    @Override
    public void put(Boolean isConnect) {
        networkConnectionStatus.onNext(isConnect);
        Log.d("net inter: ", isConnect.toString());
    }

    @Override
    public Disposable subscribe(io.reactivex.functions.Consumer<? super Boolean> onNext, io.reactivex.functions.Consumer<? super Throwable> onError) {
        return networkConnectionStatus.subscribe(onNext, onError);
    }
}
