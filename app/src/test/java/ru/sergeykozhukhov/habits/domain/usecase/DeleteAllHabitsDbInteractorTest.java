package ru.sergeykozhukhov.habits.domain.usecase;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Completable;
import io.reactivex.functions.Predicate;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.data.repository.HabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.model.exception.DeleteFromDbException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeleteAllHabitsDbInteractorTest {

    private DeleteAllHabitsDbInteractor deleteAllHabitsDbInteractor;

    @Mock
    private HabitsDatabaseRepository habitsDatabaseRepository;

    @Before
    public void setUp() throws Exception {
        deleteAllHabitsDbInteractor = new DeleteAllHabitsDbInteractor(habitsDatabaseRepository);
    }

    @Test
    public void deleteAllHabitsSuccess() {
        Completable completable = Completable.complete();
        when(habitsDatabaseRepository.deleteAllHabits()).thenReturn(completable);

        deleteAllHabitsDbInteractor.deleteAllHabits()
                .test()
                .assertNoErrors()
                .assertComplete();

        verify(habitsDatabaseRepository).deleteAllHabits();
        verifyNoMoreInteractions(habitsDatabaseRepository);
    }

    @Test
    public void deleteAllHabitsError() {
        String msg = "error";
        Exception exception = new Exception(msg);
        Completable completable = Completable.error(exception);

        when(habitsDatabaseRepository.deleteAllHabits()).thenReturn(completable);

        DeleteFromDbException deleteFromDbException = new DeleteFromDbException(R.string.delete_from_db_exception, exception);

        deleteAllHabitsDbInteractor.deleteAllHabits()
                .test()
                .assertError(deleteFromDbException)
                .assertNotComplete();

        verify(habitsDatabaseRepository).deleteAllHabits();
        verifyNoMoreInteractions(habitsDatabaseRepository);
    }

}