package ru.sergeykozhukhov.habits.domain.usecaseimpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import ru.sergeykozhukhov.habits.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.model.domain.Jwt;
import ru.sergeykozhukhov.habits.model.domain.exception.GetJwtException;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetJwtValueInteractorTest {

    private GetJwtValueInteractor getJwtValueInteractor;

    @Mock
    private IHabitsWebRepository habitsWebRepository;
    @Mock
    private IHabitsPreferencesRepository habitsPreferencesRepository;

    @Before
    public void setUp() {
        getJwtValueInteractor = new GetJwtValueInteractor(habitsWebRepository, habitsPreferencesRepository);
    }

    @Test
    public void getValueFromWebRepositorySuccess() throws GetJwtException {

        Jwt jwt = new Jwt("token");
        when(habitsWebRepository.getJwt()).thenReturn(jwt);

        getJwtValueInteractor.getValue();

        verify(habitsWebRepository).getJwt();
        verifyNoMoreInteractions(habitsWebRepository);
    }
    @Test
    public void getValueSuccessFromPreferencesRepository() throws GetJwtException {

        String jwtStringExpected = "token";
        Jwt jwt = new Jwt(jwtStringExpected);

        when(habitsWebRepository.getJwt()).thenThrow(new NullPointerException());
        when(habitsPreferencesRepository.loadJwt()).thenReturn(jwt);

        String jwtStringOutput = getJwtValueInteractor.getValue();

        assertThat(jwtStringOutput, is(jwtStringExpected));

        InOrder inOrder = Mockito.inOrder(habitsWebRepository, habitsPreferencesRepository);

        inOrder.verify(habitsWebRepository).getJwt();
        inOrder.verify(habitsPreferencesRepository).loadJwt();
        inOrder.verify(habitsWebRepository).setJwt(jwt);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void getValueError(){

        when(habitsWebRepository.getJwt()).thenThrow(new NullPointerException());
        when(habitsPreferencesRepository.loadJwt()).thenReturn(null);

        try {
            getJwtValueInteractor.getValue();
        } catch (GetJwtException e) {
            assertThat(e, isA(GetJwtException.class));
        }

        InOrder inOrder = Mockito.inOrder(habitsWebRepository, habitsPreferencesRepository);

        inOrder.verify(habitsWebRepository).getJwt();
        inOrder.verify(habitsPreferencesRepository).loadJwt();
        inOrder.verifyNoMoreInteractions();
    }

}