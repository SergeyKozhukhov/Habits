package ru.sergeykozhukhov.habits.presentation.viewmodel;

import androidx.lifecycle.ViewModel;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.SingleLiveEvent;
import ru.sergeykozhukhov.habits.domain.usecaseimpl.GetJwtValueInteractor;
import ru.sergeykozhukhov.habits.model.domain.exception.GetJwtException;

/**
 * View Model для определения авторизован ли пользователь
 */
public class AccountManagerViewModel extends ViewModel {

    /**
     * Иинтерактор получения токена, сохраненного в памяти/preferences
     */
    private final GetJwtValueInteractor getJwtValueInteractor;

    /**
     * LiveData с идентификаторами строковых ресурсов сообщений о успешном выполненнии операции
     */
    private final SingleLiveEvent<Integer> successSingleLiveEvent = new SingleLiveEvent<>();

    /**
     * LiveData с идентификаторами строковых ресурсов сообщений об ошибках
     */
    private final SingleLiveEvent<Integer> errorSingleLiveEvent = new SingleLiveEvent<>();

    public AccountManagerViewModel(GetJwtValueInteractor getJwtValueInteractor) {
        this.getJwtValueInteractor = getJwtValueInteractor;
    }

    /**
     * Определение авторизован ли пользователь
     */
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
