package ru.sergeykozhukhov.habits.presentation;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.SingleLiveEvent;
import ru.sergeykozhukhov.habits.domain.usecase.AuthenticateWebInteractor;

import ru.sergeykozhukhov.habits.model.exception.AuthenticateException;

/**
 * ViewModel для входа пользователя в свой аккаунт
 */
public class AuthenticationViewModel extends ViewModel {

    private static final String TAG = "AuthenticationViewModel";

    private final AuthenticateWebInteractor authenticateClientInteractor;
    private final SingleLiveEvent<Integer> successSingleLiveEvent = new SingleLiveEvent<>();
    private final SingleLiveEvent<Integer> errorSingleLiveEvent = new SingleLiveEvent<>();
    private CompositeDisposable compositeDisposable;


    public AuthenticationViewModel(@NonNull AuthenticateWebInteractor authenticateClientInteractor) {

        this.authenticateClientInteractor = authenticateClientInteractor;
        initData();
    }

    private void initData() {
        compositeDisposable = new CompositeDisposable();
    }


    public void authenticateClient(String email, String password) {

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
