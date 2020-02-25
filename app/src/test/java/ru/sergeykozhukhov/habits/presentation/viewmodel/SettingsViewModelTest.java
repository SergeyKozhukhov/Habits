package ru.sergeykozhukhov.habits.presentation.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Completable;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.RxImmediateSchedulerRule;
import ru.sergeykozhukhov.habits.domain.usecase.DeleteAllHabitsDbInteractor;
import ru.sergeykozhukhov.habits.model.domain.exception.DeleteFromDbException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SettingsViewModelTest {

    @Rule
    public final RxImmediateSchedulerRule rxImmediateSchedulerRule = new RxImmediateSchedulerRule();
    @Rule
    public final InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    private SettingsViewModel settingsViewModel;

    @Mock
    private DeleteAllHabitsDbInteractor deleteAllHabitsInteractor;

    @Before
    public void setUp() {
        settingsViewModel = new SettingsViewModel(deleteAllHabitsInteractor);
    }

    @Test
    public void deleteAllHabitsSuccess() {

        when(deleteAllHabitsInteractor.deleteAllHabits()).thenReturn(Completable.complete());

        settingsViewModel.deleteAllHabits();

        assertThat(settingsViewModel.getSuccessSingleLiveEvent().getValue(), is(R.string.delete_from_db_success_message));
        verify(deleteAllHabitsInteractor).deleteAllHabits();
    }

    @Test
    public void deleteAllHabitsError() {

        DeleteFromDbException deleteFromDbException = new DeleteFromDbException(R.string.delete_from_db_exception, new Exception());
        when(deleteAllHabitsInteractor.deleteAllHabits()).thenReturn(Completable.error(deleteFromDbException));

        settingsViewModel.deleteAllHabits();

        assertThat(settingsViewModel.getErrorSingleLiveEvent().getValue(), is(R.string.delete_from_db_exception));
        verify(deleteAllHabitsInteractor).deleteAllHabits();
    }

    //TestObserver testObserver = new TestObserver();

        /*deleteAllHabitsInteractor.deleteAllHabits()
                .subscribeOn(Schedulers.newThread())
                .subscribe();
*/

    //testObserver.assertComplete();

        /*when(deleteAllHabitsInteractor.deleteAllHabits()
                .subscribeOn(Schedulers.newThread())
                .subscribe(testObserver)).thenReturn(completable);*/

    //testObserver.assertError(Exception.class);

    //settingsViewModel.deleteAllHabits();

    //Log.d("gg", ""+successSingleLiveEvent.getValue());

    //Assert.assertEquals(successSingleLiveEvent.getValue(), java.util.Optional.of(R.string.delete_from_db_success_message));

    //Assert.assertEquals((long)successSingleLiveEvent.getValue(), (long)R.string.delete_from_db_exception);
}