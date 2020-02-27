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
import ru.sergeykozhukhov.habits.domain.usecaseimpl.BackupWebHabitListWebInteractor;
import ru.sergeykozhukhov.habits.domain.usecaseimpl.DeleteJwtInteractor;
import ru.sergeykozhukhov.habits.domain.usecaseimpl.ReplicationListHabitsWebInteractor;
import ru.sergeykozhukhov.habits.model.domain.exception.BackupException;
import ru.sergeykozhukhov.habits.model.domain.exception.DeleteFromDbException;
import ru.sergeykozhukhov.habits.model.domain.exception.GetJwtException;
import ru.sergeykozhukhov.habits.model.domain.exception.InsertDbException;
import ru.sergeykozhukhov.habits.model.domain.exception.LoadDbException;
import ru.sergeykozhukhov.habits.model.domain.exception.ReplicationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountViewModelTest {

    @Rule
    public final RxImmediateSchedulerRule rxImmediateSchedulerRule = new RxImmediateSchedulerRule();
    @Rule
    public final InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    private AccountViewModel accountViewModel;
    @Mock
    private ReplicationListHabitsWebInteractor replicationListHabitsWebInteractor;
    @Mock
    private BackupWebHabitListWebInteractor backupWebHabitListWebInteractor;
    @Mock
    private DeleteJwtInteractor deleteJwtInteractor;

    @Before
    public void setUp() {
        accountViewModel = new AccountViewModel(backupWebHabitListWebInteractor, replicationListHabitsWebInteractor, deleteJwtInteractor);
    }

    @Test
    public void replicationSuccess() {
        when(replicationListHabitsWebInteractor.loadHabitWithProgressesList()).thenReturn(Completable.complete());

        accountViewModel.replication();

        assertThat(accountViewModel.getSuccessSingleLiveEvent().getValue(), is(R.string.replication_success_message));
        verify(replicationListHabitsWebInteractor).loadHabitWithProgressesList();
    }

    @Test
    public void replicationErrorGetJwt() {
        when(replicationListHabitsWebInteractor.loadHabitWithProgressesList())
                .thenReturn(Completable.error(new GetJwtException(R.string.get_jwt_exception, new Exception())));

        accountViewModel.replication();

        assertThat(accountViewModel.getErrorSingleLiveEvent().getValue(), is(R.string.get_jwt_exception));
        verify(replicationListHabitsWebInteractor).loadHabitWithProgressesList();
    }

    @Test
    public void replicationErrorLoadFromWeb() {
        ReplicationException replicationException = new ReplicationException(R.string.load_web_exception, new Exception());
        when(replicationListHabitsWebInteractor.loadHabitWithProgressesList()).thenReturn(Completable.error(replicationException));

        accountViewModel.replication();

        assertThat(accountViewModel.getErrorSingleLiveEvent().getValue(), is(R.string.load_web_exception));
        verify(replicationListHabitsWebInteractor).loadHabitWithProgressesList();
    }

    @Test
    public void replicationErrorDeleteAllFromDb() {
        DeleteFromDbException deleteFromDbException = new DeleteFromDbException(R.string.cleanup_db_exception, new Exception());
        when(replicationListHabitsWebInteractor.loadHabitWithProgressesList()).thenReturn(Completable.error(deleteFromDbException));

        accountViewModel.replication();

        assertThat(accountViewModel.getErrorSingleLiveEvent().getValue(), is(R.string.cleanup_db_exception));
        verify(replicationListHabitsWebInteractor).loadHabitWithProgressesList();
    }

    @Test
    public void replicationErrorInsertToDb() {
        InsertDbException insertDbException = new InsertDbException(R.string.insert_db_exception, new Exception());
        when(replicationListHabitsWebInteractor.loadHabitWithProgressesList()).thenReturn(Completable.error(insertDbException));

        accountViewModel.replication();

        assertThat(accountViewModel.getErrorSingleLiveEvent().getValue(), is(R.string.insert_db_exception));
        verify(replicationListHabitsWebInteractor).loadHabitWithProgressesList();
    }


    @Test
    public void backupSuccess() {
        when(backupWebHabitListWebInteractor.insertHabitWithProgressesList()).thenReturn(Completable.complete());

        accountViewModel.backup();

        assertThat(accountViewModel.getSuccessSingleLiveEvent().getValue(), is(R.string.backup_success_message));
        verify(backupWebHabitListWebInteractor).insertHabitWithProgressesList();
    }

    @Test
    public void backupErrorGetJwt() {
        when(backupWebHabitListWebInteractor.insertHabitWithProgressesList())
                .thenReturn(Completable.error(new GetJwtException(R.string.get_jwt_exception, new Exception())));

        accountViewModel.backup();

        assertThat(accountViewModel.getErrorSingleLiveEvent().getValue(), is(R.string.get_jwt_exception));
        verify(backupWebHabitListWebInteractor).insertHabitWithProgressesList();
    }

    @Test
    public void backupErrorLoadFromDb() {
        when(backupWebHabitListWebInteractor.insertHabitWithProgressesList())
                .thenReturn(Completable.error(new LoadDbException(R.string.load_db_exception, new Exception())));

        accountViewModel.backup();

        assertThat(accountViewModel.getErrorSingleLiveEvent().getValue(), is(R.string.load_db_exception));
        verify(backupWebHabitListWebInteractor).insertHabitWithProgressesList();
    }

    @Test
    public void backupErrorInsertToWeb() {
        when(backupWebHabitListWebInteractor.insertHabitWithProgressesList())
                .thenReturn(Completable.error(new BackupException(R.string.insert_web_exception, new Exception())));

        accountViewModel.backup();

        assertThat(accountViewModel.getErrorSingleLiveEvent().getValue(), is(R.string.insert_web_exception));
        verify(backupWebHabitListWebInteractor).insertHabitWithProgressesList();
    }

    @Test
    public void logout() {

        accountViewModel.logout();

        assertThat(accountViewModel.getLogOutSuccessSingleLiveEvent().getValue(), is(R.string.log_out_success_message));
        verify(deleteJwtInteractor).deleteJwt();

    }
}