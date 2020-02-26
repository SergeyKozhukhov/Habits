package ru.sergeykozhukhov.habits.domain.usecase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Completable;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.domain.iusecase.IBuildRegistrationInstance;
import ru.sergeykozhukhov.habits.model.domain.Registration;
import ru.sergeykozhukhov.habits.model.domain.exception.BuildException;
import ru.sergeykozhukhov.habits.model.domain.exception.RegisterException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegisterWebInteractorTest {

    private RegisterWebInteractor registerWebInteractor;

    @Mock
    private IHabitsWebRepository habitsWebRepository;
    @Mock
    private IBuildRegistrationInstance buildRegistrationInstance;

    @Before
    public void setUp() {
        registerWebInteractor = new RegisterWebInteractor(habitsWebRepository, buildRegistrationInstance);
    }

    @Test
    public void registerClientSuccess() throws BuildException {
        String firstname = "firstname";
        String lastname = "lastname";
        String email = "email@gmail.com";
        String password = "password";
        String passwordConfirmation = "password";

        Registration registration = new Registration(firstname, lastname, email, password);

        when(buildRegistrationInstance.build(firstname, lastname, email, password, passwordConfirmation)).thenReturn(registration);
        when(habitsWebRepository.registerClient(registration)).thenReturn(Completable.complete());

        registerWebInteractor.registerClient(firstname, lastname, email, password, passwordConfirmation)
                .test()
                .assertNoErrors()
                .assertComplete();

        InOrder inOrder = Mockito.inOrder(habitsWebRepository, buildRegistrationInstance);

        inOrder.verify(buildRegistrationInstance).build(firstname, lastname, email, password, passwordConfirmation);
        inOrder.verify(habitsWebRepository).registerClient(registration);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void registerClientErrorBuildRegistration() throws BuildException {
        String firstname = "fn";
        String lastname = "ln";
        String email = "email_qwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwerty_email@gmail.com";
        String password = "password";

        when(buildRegistrationInstance.build(firstname, lastname, email, password, null)).thenThrow(BuildException.class);

        registerWebInteractor.registerClient(firstname, lastname, email, password, null)
                .test()
                .assertError(BuildException.class);

        verify(buildRegistrationInstance).build(firstname, lastname, email, password, null);
        verifyNoMoreInteractions(habitsWebRepository, buildRegistrationInstance);
    }

    @Test
    public void registerClientError() throws BuildException {
        String firstname = "firstname";
        String lastname = "lastname";
        String email = "email@gmail.com";
        String password = "password";
        String passwordConfirmation = "password";

        Registration registration = new Registration(firstname, lastname, email, password);

        String msg = "error";
        Exception exception = new Exception(msg);
        RegisterException registerException = new RegisterException(R.string.register_exception, exception);

        when(buildRegistrationInstance.build(firstname, lastname, email, password, passwordConfirmation)).thenReturn(registration);
        when(habitsWebRepository.registerClient(registration)).thenReturn(Completable.error(exception));

        registerWebInteractor.registerClient(firstname, lastname, email, password, passwordConfirmation)
                .test()
                .assertError(registerException);

        InOrder inOrder = Mockito.inOrder(habitsWebRepository, buildRegistrationInstance);

        inOrder.verify(buildRegistrationInstance).build(firstname, lastname, email, password, passwordConfirmation);
        inOrder.verify(habitsWebRepository).registerClient(registration);
        inOrder.verifyNoMoreInteractions();
    }
}