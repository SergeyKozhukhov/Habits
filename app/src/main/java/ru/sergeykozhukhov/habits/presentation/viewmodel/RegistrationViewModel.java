package ru.sergeykozhukhov.habits.presentation.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.SingleLiveEvent;
import ru.sergeykozhukhov.habits.domain.usecase.RegisterWebInteractor;
import ru.sergeykozhukhov.habits.model.domain.exception.BuildException;
import ru.sergeykozhukhov.habits.model.domain.exception.RegisterException;

/**
 * ViewModel для регистрации нового пользователя
 */
public class RegistrationViewModel extends ViewModel {

    private static final String TAG = "AuthenticationViewModel";

    private final RegisterWebInteractor registerWebInteractor;

    private CompositeDisposable compositeDisposable;

    private final SingleLiveEvent<Integer> successSingleLiveEvent = new SingleLiveEvent<>();
    private final SingleLiveEvent<Integer> errorSingleLiveEvent = new SingleLiveEvent<>();
    private final MutableLiveData<Boolean> isLoadingMutableLiveData = new MutableLiveData<>();


    public RegistrationViewModel(RegisterWebInteractor registerWebInteractor) {
        this.registerWebInteractor = registerWebInteractor;
        compositeDisposable = new CompositeDisposable();
    }

    public void registerClient(String firstname, String lastname, String email, String password, String passwodConfirmation) {

        isLoadingMutableLiveData.setValue(true);
        compositeDisposable.add(registerWebInteractor.registerClient(firstname, lastname, email, password, passwodConfirmation)
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

    public void cancelSubscritions() {
        compositeDisposable.clear();
    }
}
