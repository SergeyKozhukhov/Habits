package ru.sergeykozhukhov.habits.presentation.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.usecaseimpl.GetJwtValueInteractor;
import ru.sergeykozhukhov.habits.model.domain.exception.GetJwtException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountManagerViewModelTest {

    @Rule
    public final InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    private AccountManagerViewModel accountManagerViewModel;
    @Mock
    private GetJwtValueInteractor getJwtValueInteractor;

    @Before
    public void setUp() {
        accountManagerViewModel = new AccountManagerViewModel(getJwtValueInteractor);
    }

    @Test
    public void isLogInClientSuccess() throws GetJwtException {
        when(getJwtValueInteractor.getValue()).thenReturn("token");
        accountManagerViewModel.isLogInClient();

        assertThat(accountManagerViewModel.getSuccessSingleLiveEvent().getValue(), is(R.string.get_jwt_success_message));
        verify(getJwtValueInteractor).getValue();
    }

    @Test
    public void isLogInClientError() throws GetJwtException {
        GetJwtException getJwtException = new GetJwtException(R.string.get_jwt_exception, new Exception());

        when(getJwtValueInteractor.getValue()).thenThrow(getJwtException);
        accountManagerViewModel.isLogInClient();

        assertThat(accountManagerViewModel.getErrorSingleLiveEvent().getValue(), is(R.string.get_jwt_exception));
        verify(getJwtValueInteractor).getValue();
    }
}