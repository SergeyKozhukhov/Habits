package ru.sergeykozhukhov.habits.base.presentation;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habits.base.domain.SingleLiveEvent;
import ru.sergeykozhukhov.habits.base.domain.usecase.AuthenticateWebInteractor;

import ru.sergeykozhukhov.habits.base.model.domain.Confidentiality;
import ru.sergeykozhukhov.habits.base.model.domain.Jwt;

public class AuthenticationViewModel extends ViewModel {

    private static final String TAG = "AuthenticationViewModel";

    private final AuthenticateWebInteractor authenticateClientInteractor;

    private Disposable disposableAuthenticated;

    private SingleLiveEvent<Boolean> isAuthenticatedSingleLiveEvent;


    public AuthenticationViewModel(@NonNull AuthenticateWebInteractor authenticateClientInteractor) {

        this.authenticateClientInteractor = authenticateClientInteractor;
        initData();
    }

    private void initData(){
        isAuthenticatedSingleLiveEvent = new SingleLiveEvent<>();
    }


    public void authenticateClientRx(Confidentiality confidentiality) {

        disposableAuthenticated = authenticateClientInteractor.authenticateClientRx(confidentiality)
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<Jwt>() {
                    @Override
                    public void accept(Jwt jwt) throws Exception {
                        isAuthenticatedSingleLiveEvent.postValue(true);
                        Log.d(TAG, "authenticateClientRx: success");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        isAuthenticatedSingleLiveEvent.postValue(false);
                        Log.d(TAG, "authenticateClientRx: error");
                    }
                });
    }

    public SingleLiveEvent<Boolean> getIsAuthenticatedSingleLiveEvent() {
        return isAuthenticatedSingleLiveEvent;
    }
}
