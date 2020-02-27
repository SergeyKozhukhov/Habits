package ru.sergeykozhukhov.habits.domain.usecaseimpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import ru.sergeykozhukhov.habits.GeneratorData;
import ru.sergeykozhukhov.habits.domain.IHabitsDatabaseRepository;

@RunWith(MockitoJUnitRunner.class)
public class ChangeProgressListDbInteractorTest {

    private GeneratorData generatorData;

    private ChangeProgressListDbInteractor changeProgressListDbInteractor;

    @Mock
    private IHabitsDatabaseRepository habitsDatabaseRepository;

    @Before
    public void setUp() {
        changeProgressListDbInteractor = new ChangeProgressListDbInteractor(habitsDatabaseRepository);

        generatorData = new GeneratorData();
    }

    @Test
    public void getProgressList() {

    }

    @Test
    public void addNewDate() {

    }

    @Test
    public void deleteDate() {
    }

    @Test
    public void saveProgressList() {
    }
}