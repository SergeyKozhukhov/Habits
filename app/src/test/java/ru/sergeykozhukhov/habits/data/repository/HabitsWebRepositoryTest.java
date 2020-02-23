package ru.sergeykozhukhov.habits.data.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.FieldSetter;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;
import ru.sergeykozhukhov.habits.data.converter.ConfidentialityConverter;
import ru.sergeykozhukhov.habits.data.converter.HabitWithProgressesListConverter;
import ru.sergeykozhukhov.habits.data.converter.JwtConverter;
import ru.sergeykozhukhov.habits.data.converter.RegistrationConverter;
import ru.sergeykozhukhov.habits.data.retrofit.HabitsRetrofitClient;
import ru.sergeykozhukhov.habits.data.retrofit.IHabitsService;
import ru.sergeykozhukhov.habits.model.data.ConfidentialityData;
import ru.sergeykozhukhov.habits.model.data.HabitWithProgressesData;
import ru.sergeykozhukhov.habits.model.data.JwtData;
import ru.sergeykozhukhov.habits.model.data.RegistrationData;
import ru.sergeykozhukhov.habits.model.domain.Confidentiality;
import ru.sergeykozhukhov.habits.model.domain.HabitWithProgresses;
import ru.sergeykozhukhov.habits.model.domain.Jwt;
import ru.sergeykozhukhov.habits.model.domain.Registration;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;



// Полезная ссылка:
// https://android.jlelse.eu/complete-example-of-testing-mvp-architecture-with-kotlin-and-rxjava-part-1-816e22e71ff4

@RunWith(MockitoJUnitRunner.class)
public class HabitsWebRepositoryTest {

    private GeneratorData generatorData;

    private HabitsWebRepository habitsWebRepository;

    @Mock
    private HabitsRetrofitClient habitsRetrofitClient;
    @Mock
    private IHabitsService habitsService;
    @Mock
    private RegistrationConverter registrationConverter;
    @Mock
    private ConfidentialityConverter confidentialityConverter;
    @Mock
    private HabitWithProgressesListConverter habitWithProgressesListConverter;
    @Mock
    private JwtConverter jwtConverter;

    @Before
    public void setUp() throws Exception {

        habitsWebRepository = new HabitsWebRepository(
                habitsRetrofitClient,
                habitsService,
                registrationConverter,
                confidentialityConverter,
                habitWithProgressesListConverter,
                jwtConverter);

        generatorData = new GeneratorData();
    }

    @Test
    public void registerClientSuccess() {

        Registration registration = new Registration("firsname", "lastname", "email", "password");
        RegistrationData registrationData = new RegistrationData("firsname", "lastname", "email", "password");

        Completable completable = Completable.complete();

        when(registrationConverter.convertFrom(registration)).thenReturn(registrationData);
        when(habitsService.registerClient(registrationData)).thenReturn(completable);

        habitsWebRepository.registerClient(registration)
                .test()
                .assertNoErrors()
                .assertComplete();

        verify(registrationConverter).convertFrom(registration);
        verify(habitsService).registerClient(registrationData);
        verifyNoMoreInteractions(habitsService);
    }

    @Test
    public void registerClientError() {

        Registration registration = new Registration("firsname", "lastname", "email", "password");
        RegistrationData registrationData = new RegistrationData("firsname", "lastname", "email", "password");

        ResponseBody errorBody = mock(ResponseBody.class);
        HttpException exception = new HttpException(Response.error(401, errorBody));
        Completable completable = Completable.error(exception);

        when(registrationConverter.convertFrom(registration)).thenReturn(registrationData);
        when(habitsService.registerClient(registrationData)).thenReturn(completable);

        habitsWebRepository.registerClient(registration)
                .test()
                .assertError(exception);

        verify(registrationConverter).convertFrom(registration);
        verify(habitsService).registerClient(registrationData);
        verifyNoMoreInteractions(habitsService);
    }

    @Test
    public void authenticateClientSuccess() {

        Confidentiality confidentiality = new Confidentiality("email", "password");
        ConfidentialityData confidentialityData = new ConfidentialityData("email", "password");

        Jwt jwt = new Jwt("token");
        JwtData jwtData = new JwtData("token");

        Single<JwtData> jwtDataSingle = Single.just(jwtData);

        when(confidentialityConverter.convertFrom(confidentiality)).thenReturn(confidentialityData);
        when(jwtConverter.convertTo(jwtData)).thenReturn(jwt);

        when(habitsService.authenticateClient(confidentialityData)).thenReturn(jwtDataSingle);

        habitsWebRepository.authenticateClient(confidentiality)
                .test()
                .assertNoErrors()
                .assertValue(jwt);

        verify(confidentialityConverter).convertFrom(confidentiality);
        verify(habitsService).authenticateClient(confidentialityData);
        verify(jwtConverter).convertTo(jwtData);
        verifyNoMoreInteractions(habitsService);

    }

    @Test
    public void authenticateClientError() {
        Confidentiality confidentiality = new Confidentiality("email", "password");
        ConfidentialityData confidentialityData = new ConfidentialityData("email", "password");

        ResponseBody errorBody = mock(ResponseBody.class);
        HttpException exception = new HttpException(Response.error(401, errorBody));

        Single<JwtData> jwtDataSingle = Single.error(exception);

        when(confidentialityConverter.convertFrom(confidentiality)).thenReturn(confidentialityData);
        when(habitsService.authenticateClient(confidentialityData)).thenReturn(jwtDataSingle);

        habitsWebRepository.authenticateClient(confidentiality)
                .test()
                .assertError(exception);

        verify(confidentialityConverter).convertFrom(confidentiality);
        verify(habitsService).authenticateClient(confidentialityData);
        verifyNoMoreInteractions(habitsService);
    }


    @Test
    public void insertHabitWithProgressesListSuccess() {

        List<HabitWithProgresses> habitWithProgressesList = generatorData.createHabitWithProgressesList();
        List<HabitWithProgressesData> habitWithProgressesDataList = generatorData.createHabitWithProgressesDataList();

        String jwt = "token";
        Completable completable = Completable.complete();

        when(habitWithProgressesListConverter.convertFrom(habitWithProgressesList)).thenReturn(habitWithProgressesDataList);

        when(habitsService.insertHabitWithProgressesList(habitWithProgressesDataList, jwt)).thenReturn(completable);

        habitsWebRepository.insertHabitWithProgressesList(habitWithProgressesList, jwt)
                .test()
                .assertNoErrors()
                .assertComplete();

        verify(habitWithProgressesListConverter).convertFrom(habitWithProgressesList);
        verify(habitsService).insertHabitWithProgressesList(habitWithProgressesDataList, jwt);
        verifyNoMoreInteractions(habitsService);
    }

    @Test
    public void insertHabitWithProgressesListError() {

        List<HabitWithProgresses> habitWithProgressesList = generatorData.createHabitWithProgressesList();
        List<HabitWithProgressesData> habitWithProgressesDataList = generatorData.createHabitWithProgressesDataList();

        String jwt = "token";

        ResponseBody errorBody = mock(ResponseBody.class);
        HttpException exception = new HttpException(Response.error(401, errorBody));
        Completable completable = Completable.error(exception);

        when(habitWithProgressesListConverter.convertFrom(habitWithProgressesList)).thenReturn(habitWithProgressesDataList);

        when(habitsService.insertHabitWithProgressesList(habitWithProgressesDataList, jwt)).thenReturn(completable);

        habitsWebRepository.insertHabitWithProgressesList(habitWithProgressesList, jwt)
                .test()
                .assertError(exception);

        verify(habitWithProgressesListConverter).convertFrom(habitWithProgressesList);
        verify(habitsService).insertHabitWithProgressesList(habitWithProgressesDataList, jwt);
        verifyNoMoreInteractions(habitsService);
    }

    @Test
    public void loadHabitWithProgressesListSuccess() {

        List<HabitWithProgresses> habitWithProgressesList = generatorData.createHabitWithProgressesList();
        List<HabitWithProgressesData> habitWithProgressesDataList = generatorData.createHabitWithProgressesDataList();

        String jwt = "token";

        Single<List<HabitWithProgressesData>> single = Single.just(habitWithProgressesDataList);

        when(habitWithProgressesListConverter.convertTo(habitWithProgressesDataList)).thenReturn(habitWithProgressesList);
        when(habitsService.loadHabitWithProgressesList(jwt)).thenReturn(single);

        habitsWebRepository.loadHabitWithProgressesList(jwt)
                .test()
                .assertNoErrors()
                .assertValue(habitWithProgressesList);

        verify(habitWithProgressesListConverter).convertTo(habitWithProgressesDataList);
        verify(habitsService).loadHabitWithProgressesList(jwt);
        verifyNoMoreInteractions(habitsService);
    }

    @Test
    public void loadHabitWithProgressesListError() {

        String jwt = "token";

        ResponseBody errorBody = mock(ResponseBody.class);
        HttpException exception = new HttpException(Response.error(401, errorBody));
        Single<List<HabitWithProgressesData>> single = Single.error(exception);

        when(habitsService.loadHabitWithProgressesList(jwt)).thenReturn(single);

        habitsWebRepository.loadHabitWithProgressesList(jwt)
                .test()
                .assertError(exception);

        verify(habitsService).loadHabitWithProgressesList(jwt);
        verifyNoMoreInteractions(habitsService);
    }

    @Test
    public void setJwt() {
        Jwt jwt = new Jwt("token");
        JwtData jwtData = new JwtData("token");

        when(jwtConverter.convertFrom(jwt)).thenReturn(jwtData);
        habitsWebRepository.setJwt(jwt);

        verify(habitsRetrofitClient).setJwtData(jwtData);
        verifyNoMoreInteractions(habitsRetrofitClient);
    }

    @Test
    public void getJwt() {
        Jwt jwt = new Jwt("token");
        JwtData jwtData = new JwtData("token");

        when(habitsRetrofitClient.getJwtData()).thenReturn(jwtData);
        when(jwtConverter.convertTo(jwtData)).thenReturn(jwt);
        habitsWebRepository.getJwt();

        verify(habitsRetrofitClient).getJwtData();
        verifyNoMoreInteractions(habitsRetrofitClient);
    }

    @Test
    public void deleteJwt() {
        habitsWebRepository.deleteJwt();
        verify(habitsRetrofitClient).clearJwtData();
        verifyNoMoreInteractions(habitsRetrofitClient);

    }
}