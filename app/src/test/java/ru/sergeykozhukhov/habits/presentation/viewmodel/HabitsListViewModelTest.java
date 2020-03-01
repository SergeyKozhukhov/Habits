package ru.sergeykozhukhov.habits.presentation.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Flowable;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.GeneratorData;
import ru.sergeykozhukhov.habits.RxImmediateSchedulerRule;
import ru.sergeykozhukhov.habits.domain.usecaseimpl.LoadHabitListDbInteractor;
import ru.sergeykozhukhov.habits.model.domain.Habit;
import ru.sergeykozhukhov.habits.model.domain.exception.LoadDbException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HabitsListViewModelTest {

    @Rule
    public final RxImmediateSchedulerRule rxImmediateSchedulerRule = new RxImmediateSchedulerRule();
    @Rule
    public final InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    private GeneratorData generatorData;

    private HabitsListViewModel habitsListViewModel;

    @Mock
    private LoadHabitListDbInteractor loadHabitListDbInteractor;

    @Before
    public void setUp() {
        habitsListViewModel = new HabitsListViewModel(loadHabitListDbInteractor);
        generatorData = new GeneratorData();
    }

    @Test
    public void loadHabitListSuccess() {
        List<Habit> habitList = generatorData.createHabitList(1, 10);
        when(loadHabitListDbInteractor.loadHabitList()).thenReturn(Flowable.just(habitList));

        habitsListViewModel.loadHabitList();

        assertThat(habitsListViewModel.getHabitListLiveData().getValue(), is(habitList));
        verify(loadHabitListDbInteractor).loadHabitList();
    }

    @Test
    public void loadHabitListError() {
        LoadDbException loadDbException = new LoadDbException(R.string.load_db_exception, new Exception());
        when(loadHabitListDbInteractor.loadHabitList()).thenReturn(Flowable.error(loadDbException));

        habitsListViewModel.loadHabitList();

        assertThat(habitsListViewModel.getErrorSingleLiveEvent().getValue(), is(loadDbException.getMessageRes()));
        verify(loadHabitListDbInteractor).loadHabitList();
    }
}