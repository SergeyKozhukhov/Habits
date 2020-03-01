package ru.sergeykozhukhov.habits.domain.usecaseimpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.FieldSetter;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.Single;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.GeneratorData;
import ru.sergeykozhukhov.habits.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.model.domain.Progress;
import ru.sergeykozhukhov.habits.model.domain.exception.ChangeProgressException;
import ru.sergeykozhukhov.habits.model.domain.exception.LoadDbException;

import static junit.framework.TestCase.fail;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChangeProgressListDbInteractorTest {

    private GeneratorData generatorData;

    private ChangeProgressListDbInteractor changeProgressListDbInteractor;

    @Mock
    private IHabitsDatabaseRepository habitsDatabaseRepository;

    @Before
    public void setUp() {
        changeProgressListDbInteractor = new ChangeProgressListDbInteractor(habitsDatabaseRepository);

        generatorData = new GeneratorData();
    }

    @Test
    public void getProgressListSuccess() {
        long idHabit = 1L;
        List<Progress> progressList = generatorData.createProgressList(1, idHabit, 3);
        List<Date> dateList = Arrays.asList(progressList.get(0).getDate(), progressList.get(1).getDate(), progressList.get(2).getDate());
        when(habitsDatabaseRepository.loadProgressListByIdHabit(idHabit)).thenReturn(Single.just(progressList));

        changeProgressListDbInteractor.getProgressList(idHabit)
                .test()
                .assertNoErrors()
                .assertValue(dateList);
        verify(habitsDatabaseRepository).loadProgressListByIdHabit(idHabit);
    }

    @Test
    public void getProgressListError() {
        long idHabit = 1L;

        Exception exception = new Exception();
        LoadDbException loadDbException = new LoadDbException(R.string.load_db_exception, exception);
        when(habitsDatabaseRepository.loadProgressListByIdHabit(idHabit)).thenReturn(Single.error(exception));

        changeProgressListDbInteractor.getProgressList(idHabit)
                .test()
                .assertError(loadDbException);

        verify(habitsDatabaseRepository).loadProgressListByIdHabit(idHabit);
    }

    @Test
    public void addNewDateSuccessDeleted() throws ChangeProgressException, NoSuchFieldException {
        List<Date> progressAddedList = new LinkedList<>();
        List<Date> progressDeletedList = new LinkedList<>();
        Date date = new Date();
        progressDeletedList.add(date);

        FieldSetter.setField(changeProgressListDbInteractor,
                ChangeProgressListDbInteractor.class.getDeclaredField("progressAddedList"), progressAddedList);
        FieldSetter.setField(changeProgressListDbInteractor,
                ChangeProgressListDbInteractor.class.getDeclaredField("progressDeletedList"), progressDeletedList);

        changeProgressListDbInteractor.addNewDate(date);

        assertThat(progressDeletedList.isEmpty(), is(true));
        assertThat(progressAddedList.isEmpty(), is(true));
    }

    @Test
    public void addNewDateSuccessAdded() throws ChangeProgressException, NoSuchFieldException {
        List<Date> progressAddedList = new LinkedList<>();
        List<Date> progressDeletedList = new LinkedList<>();
        Date date = new Date();

        FieldSetter.setField(changeProgressListDbInteractor,
                ChangeProgressListDbInteractor.class.getDeclaredField("progressAddedList"), progressAddedList);
        FieldSetter.setField(changeProgressListDbInteractor,
                ChangeProgressListDbInteractor.class.getDeclaredField("progressDeletedList"), progressDeletedList);

        changeProgressListDbInteractor.addNewDate(date);

        assertThat(progressDeletedList.isEmpty(), is(true));
        assertThat(progressAddedList.size(), is(1));
        assertThat(progressAddedList.get(0), is(date));
    }

    @Test
    public void addNewDateError() {
        try {
            changeProgressListDbInteractor.addNewDate(null);
            fail();
        } catch (ChangeProgressException e) {
            assertThat(e.getMessageRes(), is(R.string.change_progress_db_exception));
        }
    }

    @Test
    public void deleteDateSuccessDeleted() throws ChangeProgressException, NoSuchFieldException {
        List<Date> progressAddedList = new LinkedList<>();
        List<Date> progressDeletedList = new LinkedList<>();
        Date date = new Date();
        progressAddedList.add(date);

        FieldSetter.setField(changeProgressListDbInteractor,
                ChangeProgressListDbInteractor.class.getDeclaredField("progressAddedList"), progressAddedList);
        FieldSetter.setField(changeProgressListDbInteractor,
                ChangeProgressListDbInteractor.class.getDeclaredField("progressDeletedList"), progressDeletedList);

        changeProgressListDbInteractor.deleteDate(date);

        assertThat(progressAddedList.isEmpty(), is(true));
        assertThat(progressDeletedList.isEmpty(), is(true));
    }

    @Test
    public void deleteDateSuccessAdded() throws ChangeProgressException, NoSuchFieldException {
        List<Date> progressAddedList = new LinkedList<>();
        List<Date> progressDeletedList = new LinkedList<>();
        Date date = new Date();

        FieldSetter.setField(changeProgressListDbInteractor,
                ChangeProgressListDbInteractor.class.getDeclaredField("progressAddedList"), progressAddedList);
        FieldSetter.setField(changeProgressListDbInteractor,
                ChangeProgressListDbInteractor.class.getDeclaredField("progressDeletedList"), progressDeletedList);

        changeProgressListDbInteractor.deleteDate(date);

        assertThat(progressAddedList.isEmpty(), is(true));
        assertThat(progressDeletedList.size(), is(1));
        assertThat(progressDeletedList.get(0), is(date));
    }

    @Test
    public void deleteDateError() {
        try {
            changeProgressListDbInteractor.deleteDate(null);
            fail();
        } catch (ChangeProgressException e) {
            assertThat(e.getMessageRes(), is(R.string.change_progress_db_exception));
        }
    }

    @Test
    public void saveProgressList() {

    }
}