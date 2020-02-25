package ru.sergeykozhukhov.habits.presentation.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Completable;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.RxImmediateSchedulerRule;
import ru.sergeykozhukhov.habits.domain.usecase.AuthenticateWebInteractor;
import ru.sergeykozhukhov.habits.model.domain.exception.AuthenticateException;
import ru.sergeykozhukhov.habits.model.domain.exception.BuildException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationViewModelTest {

    @Rule
    public final RxImmediateSchedulerRule rxImmediateSchedulerRule = new RxImmediateSchedulerRule();
    @Rule
    public final InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    private AuthenticationViewModel authenticationViewModel;
    @Mock
    private AuthenticateWebInteractor authenticateWebInteractor;

    @Before
    public void setUp() {
        authenticationViewModel = new AuthenticationViewModel(authenticateWebInteractor);
    }

    @Test
    public void authenticateClientSuccess() {
        String email = "ivanov@gmail.com";
        String password = "workhardplayhard";

        when(authenticateWebInteractor.authenticateClient(email, password)).thenReturn(Completable.complete());

        authenticationViewModel.authenticateClient(email, password);

        assertThat(authenticationViewModel.getSuccessSingleLiveEvent().getValue(), is(R.string.authentication_success_message));

        verify(authenticateWebInteractor).authenticateClient(email, password);
    }

    @Test
    public void authenticateClientErrorBuild() {
        String email = "mail";
        String password = "word";
        BuildException buildException = new BuildException(R.string.test_build_instance_exception);

        when(authenticateWebInteractor.authenticateClient(email, password)).thenReturn(Completable.error(buildException));

        authenticationViewModel.authenticateClient(email, password);

        assertThat(authenticationViewModel.getErrorSingleLiveEvent().getValue(), is(R.string.test_build_instance_exception));

        verify(authenticateWebInteractor).authenticateClient(email, password);
    }

    @Test
    public void authenticateClientErrorAuthenticate() {
        String email = "ivanov@gmail.com";
        String password = "workhardplayhard";
        AuthenticateException authenticateException = new AuthenticateException(R.string.authenticate_exception, new Exception());

        when(authenticateWebInteractor.authenticateClient(email, password)).thenReturn(Completable.error(authenticateException));

        authenticationViewModel.authenticateClient(email, password);

        assertThat(authenticationViewModel.getErrorSingleLiveEvent().getValue(), is(R.string.authenticate_exception));

        verify(authenticateWebInteractor).authenticateClient(email, password);
    }
}