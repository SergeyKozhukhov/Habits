package ru.sergeykozhukhov.habits.domain.usecase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import ru.sergeykozhukhov.habits.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.domain.IHabitsWebRepository;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class DeleteJwtInteractorTest {

    private DeleteJwtInteractor deleteJwtInteractor;

    @Mock
    private IHabitsWebRepository habitsWebRepository;
    @Mock
    private IHabitsPreferencesRepository habitsPreferencesRepository;

    @Before
    public void setUp() {
        deleteJwtInteractor = new DeleteJwtInteractor(habitsWebRepository, habitsPreferencesRepository);
    }

    @Test
    public void deleteJwt() {

        deleteJwtInteractor.deleteJwt();

        verify(habitsPreferencesRepository).deleteJwt();
        verify(habitsWebRepository).deleteJwt();
        verifyNoMoreInteractions(habitsWebRepository, habitsPreferencesRepository);

    }
}