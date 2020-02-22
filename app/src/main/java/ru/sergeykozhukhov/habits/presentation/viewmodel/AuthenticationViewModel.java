package ru.sergeykozhukhov.habits.presentation.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.SingleLiveEvent;
import ru.sergeykozhukhov.habits.domain.usecase.AuthenticateWebInteractor;

import ru.sergeykozhukhov.habits.domain.usecase.NetworkControllerInteractor;
import ru.sergeykozhukhov.habits.model.exception.AuthenticateException;

/**
 * ViewModel для входа пользователя в свой аккаунт
 */
public class AuthenticationViewModel extends ViewModel {

    private static final String TAG = "AuthenticationViewModel";

    private final AuthenticateWebInteractor authenticateClientInteractor;
    private final NetworkControllerInteractor networkControllerInteractor;
    private final SingleLiveEvent<Integer> successSingleLiveEvent = new SingleLiveEvent<>();
    private final SingleLiveEvent<Integer> errorSingleLiveEvent = new SingleLiveEvent<>();
    private CompositeDisposable compositeDisposable;

    private boolean isConnect;


    public AuthenticationViewModel(@NonNull AuthenticateWebInteractor authenticateClientInteractor, NetworkControllerInteractor networkControllerInteractor) {

        this.authenticateClientInteractor = authenticateClientInteractor;
        this.networkControllerInteractor = networkControllerInteractor;
        initData();
        initNetworkController();
    }

    private void initData() {
        compositeDisposable = new CompositeDisposable();
    }

    private void initNetworkController() {
        compositeDisposable.add(networkControllerInteractor.subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                isConnect = aBoolean;
                if (!aBoolean)
                    errorSingleLiveEvent.postValue(R.string.connect_network_error_message);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        }));
    }


    public void authenticateClient(String email, String password) {

        if (isConnect) {
            compositeDisposable.add(authenticateClientInteractor.authenticateClient(email, password)
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(idRes -> {
                        successSingleLiveEvent.postValue(R.string.authentication_success_message);
                        Log.d(TAG, "authenticateClient: success");
                    }, throwable -> {
                        if (throwable instanceof AuthenticateException) {
                            errorSingleLiveEvent.postValue((((AuthenticateException) throwable).getMessageRes()));
                            Log.d(TAG, "authenticateClient: error");
                        }

                    }));
        } else {
            errorSingleLiveEvent.postValue(R.string.connect_network_error_message);
        }
    }

    public SingleLiveEvent<Integer> getSuccessSingleLiveEvent() {
        return successSingleLiveEvent;
    }

    public SingleLiveEvent<Integer> getErrorSingleLiveEvent() {
        return errorSingleLiveEvent;
    }

    public void cancelSubscribe() {
        compositeDisposable.clear();
    }
}
