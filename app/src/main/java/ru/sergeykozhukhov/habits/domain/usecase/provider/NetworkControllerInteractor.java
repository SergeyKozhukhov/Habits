package ru.sergeykozhukhov.habits.domain.usecase.provider;

import org.reactivestreams.Subscription;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import ru.sergeykozhukhov.habits.domain.IInreractor.provider.INetworkControllerInteractor;

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
    }

    @Override
    public void subscribe(Observer<Boolean> observer) {
        networkConnectionStatus.subscribe(observer);
    }


    @Override
    public void unsubscribe() {

    }
}
