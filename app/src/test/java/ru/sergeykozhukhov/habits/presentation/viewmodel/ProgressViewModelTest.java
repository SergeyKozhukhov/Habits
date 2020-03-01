package ru.sergeykozhukhov.habits.presentation.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.RxImmediateSchedulerRule;
import ru.sergeykozhukhov.habits.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.domain.usecaseimpl.ChangeProgressListDbInteractor;
import ru.sergeykozhukhov.habits.model.domain.exception.ChangeProgressException;
import ru.sergeykozhukhov.habits.model.domain.exception.DeleteFromDbException;
import ru.sergeykozhukhov.habits.model.domain.exception.InsertDbException;
import ru.sergeykozhukhov.habits.model.domain.exception.LoadDbException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProgressViewModelTest {

    @Rule
    public final RxImmediateSchedulerRule rxImmediateSchedulerRule = new RxImmediateSchedulerRule();
    @Rule
    public final InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    private ProgressViewModel progressViewModel;

    @Mock
    private ChangeProgressListDbInteractor changeProgressListDbInteractor;

    @Before
    public void setUp() {
        progressViewModel = new ProgressViewModel(changeProgressListDbInteractor);
    }

    @Test
    public void initChangeProgressListSuccess() {
        long idHabit = 1L;
        List<Date> dateList = Arrays.asList(new Date(0), new Date());
        when(changeProgressListDbInteractor.getProgressList(idHabit)).thenReturn(Single.just(dateList));

        progressViewModel.initChangeProgressList(idHabit);

        assertThat(progressViewModel.getDateListLoadedSingleLiveEvent().getValue(), is(dateList));
        verify(changeProgressListDbInteractor).getProgressList(idHabit);
    }

    @Test
    public void initChangeProgressListError() {
        long idHabit = 1L;
        LoadDbException loadDbException = new LoadDbException(R.string.load_db_exception, new Exception());
        when(changeProgressListDbInteractor.getProgressList(idHabit)).thenReturn(Single.error(loadDbException));

        progressViewModel.initChangeProgressList(idHabit);

        assertThat(progressViewModel.getErrorSingleLiveEvent().getValue(), is(R.string.load_db_exception));
        verify(changeProgressListDbInteractor).getProgressList(idHabit);
    }

    @Test
    public void addProgressSuccess() throws ChangeProgressException {
        Date date = new Date();

        progressViewModel.addProgress(date);

        verify(changeProgressListDbInteractor).addNewDate(date);
    }

    @Test
    public void addProgressError() {

        /*ChangeProgressException changeProgressException = new ChangeProgressException(R.string.change_progress_db_exception);
        progressViewModel.addProgress(null);

        assertThat(progressViewModel.getErrorSingleLiveEvent().getValue(), is(R.string.change_progress_db_exception));*/
        //verify(changeProgressListDbInteractor).addNewDate(null);
    }

    @Test
    public void deleteProgressSuccess() throws ChangeProgressException {
        Date date = new Date();

        progressViewModel.deleteProgress(date);

        verify(changeProgressListDbInteractor).deleteDate(date);
    }

    @Test
    public void deleteProgressError() {

        /*ProgressViewModel progressViewModel1 = new ProgressViewModel(new ChangeProgressListDbInteractor(mock(IHabitsDatabaseRepository.class)));

        progressViewModel1.deleteProgress(null);

         assertThat(progressViewModel1.getErrorSingleLiveEvent().getValue(), is(R.string.change_progress_db_exception));*/
        // verify(changeProgressListDbInteractor).deleteDate(null);
    }

    @Test
    public void saveProgressListSuccess() {
        when(changeProgressListDbInteractor.saveProgressList()).thenReturn(Completable.complete());

        progressViewModel.saveProgressList();

        assertThat(progressViewModel.getSuccessSingleLiveEvent().getValue(), is(R.string.change_progress_db_success_message));
        verify(changeProgressListDbInteractor).saveProgressList();
    }

    @Test
    public void saveProgressListErrorDelete() {
        when(changeProgressListDbInteractor.saveProgressList())
                .thenReturn(Completable.error(new DeleteFromDbException(R.string.delete_from_db_exception, new Exception())));

        progressViewModel.saveProgressList();

        assertThat(progressViewModel.getErrorSingleLiveEvent().getValue(), is(R.string.delete_from_db_exception));
        verify(changeProgressListDbInteractor).saveProgressList();
    }

    @Test
    public void saveProgressListErrorInsert() {
        when(changeProgressListDbInteractor.saveProgressList())
                .thenReturn(Completable.error(new InsertDbException(R.string.insert_db_exception, new Exception())));

        progressViewModel.saveProgressList();

        assertThat(progressViewModel.getErrorSingleLiveEvent().getValue(), is(R.string.insert_db_exception));
        verify(changeProgressListDbInteractor).saveProgressList();
    }
}