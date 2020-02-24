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
import io.reactivex.observers.TestObserver;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.GeneratorData;
import ru.sergeykozhukhov.habits.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.domain.IInreractor.IGetJwtValueInteractor;
import ru.sergeykozhukhov.habits.model.domain.HabitWithProgresses;
import ru.sergeykozhukhov.habits.model.domain.exception.DeleteFromDbException;
import ru.sergeykozhukhov.habits.model.domain.exception.GetJwtException;
import ru.sergeykozhukhov.habits.model.domain.exception.InsertDbException;
import ru.sergeykozhukhov.habits.model.domain.exception.ReplicationException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReplicationListHabitsWebInteractorTest {

    private GeneratorData generatorData;

    private ReplicationListHabitsWebInteractor replicationListHabitsWebInteractor;
    @Mock
    private IHabitsWebRepository habitsWebRepository;
    @Mock
    private IHabitsDatabaseRepository habitsDatabaseRepository;
    @Mock
    private IGetJwtValueInteractor getJwtValue;


    @Before
    public void setUp() {
        replicationListHabitsWebInteractor = new ReplicationListHabitsWebInteractor(
                habitsWebRepository,
                habitsDatabaseRepository,
                getJwtValue
        );
        generatorData = new GeneratorData();
    }

    @Test
    public void loadHabitWithProgressesListSuccess() throws GetJwtException {

        String jwt = "token";

        List<HabitWithProgresses> habitWithProgressesList = generatorData.createHabitWithProgressesList();
        Single<List<HabitWithProgresses>> single = Single.just(habitWithProgressesList);

        Completable completableDelete = Completable.complete();
        Completable completableInsert = Completable.complete();

        when(getJwtValue.getValue()).thenReturn(jwt);
        when(habitsWebRepository.loadHabitWithProgressesList(jwt)).thenReturn(single);
        when(habitsDatabaseRepository.deleteAllHabits()).thenReturn(completableDelete);
        when(habitsDatabaseRepository.insertHabitWithProgressesList(single.blockingGet())).thenReturn(completableInsert);

        replicationListHabitsWebInteractor.loadHabitWithProgressesList()
                .test()
                .assertNoErrors()
                .assertComplete();

        InOrder inOrder = Mockito.inOrder(getJwtValue, habitsWebRepository, habitsDatabaseRepository);

        inOrder.verify(getJwtValue).getValue();
        inOrder.verify(habitsWebRepository).loadHabitWithProgressesList(jwt);
        inOrder.verify(habitsDatabaseRepository).deleteAllHabits();
        inOrder.verify(habitsDatabaseRepository).insertHabitWithProgressesList(single.blockingGet());
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void loadHabitWithProgressesListErrorGetJwt() throws GetJwtException {

        GetJwtException getJwtException = new GetJwtException(R.string.get_jwt_exception);

        when(getJwtValue.getValue()).thenThrow(getJwtException);

        replicationListHabitsWebInteractor.loadHabitWithProgressesList()
                .test()
                .assertError(getJwtException);

        InOrder inOrder = Mockito.inOrder(getJwtValue, habitsWebRepository, habitsDatabaseRepository);

        inOrder.verify(getJwtValue).getValue();
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void loadHabitWithProgressesListErrorLoadList() throws GetJwtException {

        String jwt = "token";

        ResponseBody errorBody = mock(ResponseBody.class);
        HttpException exception = new HttpException(Response.error(401, errorBody));
        ReplicationException replicationException = new ReplicationException(R.string.load_web_exception, exception);

        Single<List<HabitWithProgresses>> single = Single.error(replicationException);

        when(getJwtValue.getValue()).thenReturn(jwt);
        when(habitsWebRepository.loadHabitWithProgressesList(jwt)).thenReturn(single);

        replicationListHabitsWebInteractor.loadHabitWithProgressesList()
                .test()
                .assertError(replicationException);

        InOrder inOrder = Mockito.inOrder(getJwtValue, habitsWebRepository, habitsDatabaseRepository);

        inOrder.verify(getJwtValue).getValue();
        inOrder.verify(habitsWebRepository).loadHabitWithProgressesList(jwt);
        inOrder.verifyNoMoreInteractions();
    }


    @Test
    public void loadHabitWithProgressesListErrorDelete() throws GetJwtException {

        String jwt = "token";

        String msg = "error";
        Exception exception = new Exception(msg);

        DeleteFromDbException deleteFromDbException = new DeleteFromDbException(R.string.delete_from_db_exception, exception);

        List<HabitWithProgresses> habitWithProgressesList = generatorData.createHabitWithProgressesList();
        Single<List<HabitWithProgresses>> single = Single.just(habitWithProgressesList);

        Completable completableDelete = Completable.error(deleteFromDbException);

        Completable completableInsert = Completable.complete();
        when(getJwtValue.getValue()).thenReturn(jwt);
        when(habitsWebRepository.loadHabitWithProgressesList(jwt)).thenReturn(single);
        when(habitsDatabaseRepository.deleteAllHabits()).thenReturn(completableDelete);
        when(habitsDatabaseRepository.insertHabitWithProgressesList(single.blockingGet())).thenReturn(completableInsert);

        replicationListHabitsWebInteractor.loadHabitWithProgressesList()
                .test()
                .assertError(deleteFromDbException);

        InOrder inOrder = Mockito.inOrder(getJwtValue, habitsWebRepository, habitsDatabaseRepository);

        inOrder.verify(getJwtValue).getValue();
        inOrder.verify(habitsWebRepository).loadHabitWithProgressesList(jwt);
        inOrder.verify(habitsDatabaseRepository).deleteAllHabits();
        inOrder.verify(habitsDatabaseRepository).insertHabitWithProgressesList(single.blockingGet()); // moment
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void loadHabitWithProgressesListErrorInput() throws GetJwtException {

        String jwt = "token";

        String msg = "error";
        Exception exception = new Exception(msg);

        InsertDbException insertDbException = new InsertDbException(R.string.delete_from_db_exception, exception);

        List<HabitWithProgresses> habitWithProgressesList = generatorData.createHabitWithProgressesList();
        Single<List<HabitWithProgresses>> single = Single.just(habitWithProgressesList);

        Completable completableDelete = Completable.complete();
        Completable completableInsert = Completable.error(insertDbException);
        when(getJwtValue.getValue()).thenReturn(jwt);
        when(habitsWebRepository.loadHabitWithProgressesList(jwt)).thenReturn(single);
        when(habitsDatabaseRepository.deleteAllHabits()).thenReturn(completableDelete);
        when(habitsDatabaseRepository.insertHabitWithProgressesList(single.blockingGet())).thenReturn(completableInsert);

        replicationListHabitsWebInteractor.loadHabitWithProgressesList()
                .test()
                .assertError(insertDbException);

        InOrder inOrder = Mockito.inOrder(getJwtValue, habitsWebRepository, habitsDatabaseRepository);

        inOrder.verify(getJwtValue).getValue();
        inOrder.verify(habitsWebRepository).loadHabitWithProgressesList(jwt);
        inOrder.verify(habitsDatabaseRepository).deleteAllHabits();
        inOrder.verify(habitsDatabaseRepository).insertHabitWithProgressesList(single.blockingGet());
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void loadHabitWithProgressesListErrors() throws GetJwtException {

        String jwt = "token";

        String msgDelete = "errorDelete";
        Exception exceptionDelete = new Exception(msgDelete);

        String msgInput = "errorInput";
        Exception exceptionInput= new Exception(msgInput);

        DeleteFromDbException deleteFromDbException = new DeleteFromDbException(R.string.delete_from_db_exception, exceptionDelete);
        InsertDbException insertDbException = new InsertDbException(R.string.insert_db_exception, exceptionInput);

        List<HabitWithProgresses> habitWithProgressesList = generatorData.createHabitWithProgressesList();
        Single<List<HabitWithProgresses>> single = Single.just(habitWithProgressesList);

        Completable completableDelete = Completable.error(deleteFromDbException);
        Completable completableInsert = Completable.error(insertDbException);
        when(getJwtValue.getValue()).thenReturn(jwt);
        when(habitsWebRepository.loadHabitWithProgressesList(jwt)).thenReturn(single);
        when(habitsDatabaseRepository.deleteAllHabits()).thenReturn(completableDelete);
        when(habitsDatabaseRepository.insertHabitWithProgressesList(single.blockingGet())).thenReturn(completableInsert);

        TestObserver<Void> test = replicationListHabitsWebInteractor.loadHabitWithProgressesList()
                .test()
                .assertError(deleteFromDbException);

        assertEquals(1, test.errorCount());

        InOrder inOrder = Mockito.inOrder(getJwtValue, habitsWebRepository, habitsDatabaseRepository);

        inOrder.verify(getJwtValue).getValue();
        inOrder.verify(habitsWebRepository).loadHabitWithProgressesList(jwt);
        inOrder.verify(habitsDatabaseRepository).deleteAllHabits();
        inOrder.verify(habitsDatabaseRepository).insertHabitWithProgressesList(single.blockingGet());
        inOrder.verifyNoMoreInteractions();
    }
}