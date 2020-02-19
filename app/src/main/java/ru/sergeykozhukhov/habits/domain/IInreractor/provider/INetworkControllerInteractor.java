package ru.sergeykozhukhov.habits.domain.IInreractor.provider;

import io.reactivex.Observer;

public interface INetworkControllerInteractor {

    void put(Boolean isConnect);
    void subscribe(Observer<Boolean> observer);
    void unsubscribe();

}
