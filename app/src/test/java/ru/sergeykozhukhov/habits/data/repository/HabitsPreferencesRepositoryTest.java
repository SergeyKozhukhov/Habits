package ru.sergeykozhukhov.habits.data.repository;

import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import ru.sergeykozhukhov.habits.data.converter.JwtConverter;
import ru.sergeykozhukhov.habits.data.preferences.HabitsPreferences;
import ru.sergeykozhukhov.habits.model.domain.Jwt;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

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
        /*Jwt jwt = new Jwt("token");
        String jwt_preferences = "JWT_PREFERENCES";
        habitsPreferencesRepository.saveJwt(jwt);

        verify(habitsPreferences).getSharedPreferences().edit();*/
        //verifyNoMoreInteractions(habitsService);

        /*habitsPreferences.getSharedPreferences().edit().
                putString(HabitsPreferences.JWT_PREFERENCES, jwt.getJwt()).
                apply();*/
    }

    @Test
    public void loadJwt() {
    }

    @Test
    public void deleteJwt() {
    }
}