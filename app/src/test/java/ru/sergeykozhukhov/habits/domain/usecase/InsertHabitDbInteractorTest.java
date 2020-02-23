package ru.sergeykozhukhov.habits.domain.usecase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

import io.reactivex.Single;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.data.repository.HabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.model.domain.Habit;
import ru.sergeykozhukhov.habits.model.exception.BuildException;
import ru.sergeykozhukhov.habits.model.exception.InsertDbException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InsertHabitDbInteractorTest {

    private InsertHabitDbInteractor insertHabitDbInteractor;

    @Mock
    private HabitsDatabaseRepository habitsDatabaseRepository;
    @Mock
    private BuildHabitInstace buildHabitInstace;

    @Before
    public void setUp() {
        insertHabitDbInteractor = new InsertHabitDbInteractor(habitsDatabaseRepository, buildHabitInstace);
    }

    @Test
    public void insertHabitSuccess() throws BuildException {
        String title = "title";
        String description = "description";
        String startDate = "01-01-2000";
        String duration = "21";

        long idHabit = 1L;

        Habit habit = new Habit(idHabit, 0, "title", "description", new Date(946684800), 21);

        Single<Long> single = Single.just(idHabit);

        when(buildHabitInstace.build(title, description, startDate, duration)).thenReturn(habit);
        when(habitsDatabaseRepository.insertHabit(habit)).thenReturn(single);

        insertHabitDbInteractor.insertHabit(title, description, startDate, duration)
                .test()
                .assertNoErrors()
                .assertValue(idHabit);

        verify(buildHabitInstace).build(title, description, startDate, duration);
        verify(habitsDatabaseRepository).insertHabit(habit);
        verifyNoMoreInteractions(habitsDatabaseRepository);
    }

    @Test
    public void insertHabitErrorBuild() throws BuildException {
        String title = "title";
        String description = "description";
        String startDate = "01-01-2000";
        String duration = "-10";

        BuildException buildException = new BuildException(R.string.null_data_build_instance_exception);

        when(buildHabitInstace.build(title, description, startDate, duration)).thenThrow(buildException);

        insertHabitDbInteractor.insertHabit(title, description, startDate, duration)
                .test()
                .assertError(buildException);

        verify(buildHabitInstace).build(title, description, startDate, duration);
        verifyNoMoreInteractions(habitsDatabaseRepository);
    }

    @Test
    public void insertHabitErrorInsert() throws BuildException {
        String title = "title";
        String description = "description";
        String startDate = "01-01-2000";
        String duration = "21";

        long idHabit = 1L;

        Habit habit = new Habit(idHabit, 0, "title", "description", new Date(946684800), 21);

        String msg = "error";
        Exception exception = new Exception(msg);
        InsertDbException insertDbException = new InsertDbException(R.string.insert_db_exception, exception);

        Single<Long> single = Single.error(insertDbException);

        when(buildHabitInstace.build(title, description, startDate, duration)).thenReturn(habit);
        when(habitsDatabaseRepository.insertHabit(habit)).thenReturn(single);

        insertHabitDbInteractor.insertHabit(title, description, startDate, duration)
                .test()
                .assertError(insertDbException);

        verify(buildHabitInstace).build(title, description, startDate, duration);
        verify(habitsDatabaseRepository).insertHabit(habit);
        verifyNoMoreInteractions(habitsDatabaseRepository);
    }


}