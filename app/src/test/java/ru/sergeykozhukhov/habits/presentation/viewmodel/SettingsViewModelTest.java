package ru.sergeykozhukhov.habits.presentation.viewmodel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;

import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import ru.sergeykozhukhov.habits.domain.SingleLiveEvent;
import ru.sergeykozhukhov.habits.domain.usecase.DeleteAllHabitsDbInteractor;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class SettingsViewModelTest {

    private SettingsViewModel settingsViewModel;

    @Mock
    private DeleteAllHabitsDbInteractor deleteAllHabitsInteractor;
    @Mock
    private CompositeDisposable compositeDisposable;
    @Mock
    private SingleLiveEvent<Integer> successSingleLiveEvent;
    @Mock
    private SingleLiveEvent<Integer> errorSingleLiveEvent;

    @Before
    public void setUp() {
        settingsViewModel = new SettingsViewModel(deleteAllHabitsInteractor);
    }

    @Test
    public void deleteAllHabits() {
        Completable completable = Completable.complete();
        //when(compositeDisposable.add(completable));
        //when(deleteAllHabitsInteractor.deleteAllHabits()).thenReturn(completable);

        //settingsViewModel.deleteAllHabits();
    }
}