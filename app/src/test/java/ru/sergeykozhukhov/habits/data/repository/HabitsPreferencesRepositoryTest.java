package ru.sergeykozhukhov.habits.data.repository;

import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import ru.sergeykozhukhov.habits.data.converter.JwtConverter;
import ru.sergeykozhukhov.habits.data.preferences.HabitsPreferences;
import ru.sergeykozhukhov.habits.model.data.JwtData;
import ru.sergeykozhukhov.habits.model.domain.Jwt;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Unit тесты на {@link HabitsPreferencesRepository}
 **/
@RunWith(MockitoJUnitRunner.class)
public class HabitsPreferencesRepositoryTest {

    private HabitsPreferencesRepository habitsPreferencesRepository;

    @Mock
    private HabitsPreferences habitsPreferences;
    @Mock
    private JwtConverter jwtConverter;

    @Before
    public void setUp() {
        habitsPreferencesRepository = new HabitsPreferencesRepository(habitsPreferences, jwtConverter);
    }

    @Test
    public void saveJwt() {
        Jwt jwt = new Jwt("token");
        SharedPreferences sharedPreferences = mock(SharedPreferences.class);
        SharedPreferences.Editor sharedPreferencesEditor = mock(SharedPreferences.Editor.class);

        when(habitsPreferences.getSharedPreferences()).thenReturn(sharedPreferences);
        when(sharedPreferences.edit()).thenReturn(sharedPreferencesEditor);

        habitsPreferencesRepository.saveJwt(jwt);

        InOrder inOrder = Mockito.inOrder(sharedPreferencesEditor);

        inOrder.verify(sharedPreferencesEditor).putString(HabitsPreferences.JWT_PREFERENCES, jwt.getJwt());
        inOrder.verify(sharedPreferencesEditor).apply();
    }

    @Test
    public void loadJwt() {
        JwtData jwtData = new JwtData("token");
        Jwt jwtExpected = new Jwt("token");
        SharedPreferences sharedPreferences = mock(SharedPreferences.class);

        when(habitsPreferences.getSharedPreferences()).thenReturn(sharedPreferences);
        when(sharedPreferences.getString(HabitsPreferences.JWT_PREFERENCES, "")).thenReturn(jwtData.getJwt());
        when(jwtConverter.convertTo(jwtData)).thenReturn(jwtExpected);

        Jwt jwtDataOutput = habitsPreferencesRepository.loadJwt();

        assertThat(jwtDataOutput, is(jwtExpected));

        InOrder inOrder = Mockito.inOrder(sharedPreferences, jwtConverter);

        inOrder.verify(sharedPreferences).getString(HabitsPreferences.JWT_PREFERENCES, "");
        inOrder.verify(jwtConverter).convertTo(jwtData);
    }

    @Test
    public void loadJwtNull() {
        SharedPreferences sharedPreferences = mock(SharedPreferences.class);

        when(habitsPreferences.getSharedPreferences()).thenReturn(sharedPreferences);
        when(sharedPreferences.getString(HabitsPreferences.JWT_PREFERENCES, "")).thenReturn("");

        Jwt jwtDataOutput = habitsPreferencesRepository.loadJwt();

        assertNull(jwtDataOutput);

        verify(sharedPreferences).getString(HabitsPreferences.JWT_PREFERENCES, "");
    }

    @Test
    public void deleteJwt() {
        SharedPreferences sharedPreferences = mock(SharedPreferences.class);
        SharedPreferences.Editor sharedPreferencesEditor = mock(SharedPreferences.Editor.class);

        when(habitsPreferences.getSharedPreferences()).thenReturn(sharedPreferences);
        when(sharedPreferences.edit()).thenReturn(sharedPreferencesEditor);

        habitsPreferencesRepository.deleteJwt();

        InOrder inOrder = Mockito.inOrder(sharedPreferencesEditor);

        inOrder.verify(sharedPreferencesEditor).remove(HabitsPreferences.JWT_PREFERENCES);
        inOrder.verify(sharedPreferencesEditor).apply();
    }
}