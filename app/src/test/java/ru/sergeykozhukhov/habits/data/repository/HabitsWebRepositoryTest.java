package ru.sergeykozhukhov.habits.data.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.FieldSetter;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;
import retrofit2.Response;
import ru.sergeykozhukhov.habits.data.converter.ConfidentialityConverter;
import ru.sergeykozhukhov.habits.data.converter.HabitWithProgressesListConverter;
import ru.sergeykozhukhov.habits.data.converter.JwtConverter;
import ru.sergeykozhukhov.habits.data.converter.RegistrationConverter;
import ru.sergeykozhukhov.habits.data.retrofit.HabitsRetrofitClient;
import ru.sergeykozhukhov.habits.data.retrofit.IHabitsService;
import ru.sergeykozhukhov.habits.model.data.ConfidentialityData;
import ru.sergeykozhukhov.habits.model.data.JwtData;
import ru.sergeykozhukhov.habits.model.domain.Confidentiality;
import ru.sergeykozhukhov.habits.model.domain.Jwt;

import static org.mockito.Mockito.when;



// Полезная ссылка:
// https://android.jlelse.eu/complete-example-of-testing-mvp-architecture-with-kotlin-and-rxjava-part-1-816e22e71ff4

@RunWith(MockitoJUnitRunner.class)
public class HabitsWebRepositoryTest {

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
    }

    @Test
    public void authenticateClientSuccess() {

        // arrange

        Confidentiality confidentiality = new Confidentiality("email", "password");
        ConfidentialityData confidentialityData = new ConfidentialityData("email", "password");

        Jwt jwt = new Jwt("token");
        JwtData jwtData = new JwtData("token");

        Single<Jwt> jwtSingle = Single.just(jwt);
        Single<JwtData> jwtDataSingle = Single.just(jwtData);


        when(confidentialityConverter.convertFrom(confidentiality)).thenReturn(confidentialityData);
        when(jwtConverter.convertFrom(jwt)).thenReturn(jwtData);
        when(jwtConverter.convertTo(jwtData)).thenReturn(jwt);

        when(habitsService.authenticateClient(confidentialityData)).thenReturn(jwtDataSingle);
        when(habitsWebRepository.authenticateClient(confidentiality)).thenReturn(jwtSingle); // не проходит
        //when(habitsWebRepository.authenticateClient(confidentiality)).thenReturn(jwtSingle.delay(2, TimeUnit.SECONDS)); // проходит

        // act

        TestObserver<Jwt> testObserver = habitsWebRepository.authenticateClient(confidentiality).test();

        // assert

        testObserver.assertNoErrors();
        testObserver.assertValue(jwt);


    }




}