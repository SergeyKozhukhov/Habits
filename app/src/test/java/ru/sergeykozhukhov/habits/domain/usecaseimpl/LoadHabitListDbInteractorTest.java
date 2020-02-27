package ru.sergeykozhukhov.habits.domain.usecaseimpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Flowable;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.GeneratorData;
import ru.sergeykozhukhov.habits.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.model.domain.Habit;
import ru.sergeykozhukhov.habits.model.domain.exception.LoadDbException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoadHabitListDbInteractorTest {

    private GeneratorData generatorData;

    private LoadHabitListDbInteractor loadHabitListDbInteractor;

    @Mock
    private IHabitsDatabaseRepository habitsDatabaseRepository;

    @Before
    public void setUp() {
        loadHabitListDbInteractor = new LoadHabitListDbInteractor(habitsDatabaseRepository);
        generatorData = new GeneratorData();
    }

    @Test
    public void loadHabitListSuccess() {
        List<Habit> habitList = generatorData.createHabitList(0,10);
        Flowable<List<Habit>> flowable = Flowable.just(habitList);

        when(habitsDatabaseRepository.loadHabitList()).thenReturn(flowable);

        loadHabitListDbInteractor.loadHabitList()
                .test()
                .assertNoErrors()
                .assertValue(habitList);

        verify(habitsDatabaseRepository).loadHabitList();
        verifyNoMoreInteractions(habitsDatabaseRepository);
    }

    @Test
    public void loadHabitListError() {
        List<Habit> habitList = generatorData.createHabitList(0,10);
        Flowable<List<Habit>> flowable = Flowable.just(habitList);

        String msg = "error";
        Exception exception = new Exception(msg);
        LoadDbException loadDbException = new LoadDbException(R.string.load_db_exception, exception);

        when(habitsDatabaseRepository.loadHabitList()).thenReturn(Flowable.error(loadDbException));

        loadHabitListDbInteractor.loadHabitList()
                .test()
                .assertError(loadDbException)
                .assertNotComplete()
                .dispose();

        verify(habitsDatabaseRepository).loadHabitList();
        verifyNoMoreInteractions(habitsDatabaseRepository);
    }

}