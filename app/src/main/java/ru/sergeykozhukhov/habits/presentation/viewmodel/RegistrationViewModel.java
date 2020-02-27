package ru.sergeykozhukhov.habits.presentation.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.SingleLiveEvent;
import ru.sergeykozhukhov.habits.domain.usecaseimpl.RegisterWebInteractor;
import ru.sergeykozhukhov.habits.model.domain.exception.BuildException;
import ru.sergeykozhukhov.habits.model.domain.exception.RegisterException;

/**
 * ViewModel для регистрации нового пользователя
 */
public class RegistrationViewModel extends ViewModel {

    /**
     * Интерактор регистрации нового пользователя
     */
    private final RegisterWebInteractor registerWebInteractor;

    /**
     * LiveData с идентификаторами строковых ресурсов сообщений о успешном выполненнии операции
     */
    private final SingleLiveEvent<Integer> successSingleLiveEvent = new SingleLiveEvent<>();

    /**
     * LiveData с идентификаторами строковых ресурсов сообщений об ошибках
     */
    private final SingleLiveEvent<Integer> errorSingleLiveEvent = new SingleLiveEvent<>();

    /**
     * LiveData с состоянием выполнения операции (true - операция выполняется, false - операция закончена)
     */
    private final MutableLiveData<Boolean> isLoadingMutableLiveData = new MutableLiveData<>();

    private CompositeDisposable compositeDisposable;

    public RegistrationViewModel(RegisterWebInteractor registerWebInteractor) {
        this.registerWebInteractor = registerWebInteractor;
        compositeDisposable = new CompositeDisposable();
    }

    /**
     * Регистрация нового пользователя
     *
     * @param firstname            имя
     * @param lastname             фамилия
     * @param email                почта
     * @param password             пароль
     * @param passwordConfirmation пповторный пароль для подтверждения
     */
    public void registerClient(String firstname, String lastname, String email, String password, String passwordConfirmation) {

        isLoadingMutableLiveData.setValue(true);
        compositeDisposable.add(registerWebInteractor.registerClient(firstname, lastname, email, password, passwordConfirmation)
                .subscribeOn(Schedulers.newThread())
                .doOnTerminate(() -> isLoadingMutableLiveData.postValue(false))
                .subscribe(() -> successSingleLiveEvent.postValue(R.string.registration_success_message),
                        throwable -> {
                            if (throwable instanceof RegisterException)
                                errorSingleLiveEvent.postValue((((RegisterException) throwable).getMessageRes()));
                            else if (throwable instanceof BuildException)
                                errorSingleLiveEvent.postValue((((BuildException) throwable).getMessageRes()));
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
