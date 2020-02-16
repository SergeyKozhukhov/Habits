package ru.sergeykozhukhov.habits.base.presentation;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.base.domain.SingleLiveEvent;
import ru.sergeykozhukhov.habits.base.domain.usecase.RegisterWebInteractor;
import ru.sergeykozhukhov.habits.base.model.exception.BuildException;
import ru.sergeykozhukhov.habits.base.model.exception.RegisterException;

/**
 * ViewModel для регистрации нового пользователя
 */
public class RegistrationViewModel extends ViewModel {

    private static final String TAG = "AuthenticationViewModel";

    private final RegisterWebInteractor registerWebInteractor;

    private CompositeDisposable compositeDisposable;

    private final SingleLiveEvent<Integer> succesSingleLiveEvent = new SingleLiveEvent<>();
    private final SingleLiveEvent<Integer> errorSingleLiveEvent = new SingleLiveEvent<>();


    public RegistrationViewModel(RegisterWebInteractor registerWebInteractor) {
        this.registerWebInteractor = registerWebInteractor;

        initData();
    }

    private void initData(){
        compositeDisposable = new CompositeDisposable();
    }

    public void registrateClient(String firstname, String lastname, String email, String password) {

        compositeDisposable.add(registerWebInteractor.registerClient(firstname, lastname, email, password)
                .subscribeOn(Schedulers.newThread())
                .subscribe(() ->
                        succesSingleLiveEvent.postValue(R.string.registration_success_message),
                        throwable -> {
                            if (throwable instanceof RegisterException) {
                                errorSingleLiveEvent.postValue((((RegisterException) throwable).getMessageRes()));
                                Log.d(TAG, "registration build error");
                            }
                        }));


    }

    public SingleLiveEvent<Integer> getSuccesSingleLiveEvent() {
        return succesSingleLiveEvent;
    }

    public SingleLiveEvent<Integer> getErrorSingleLiveEvent() {
        return errorSingleLiveEvent;
    }

    public void cancelSubscribe() {
        compositeDisposable.clear();
    }
}
