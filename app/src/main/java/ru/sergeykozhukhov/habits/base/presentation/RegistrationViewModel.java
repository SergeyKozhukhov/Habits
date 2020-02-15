package ru.sergeykozhukhov.habits.base.presentation;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.base.domain.SingleLiveEvent;
import ru.sergeykozhukhov.habits.base.domain.usecase.AuthenticateWebInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.RegistrateWebInteractor;
import ru.sergeykozhukhov.habits.base.model.domain.Jwt;
import ru.sergeykozhukhov.habits.base.model.exception.BuildException;

public class RegistrationViewModel extends ViewModel {

    private static final String TAG = "AuthenticationViewModel";

    private final RegistrateWebInteractor registrateWebInteractor;

    private CompositeDisposable compositeDisposable;

    private final SingleLiveEvent<Integer> registatedSuccesSingleLiveEvent = new SingleLiveEvent<>();
    private final SingleLiveEvent<Integer> errorSingleLiveEvent = new SingleLiveEvent<>();


    public RegistrationViewModel(RegistrateWebInteractor registrateWebInteractor) {
        this.registrateWebInteractor = registrateWebInteractor;
    }

    private void initData(){
        compositeDisposable = new CompositeDisposable();
    }


    public void registrateClient(String firstname, String lastname, String email, String password) {

        registrateWebInteractor.registrateClient(firstname, lastname, email, password)
                .subscribeOn(Schedulers.newThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        registatedSuccesSingleLiveEvent.postValue(R.string.registration_success_message);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        if (throwable instanceof BuildException) {
                            errorSingleLiveEvent.postValue((((BuildException) throwable).getMessageRes()));
                        }
                        Log.d(TAG, "registration build error");
                    }
                });


    }

    public SingleLiveEvent<Integer> getRegistatedSuccesSingleLiveEvent() {
        return registatedSuccesSingleLiveEvent;
    }

    public SingleLiveEvent<Integer> getErrorSingleLiveEvent() {
        return errorSingleLiveEvent;
    }
}
