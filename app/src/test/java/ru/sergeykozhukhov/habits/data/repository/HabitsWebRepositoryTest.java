package ru.sergeykozhukhov.habits.data.repository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.FieldSetter;

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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



// https://android.jlelse.eu/complete-example-of-testing-mvp-architecture-with-kotlin-and-rxjava-part-1-816e22e71ff4
public class HabitsWebRepositoryTest {

    private HabitsWebRepository habitsWebRepository;

    private HabitsRetrofitClient habitsRetrofitClient;
    private IHabitsService habitsService;

    private RegistrationConverter registrationConverter;
    private ConfidentialityConverter confidentialityConverter;
    private HabitWithProgressesListConverter habitWithProgressesListConverter;
    private JwtConverter jwtConverter;

    @Before
    public void setUp() throws Exception {

        habitsRetrofitClient = mock(HabitsRetrofitClient.class);
        habitsService = mock(IHabitsService.class);

        registrationConverter = mock(RegistrationConverter.class);
        confidentialityConverter = mock(ConfidentialityConverter.class);
        habitWithProgressesListConverter = mock(HabitWithProgressesListConverter.class);
        jwtConverter = mock(JwtConverter.class);

        habitsWebRepository = new HabitsWebRepository(
                habitsRetrofitClient,
                registrationConverter,
                confidentialityConverter,
                habitWithProgressesListConverter,
                jwtConverter);

        FieldSetter.setField(habitsWebRepository, HabitsWebRepository.class.getDeclaredField("habitsService"), habitsService);
    }

    @Test
    public void authenticateClientSuccess() {

        // arrange

        Confidentiality confidentiality = mock(Confidentiality.class);
        ConfidentialityData confidentialityData = mock(ConfidentialityData.class);

        Jwt jwt = mock(Jwt.class);
        JwtData jwtData = mock(JwtData.class);


        Single<Jwt> jwtSingle = Single.just(jwt);
        Single<JwtData> jwtDataSingle = Single.just(jwtData);


        //when(jwt.getJwt()).thenReturn("jwt");
        //when(jwtData.getJwt()).thenReturn("jwt");

        //when(confidentiality.getEmail()).thenReturn("email");
        //when(confidentiality.getPassword()).thenReturn("password");

        when(confidentialityConverter.convertFrom(confidentiality)).thenReturn(confidentialityData);
        when(jwtConverter.convertFrom(jwt)).thenReturn(jwtData);
        when(jwtConverter.convertTo(jwtData)).thenReturn(jwt);

        when(habitsService.authenticateClient(confidentialityData)).thenReturn(jwtDataSingle);
        when(habitsWebRepository.authenticateClient(confidentiality)).thenReturn(jwtSingle.delay(2, TimeUnit.SECONDS));

        // act

        TestObserver<Jwt> testObserver = habitsWebRepository.authenticateClient(confidentiality).test();

        // assert

        testObserver.assertNoErrors();
        testObserver.assertValue(jwt);


    }




}