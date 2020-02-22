package ru.sergeykozhukhov.habits.domain.usecase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import ru.sergeykozhukhov.habits.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.domain.IHabitsWebRepository;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class GetJwtValueInteractorTest {


    private IHabitsWebRepository habitsWebRepository;
    private IHabitsPreferencesRepository habitsPreferencesRepository;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getValue() {
    }
}