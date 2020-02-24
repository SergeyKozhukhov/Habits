package ru.sergeykozhukhov.habits.domain.usecase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.functions.Predicate;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.GeneratorData;
import ru.sergeykozhukhov.habits.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.domain.IInreractor.IGetJwtValueInteractor;
import ru.sergeykozhukhov.habits.model.domain.HabitWithProgresses;
import ru.sergeykozhukhov.habits.model.domain.exception.BackupException;
import ru.sergeykozhukhov.habits.model.domain.exception.GetJwtException;
import ru.sergeykozhukhov.habits.model.domain.exception.LoadDbException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BackupWebHabitListWebInteractorTest {

    private GeneratorData generatorData;

    private BackupWebHabitListWebInteractor backupWebHabitListWebInteractor;

    @Mock
    private IHabitsWebRepository habitsWebRepository;
    @Mock
    private IHabitsDatabaseRepository habitsDatabaseRepository;
    @Mock
    private IGetJwtValueInteractor getJwtValueInteractor;

    @Before
    public void setUp() {
        backupWebHabitListWebInteractor = new BackupWebHabitListWebInteractor(habitsWebRepository, habitsDatabaseRepository, getJwtValueInteractor);
        generatorData = new GeneratorData();
    }

    @Test
    public void insertHabitWithProgressesListSuccess() throws GetJwtException {
        String jwt = "token";
        when(getJwtValueInteractor.getValue()).thenReturn(jwt);

        List<HabitWithProgresses> habitWithProgressesList = generatorData.createHabitWithProgressesList();
        Single<List<HabitWithProgresses>> single = Single.just(habitWithProgressesList);
        when(habitsDatabaseRepository.loadHabitWithProgressesList()).thenReturn(single);

        Completable completable = Completable.complete();
        when(habitsWebRepository.insertHabitWithProgressesList(habitWithProgressesList, jwt)).thenReturn(completable);

        backupWebHabitListWebInteractor.insertHabitWithProgressesList()
                .test()
                .assertNoErrors()
                .assertComplete();

        InOrder inOrder = Mockito.inOrder(getJwtValueInteractor, habitsDatabaseRepository, habitsWebRepository);

        inOrder.verify(getJwtValueInteractor).getValue();
        inOrder.verify(habitsDatabaseRepository).loadHabitWithProgressesList();
        inOrder.verify(habitsWebRepository).insertHabitWithProgressesList(habitWithProgressesList, jwt);
    }

    @Test
    public void insertHabitWithProgressesListErrorGetJwt() throws GetJwtException {

        GetJwtException getJwtException = new GetJwtException(R.string.get_jwt_exception);

        when(getJwtValueInteractor.getValue()).thenThrow(getJwtException);

        backupWebHabitListWebInteractor.insertHabitWithProgressesList()
                .test()
                .assertError(getJwtException);

        verify(getJwtValueInteractor).getValue();
    }

    @Test
    public void insertHabitWithProgressesListErrorLoadListDb() throws GetJwtException {
        String jwt = "token";
        when(getJwtValueInteractor.getValue()).thenReturn(jwt);

        Exception exception = new Exception();
        Single<List<HabitWithProgresses>> single = Single.error(exception);
        when(habitsDatabaseRepository.loadHabitWithProgressesList()).thenReturn(single);

        backupWebHabitListWebInteractor.insertHabitWithProgressesList()
                .test()
                .assertError(throwable -> {
                    if (throwable instanceof LoadDbException){
                        return R.string.load_db_exception == ((LoadDbException) throwable).getMessageRes();
                    }
                    return false;
                });

        InOrder inOrder = Mockito.inOrder(getJwtValueInteractor, habitsDatabaseRepository, habitsWebRepository);

        inOrder.verify(getJwtValueInteractor).getValue();
        inOrder.verify(habitsDatabaseRepository).loadHabitWithProgressesList();
    }

    @Test
    public void insertHabitWithProgressesListErrorInsertListWeb() throws GetJwtException {
        String jwt = "token";
        when(getJwtValueInteractor.getValue()).thenReturn(jwt);

        List<HabitWithProgresses> habitWithProgressesList = generatorData.createHabitWithProgressesList();
        Single<List<HabitWithProgresses>> single = Single.just(habitWithProgressesList);
        when(habitsDatabaseRepository.loadHabitWithProgressesList()).thenReturn(single);

        Exception exception = new Exception();
        Completable completable = Completable.error(exception);
        when(habitsWebRepository.insertHabitWithProgressesList(habitWithProgressesList, jwt)).thenReturn(completable);

        backupWebHabitListWebInteractor.insertHabitWithProgressesList()
                .test()
                .assertError(throwable -> {
                    if (throwable instanceof BackupException)
                        return R.string.insert_web_exception == ((BackupException) throwable).getMessageRes();
                    return false;
                });

        InOrder inOrder = Mockito.inOrder(getJwtValueInteractor, habitsDatabaseRepository, habitsWebRepository);

        inOrder.verify(getJwtValueInteractor).getValue();
        inOrder.verify(habitsDatabaseRepository).loadHabitWithProgressesList();
        inOrder.verify(habitsWebRepository).insertHabitWithProgressesList(habitWithProgressesList, jwt);
    }
}