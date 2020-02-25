package ru.sergeykozhukhov.habits.presentation.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.SingleLiveEvent;
import ru.sergeykozhukhov.habits.domain.usecase.AuthenticateWebInteractor;

import ru.sergeykozhukhov.habits.model.domain.exception.AuthenticateException;
import ru.sergeykozhukhov.habits.model.domain.exception.BuildException;

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
        compositeDisposable = new CompositeDisposable();
    }

    public void authenticateClient(String email, String password) {
        compositeDisposable.add(authenticateClientInteractor.authenticateClient(email, password)
                .subscribeOn(Schedulers.newThread())
                .subscribe(() -> successSingleLiveEvent.postValue(R.string.authentication_success_message), throwable -> {
                    if (throwable instanceof BuildException) {
                        errorSingleLiveEvent.postValue((((BuildException) throwable).getMessageRes()));
                    }
                    if (throwable instanceof AuthenticateException) {
                        errorSingleLiveEvent.postValue((((AuthenticateException) throwable).getMessageRes()));
                    }
                }));

    }

    public SingleLiveEvent<Integer> getSuccessSingleLiveEvent() {
        return successSingleLiveEvent;
    }

    public SingleLiveEvent<Integer> getErrorSingleLiveEvent() {
        return errorSingleLiveEvent;
    }

    public void cancelSubscritions() {
        compositeDisposable.clear();
    }
}
