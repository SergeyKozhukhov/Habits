package ru.sergeykozhukhov.habits.presentation.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habits.domain.SingleLiveEvent;
import ru.sergeykozhukhov.habits.domain.usecase.DeleteAllHabitsDbInteractor;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SettingsViewModelTest {

    @Rule
    public final TestSchedulerRule testSchedulerRule = new TestSchedulerRule();

    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    private SettingsViewModel settingsViewModel;

    @Mock
    private DeleteAllHabitsDbInteractor deleteAllHabitsInteractor;
    //@Mock
    //private CompositeDisposable compositeDisposable;
    @Mock
    private SingleLiveEvent<Integer> successSingleLiveEvent;
    @Mock
    private SingleLiveEvent<Integer> errorSingleLiveEvent;

    @Before
    public void setUp() {
        settingsViewModel = new SettingsViewModel(deleteAllHabitsInteractor);
    }

    @Test
    public void deleteAllHabitsSuccess() {
        TestObserver testObserver = new TestObserver();

        Completable completable = Completable.complete();
        when(deleteAllHabitsInteractor.deleteAllHabits()).thenReturn(completable);

        deleteAllHabitsInteractor.deleteAllHabits()
                .subscribeOn(Schedulers.newThread())
                .subscribe(testObserver);

        /*when(deleteAllHabitsInteractor.deleteAllHabits()
                .subscribeOn(Schedulers.newThread())
                .subscribe(testObserver)).thenReturn(completable);*/

        //testObserver.assertError(Exception.class);

        //settingsViewModel.deleteAllHabits();

        settingsViewModel.deleteAllHabits();
    }
}