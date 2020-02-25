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
import ru.sergeykozhukhov.habits.domain.usecase.RegisterWebInteractor;
import ru.sergeykozhukhov.habits.model.domain.exception.BuildException;
import ru.sergeykozhukhov.habits.model.domain.exception.RegisterException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationViewModelTest {

    @Rule
    public final RxImmediateSchedulerRule rxImmediateSchedulerRule = new RxImmediateSchedulerRule();
    @Rule
    public final InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    private RegistrationViewModel registrationViewModel;

    @Mock
    private RegisterWebInteractor registerWebInteractor;

    @Before
    public void setUp() throws Exception {
        registrationViewModel = new RegistrationViewModel(registerWebInteractor);
    }

    @Test
    public void registerClientSucccess() {

        String firstname = "firstname";
        String lastname = "lastname";
        String email = "email@gmail.com";
        String password = "password";
        String passwordConfirmation = "password";

        when(registerWebInteractor.registerClient(firstname, lastname, email, password, passwordConfirmation)).thenReturn(Completable.complete());

        registrationViewModel.registerClient(firstname, lastname, email, password, passwordConfirmation);

        assertThat(registrationViewModel.getSuccessSingleLiveEvent().getValue(), is(R.string.registration_success_message));
        verify(registerWebInteractor).registerClient(firstname, lastname, email, password, passwordConfirmation);
    }

    @Test
    public void registerClientErrorBuild() {

        String firstname = "fn";
        String lastname = "ln";
        String email = "email_qwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwerty_email@gmail.com";
        String password = "password";

        BuildException buildException = new BuildException(R.string.test_build_instance_exception);
        when(registerWebInteractor.registerClient(firstname, lastname, email, password, null)).thenReturn(Completable.error(buildException));

        registrationViewModel.registerClient(firstname, lastname, email, password, null);

        assertThat(registrationViewModel.getErrorSingleLiveEvent().getValue(), is(R.string.test_build_instance_exception));
        verify(registerWebInteractor).registerClient(firstname, lastname, email, password, null);
    }

    @Test
    public void registerClientErrorRegister() {

        String firstname = "firstname";
        String lastname = "lastname";
        String email = "email@gmail.com";
        String password = "password";

        RegisterException registerException = new RegisterException(R.string.register_exception, new Exception());
        when(registerWebInteractor.registerClient(firstname, lastname, email, password, null)).thenReturn(Completable.error(registerException));

        registrationViewModel.registerClient(firstname, lastname, email, password, null);

        assertThat(registrationViewModel.getErrorSingleLiveEvent().getValue(), is(R.string.register_exception));
        verify(registerWebInteractor).registerClient(firstname, lastname, email, password, null);
    }


}