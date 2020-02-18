package ru.sergeykozhukhov.habits.presentation.viewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.SingleLiveEvent;
import ru.sergeykozhukhov.habits.domain.usecase.RegisterWebInteractor;
import ru.sergeykozhukhov.habits.model.exception.BuildException;
import ru.sergeykozhukhov.habits.model.exception.RegisterException;

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

    public void registerClient(String firstname, String lastname, String email, String password, String passwodConfirmation) {

        compositeDisposable.add(registerWebInteractor.registerClient(firstname, lastname, email, password, passwodConfirmation)
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Action() {
                               @Override
                               public void run() throws Exception {
                                   succesSingleLiveEvent.postValue(R.string.registration_success_message);
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                if (throwable instanceof RegisterException) {
                                    errorSingleLiveEvent.postValue((((RegisterException) throwable).getMessageRes()));
                                    Log.d(TAG, "registration build error");
                                }
                                if (throwable instanceof BuildException) {
                                    errorSingleLiveEvent.postValue((((BuildException) throwable).getMessageRes()));
                                    Log.d(TAG, "registration build error");
                                }
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
