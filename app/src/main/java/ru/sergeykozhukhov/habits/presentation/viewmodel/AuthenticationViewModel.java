package ru.sergeykozhukhov.habits.presentation.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.SingleLiveEvent;
import ru.sergeykozhukhov.habits.domain.usecaseimpl.AuthenticateWebInteractor;

import ru.sergeykozhukhov.habits.model.domain.exception.AuthenticateException;
import ru.sergeykozhukhov.habits.model.domain.exception.BuildException;

/**
 * ViewModel для входа пользователя в свой аккаунт
 */
public class AuthenticationViewModel extends ViewModel {

    /**
     * Интерктор входа пользователя в свой аккаунт
     */
    private final AuthenticateWebInteractor authenticateClientInteractor;

    /**
     * LiveData с идентификаторами строковых ресурсов сообщений о успешном выполненнии операции
     */
    private final SingleLiveEvent<Integer> successSingleLiveEvent = new SingleLiveEvent<>();

    /**
     * LiveData с идентификаторами строковых ресурсов сообщений об ошибках
     */
    private final SingleLiveEvent<Integer> errorSingleLiveEvent = new SingleLiveEvent<>();

    /**
     * LiveData с состоянием выполнения операции входа в аккаунт (true - операция выполняется, false - операция закончена)
     */
    private final MutableLiveData<Boolean> isLoadingMutableLiveData = new MutableLiveData<>();

    private CompositeDisposable compositeDisposable;

    public AuthenticationViewModel(@NonNull AuthenticateWebInteractor authenticateClientInteractor) {

        this.authenticateClientInteractor = authenticateClientInteractor;
        compositeDisposable = new CompositeDisposable();
    }

    /**
     * Вход пользователя в свой аккаунт
     *
     * @param email    почта
     * @param password пароль
     */
    public void authenticateClient(String email, String password) {
        isLoadingMutableLiveData.setValue(false);
        compositeDisposable.add(authenticateClientInteractor.authenticateClient(email, password)
                .subscribeOn(Schedulers.newThread())
                .doOnTerminate(() -> isLoadingMutableLiveData.postValue(false))
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

    public MutableLiveData<Boolean> getIsLoadingMutableLiveData() {
        return isLoadingMutableLiveData;
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
    }
}
