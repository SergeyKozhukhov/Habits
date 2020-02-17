package ru.sergeykozhukhov.habits.presentation;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.IInreractor.provider.IGetJwtValueInteractor;
import ru.sergeykozhukhov.habits.domain.SingleLiveEvent;
import ru.sergeykozhukhov.habits.domain.usecase.GetJwtValueInteractor;
import ru.sergeykozhukhov.habits.model.domain.Jwt;
import ru.sergeykozhukhov.habits.model.exception.GetJwtException;
import ru.sergeykozhukhov.habits.model.exception.RegisterException;

public class AccountManagerViewModel extends ViewModel {

    private static final String TAG = "AuthenticationViewModel";

    private final IGetJwtValueInteractor getJwtValueInteractor;

    private CompositeDisposable compositeDisposable;

    private final SingleLiveEvent<Integer> successSingleLiveEvent = new SingleLiveEvent<>();
    private final SingleLiveEvent<Integer> errorSingleLiveEvent = new SingleLiveEvent<>();


    public AccountManagerViewModel(IGetJwtValueInteractor getJwtValueInteractor) {
        this.getJwtValueInteractor = getJwtValueInteractor;

        initData();
    }


    private void initData() {
        compositeDisposable = new CompositeDisposable();
    }


    public void isLogInClient() {
        compositeDisposable.add(getJwtValueInteractor.getValueSingle().subscribe(new Consumer<Jwt>() {
            @Override
            public void accept(Jwt jwt) throws Exception {
                successSingleLiveEvent.postValue(R.string.authentication_success_message);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (throwable instanceof GetJwtException) {
                    errorSingleLiveEvent.postValue((((GetJwtException) throwable).getMessageRes()));
                }

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
