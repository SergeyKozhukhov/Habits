package ru.sergeykozhukhov.habits.presentation.viewmodel;

import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.IInreractor.IGetJwtValueInteractor;
import ru.sergeykozhukhov.habits.domain.SingleLiveEvent;
import ru.sergeykozhukhov.habits.domain.usecase.GetJwtValueInteractor;
import ru.sergeykozhukhov.habits.model.domain.exception.GetJwtException;

public class AccountManagerViewModel extends ViewModel {

    private static final String TAG = "AuthenticationViewModel";

    private final GetJwtValueInteractor getJwtValueInteractor;

    private final SingleLiveEvent<Integer> successSingleLiveEvent = new SingleLiveEvent<>();
    private final SingleLiveEvent<Integer> errorSingleLiveEvent = new SingleLiveEvent<>();


    public AccountManagerViewModel(GetJwtValueInteractor getJwtValueInteractor) {
        this.getJwtValueInteractor = getJwtValueInteractor;
    }

    public void isLogInClient() {

        try {
            getJwtValueInteractor.getValue();
            successSingleLiveEvent.postValue(R.string.get_jwt_success_message);
        } catch (GetJwtException e) {
            errorSingleLiveEvent.postValue((e.getMessageRes()));
        }
    }

    public SingleLiveEvent<Integer> getSuccessSingleLiveEvent() {
        return successSingleLiveEvent;
    }

    public SingleLiveEvent<Integer> getErrorSingleLiveEvent() {
        return errorSingleLiveEvent;
    }

}
