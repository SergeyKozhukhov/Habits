package ru.sergeykozhukhov.habits.domain.usecase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.domain.IInreractor.IAuthenticateWebInteractor;
import ru.sergeykozhukhov.habits.model.domain.Confidentiality;
import ru.sergeykozhukhov.habits.model.domain.Jwt;
import ru.sergeykozhukhov.habits.model.exception.AuthenticateException;
import ru.sergeykozhukhov.habits.model.exception.BuildException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticateWebInteractorTest {

    private AuthenticateWebInteractor authenticateWebInteractor;
    @Mock
    private IHabitsWebRepository habitsWebRepository;
    @Mock
    private IHabitsPreferencesRepository habitsPreferencesRepository;
    @Mock
    private BuildConfidentialityInstance buildConfidentialityInstance;

    @Before
    public void setUp() {
        authenticateWebInteractor = new AuthenticateWebInteractor(
                habitsWebRepository,
                habitsPreferencesRepository,
                buildConfidentialityInstance);
    }

    @Test
    public void authenticateClientSuccess() throws BuildException {
        String email = "ivanov@gmail.com";
        String password = "workhardplayhard";
        Confidentiality confidentiality = new Confidentiality(email, password);

        Jwt jwt = new Jwt("token");
        Single<Jwt> jwtSingle = Single.just(jwt);

        when(buildConfidentialityInstance.build(email, password)).thenReturn(confidentiality);
        when(habitsWebRepository.authenticateClient(confidentiality)).thenReturn(jwtSingle);

        authenticateWebInteractor.authenticateClient(email, password)
                .test()
                .assertNoErrors()
                .assertComplete();

        InOrder inOrder = Mockito.inOrder(buildConfidentialityInstance, habitsWebRepository, habitsPreferencesRepository);

        inOrder.verify(buildConfidentialityInstance).build(email, password);
        inOrder.verify(habitsWebRepository).authenticateClient(confidentiality);
        inOrder.verify(habitsWebRepository).setJwt(jwtSingle.blockingGet());
        inOrder.verify(habitsPreferencesRepository).saveJwt(jwtSingle.blockingGet());
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void authenticateClientErrorBuildConfidentiality() throws BuildException {
        String email = "ivanov@gmail.com";
        String password = "workhardplayhard";

        BuildException buildException = new BuildException(R.string.null_data_build_instance_exception);

        when(buildConfidentialityInstance.build(email, password)).thenThrow(buildException);

        authenticateWebInteractor.authenticateClient(email, password)
                .test()
                .assertError(buildException);

        InOrder inOrder = Mockito.inOrder(buildConfidentialityInstance, habitsWebRepository, habitsPreferencesRepository);

        inOrder.verify(buildConfidentialityInstance).build(email, password);
        inOrder.verifyNoMoreInteractions();
    }


    @Test
    public void authenticateClientError() throws BuildException {
        String email = "ivanov@gmail.com";
        String password = "workhardplayhard";
        Confidentiality confidentiality = new Confidentiality(email, password);

        ResponseBody errorBody = mock(ResponseBody.class);
        HttpException exception = new HttpException(Response.error(401, errorBody));
        AuthenticateException authenticateException = new AuthenticateException(R.string.authenticate_exception, exception);
        Single<Jwt> jwtSingle = Single.error(authenticateException);

        when(buildConfidentialityInstance.build(email, password)).thenReturn(confidentiality);
        when(habitsWebRepository.authenticateClient(confidentiality)).thenReturn(jwtSingle);

        authenticateWebInteractor.authenticateClient(email, password)
                .test()
                .assertError(authenticateException);

        InOrder inOrder = Mockito.inOrder(buildConfidentialityInstance, habitsWebRepository, habitsPreferencesRepository);

        inOrder.verify(buildConfidentialityInstance).build(email, password);
        inOrder.verify(habitsWebRepository).authenticateClient(confidentiality);
        inOrder.verifyNoMoreInteractions();
    }


}