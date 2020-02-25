package ru.sergeykozhukhov.habits.presentation.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Single;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.RxImmediateSchedulerRule;
import ru.sergeykozhukhov.habits.domain.usecase.InsertHabitDbInteractor;
import ru.sergeykozhukhov.habits.model.domain.exception.BuildException;
import ru.sergeykozhukhov.habits.model.domain.exception.InsertDbException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddHabitViewModelTest {

    @Rule
    public final RxImmediateSchedulerRule rxImmediateSchedulerRule = new RxImmediateSchedulerRule();
    @Rule
    public final InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    private AddHabitViewModel addHabitViewModel;

    @Mock
    private InsertHabitDbInteractor insertHabitDbInteractor;

    @Before
    public void setUp() throws Exception {
        addHabitViewModel = new AddHabitViewModel(insertHabitDbInteractor);
    }

    @Test
    public void insertHabitSuccess() {
        String title = "title";
        String description = "description";
        String startDate = "01-01-2000";
        String duration = "21";

        when(insertHabitDbInteractor.insertHabit(title, description, startDate, duration)).thenReturn(Single.just(1L));

        addHabitViewModel.insertHabit(title, description, startDate, duration);

        assertThat(addHabitViewModel.getInsertedSuccessSingleLiveEvent().getValue(), is(R.string.habit_success_inserted_db_message));
        verify(insertHabitDbInteractor).insertHabit(title, description, startDate, duration);
    }

    @Test
    public void insertHabitErrorBuild() {
        String title = "qwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjkl";
        String description = "descr";
        String startDate = "1 день 2000 года";
        String duration = "-10";

        BuildException buildException = new BuildException(R.string.test_build_instance_exception);

        when(insertHabitDbInteractor.insertHabit(title, description, startDate, duration)).thenReturn(Single.error(buildException));

        addHabitViewModel.insertHabit(title, description, startDate, duration);

        assertThat(addHabitViewModel.getErrorSingleLiveEvent().getValue(), is(R.string.test_build_instance_exception));
        verify(insertHabitDbInteractor).insertHabit(title, description, startDate, duration);
    }

    @Test
    public void insertHabitErrorInsert() {
        String title = "title";
        String description = "description";
        String startDate = "01-01-2000";
        String duration = "21";

        InsertDbException insertDbException = new InsertDbException(R.string.insert_db_exception, new Exception());

        when(insertHabitDbInteractor.insertHabit(title, description, startDate, duration)).thenReturn(Single.error(insertDbException));

        addHabitViewModel.insertHabit(title, description, startDate, duration);

        assertThat(addHabitViewModel.getErrorSingleLiveEvent().getValue(), is(R.string.insert_db_exception));
        verify(insertHabitDbInteractor).insertHabit(title, description, startDate, duration);
    }
}