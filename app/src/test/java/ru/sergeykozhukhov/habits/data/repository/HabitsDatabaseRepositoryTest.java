package ru.sergeykozhukhov.habits.data.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import ru.sergeykozhukhov.habits.data.converter.HabitConverter;
import ru.sergeykozhukhov.habits.data.converter.HabitListConverter;
import ru.sergeykozhukhov.habits.data.converter.HabitWithProgressesConverter;
import ru.sergeykozhukhov.habits.data.converter.HabitWithProgressesListConverter;
import ru.sergeykozhukhov.habits.data.converter.ProgressListConverter;
import ru.sergeykozhukhov.habits.data.converter.StatisticListConverter;
import ru.sergeykozhukhov.habits.data.database.HabitDao;
import ru.sergeykozhukhov.habits.model.data.HabitData;
import ru.sergeykozhukhov.habits.model.data.HabitWithProgressesData;
import ru.sergeykozhukhov.habits.model.data.ProgressData;
import ru.sergeykozhukhov.habits.model.domain.Habit;
import ru.sergeykozhukhov.habits.model.domain.HabitWithProgresses;
import ru.sergeykozhukhov.habits.model.domain.Progress;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HabitsDatabaseRepositoryTest {

    private GeneratorData generatorData;

    private HabitsDatabaseRepository habitsDatabaseRepository;

    @Mock
    private HabitDao habitDao;
    @Mock
    private HabitConverter habitConverter;
    @Mock
    private HabitListConverter habitListConverter;
    @Mock
    private ProgressListConverter progressListConverter;
    @Mock
    private HabitWithProgressesConverter habitWithProgressesConverter;
    @Mock
    private HabitWithProgressesListConverter habitWithProgressesListConverter;
    @Mock
    private StatisticListConverter statisticListConverter;

    @Before
    public void setUp() {
        habitsDatabaseRepository = new HabitsDatabaseRepository(
                habitDao,
                habitConverter,
                habitListConverter,
                progressListConverter,
                habitWithProgressesConverter,
                habitWithProgressesListConverter,
                statisticListConverter
        );
        generatorData = new GeneratorData();
    }

    @Test
    public void insertHabitSuccess() {

        Habit habit = generatorData.createHabit(1);
        HabitData habitData = generatorData.createHabitData(1);

        long idHabit = 1L;
        Single<Long> single = Single.just(idHabit);

        when(habitConverter.convertFrom(habit)).thenReturn(habitData);
        when(habitDao.insertHabit(habitData)).thenReturn(single);

        habitsDatabaseRepository.insertHabit(habit)
                .test()
                .assertNoErrors()
                .assertValue(idHabit);

        verify(habitConverter).convertFrom(habit);
        verify(habitDao).insertHabit(habitData);
        verifyNoMoreInteractions(habitDao);
    }

    @Test
    public void insertHabitError() {

        Habit habit = generatorData.createHabit(1);
        HabitData habitData = generatorData.createHabitData(1);

        String msg = "error";
        Exception exception = new Exception(msg);
        Single<Long> single = Single.error(exception);

        when(habitConverter.convertFrom(habit)).thenReturn(habitData);
        when(habitDao.insertHabit(habitData)).thenReturn(single);

        habitsDatabaseRepository.insertHabit(habit)
                .test()
                .assertError(exception);

        verify(habitConverter).convertFrom(habit);
        verify(habitDao).insertHabit(habitData);
        verifyNoMoreInteractions(habitDao);
    }

    @Test
    public void insertProgressListSuccess() {

        List<Progress> progressList = generatorData.createProgressList(1, 1, 2);
        List<ProgressData> progressDataList = generatorData.createProgressDataList(1, 1, 2);

        Completable completable = Completable.complete();

        when(progressListConverter.convertFrom(progressList)).thenReturn(progressDataList);
        when(habitDao.insertProgressList(progressDataList)).thenReturn(completable);

        habitsDatabaseRepository.insertProgressList(progressList)
                .test()
                .assertComplete();

        verify(progressListConverter).convertFrom(progressList);
        verify(habitDao).insertProgressList(progressDataList);
        verifyNoMoreInteractions(habitDao);
    }

    @Test
    public void insertProgressListError() {

        List<Progress> progressList = generatorData.createProgressList(1, 1, 2);
        List<ProgressData> progressDataList = generatorData.createProgressDataList(1, 1, 2);

        String msg = "error";
        Exception exception = new Exception(msg);
        Completable completable = Completable.error(exception);

        when(progressListConverter.convertFrom(progressList)).thenReturn(progressDataList);
        when(habitDao.insertProgressList(progressDataList)).thenReturn(completable);

        habitsDatabaseRepository.insertProgressList(progressList)
                .test()
                .assertError(exception);

        verify(progressListConverter).convertFrom(progressList);
        verify(habitDao).insertProgressList(progressDataList);
        verifyNoMoreInteractions(habitDao);
    }

    // test
    @Test
    public void insertHabitWithProgressesListSuccess() {
        List<HabitWithProgresses> habitWithProgressesList = generatorData.createHabitWithProgressesList();
        List<HabitWithProgressesData> habitWithProgressesDataList = generatorData.createHabitWithProgressesDataList();
    }

    @Test
    public void loadHabitListSuccess() {

        List<Habit> habitList1 = generatorData.createHabitList(1, 5);
        List<HabitData> habitDataList1 = generatorData.createHabitDataList(1, 5);

        List<Habit> habitList2 = generatorData.createHabitList(1, 10);
        List<HabitData> habitDataList2 = generatorData.createHabitDataList(1, 10);


        Flowable<List<HabitData>> flowable = Flowable.just(habitDataList1, habitDataList2);

        when(habitListConverter.convertTo(habitDataList1)).thenReturn(habitList1);
        when(habitListConverter.convertTo(habitDataList2)).thenReturn(habitList2);

        when(habitDao.getHabitList()).thenReturn(flowable);

        habitsDatabaseRepository.loadHabitList()
                .test()
                .assertNoErrors()
                .assertValueCount(2)
                .assertValueAt(0, habitList1)
                .assertValueAt(1, habitList2);

        verify(habitListConverter).convertTo(habitDataList1);
        verify(habitListConverter).convertTo(habitDataList2);
        verify(habitDao).getHabitList();
        verifyNoMoreInteractions(habitDao);
    }

    @Test
    public void loadHabitListError() {

        String msg = "error";
        Exception exception = new Exception(msg);
        Flowable<List<HabitData>> flowable = Flowable.error(exception);

        when(habitDao.getHabitList()).thenReturn(flowable);

        habitsDatabaseRepository.loadHabitList()
                .test()
                .assertError(exception)
                .assertValueCount(0);

        verify(habitDao).getHabitList();
        verifyNoMoreInteractions(habitDao);
    }

    @Test
    public void loadProgressListByIdHabitSuccess() {
        List<Progress> progressList = generatorData.createProgressList(1, 1, 2);
        List<ProgressData> progressDataList = generatorData.createProgressDataList(1, 1, 2);

        long idHabit = 1L;
        Single<List<ProgressData>> single = Single.just(progressDataList);

        when(progressListConverter.convertTo(progressDataList)).thenReturn(progressList);
        when(habitDao.getProgressByHabit(idHabit)).thenReturn(single);

        habitsDatabaseRepository.loadProgressListByIdHabit(idHabit)
                .test()
                .assertNoErrors()
                .assertValue(progressList);

        verify(progressListConverter).convertTo(progressDataList);
        verify(habitDao).getProgressByHabit(idHabit);
        verifyNoMoreInteractions(habitDao);
    }

    @Test
    public void loadProgressListByIdHabitError() {

        long idHabit = 1L;
        String msg = "error";
        Exception exception = new Exception(msg);
        Single<List<ProgressData>> single = Single.error(exception);

        when(habitDao.getProgressByHabit(idHabit)).thenReturn(single);

        habitsDatabaseRepository.loadProgressListByIdHabit(idHabit)
                .test()
                .assertError(exception);

        verify(habitDao).getProgressByHabit(idHabit);
        verifyNoMoreInteractions(habitDao);
    }

    @Test
    public void loadHabitWithProgressesListSuccess() {

        List<HabitWithProgresses> habitWithProgressesList = generatorData.createHabitWithProgressesList();
        List<HabitWithProgressesData> habitWithProgressesDataList = generatorData.createHabitWithProgressesDataList();

        Single<List<HabitWithProgressesData>> single = Single.just(habitWithProgressesDataList);

        when(habitWithProgressesListConverter.convertTo(habitWithProgressesDataList)).thenReturn(habitWithProgressesList);
        when(habitDao.getHabitWithProgressesList()).thenReturn(single);

        habitsDatabaseRepository.loadHabitWithProgressesList()
                .test()
                .assertNoErrors()
                .assertValue(habitWithProgressesList);

        verify(habitWithProgressesListConverter).convertTo(habitWithProgressesDataList);
        verify(habitDao).getHabitWithProgressesList();
        verifyNoMoreInteractions(habitDao);

    }

    @Test
    public void loadHabitWithProgressesListError() {

        String msg = "error";
        Exception exception = new Exception(msg);
        Single<List<HabitWithProgressesData>> single = Single.error(exception);

        when(habitDao.getHabitWithProgressesList()).thenReturn(single);

        habitsDatabaseRepository.loadHabitWithProgressesList()
                .test()
                .assertError(exception);

        verify(habitDao).getHabitWithProgressesList();
        verifyNoMoreInteractions(habitDao);

    }

    // test
    @Test
    public void loadStatisticListSuccess() {
    }

    @Test
    public void deleteAllHabitsSuccess() {
        Completable completable = Completable.complete();

        when(habitDao.deleteAll()).thenReturn(completable);

        habitsDatabaseRepository.deleteAllHabits()
                .test()
                .assertNoErrors()
                .assertComplete();

        verify(habitDao).deleteAll();
        verifyNoMoreInteractions(habitDao);
    }

    @Test
    public void deleteAllHabitsError() {
        String msg = "error";
        Exception exception = new Exception(msg);
        Completable completable = Completable.error(exception);

        when(habitDao.deleteAll()).thenReturn(completable);

        habitsDatabaseRepository.deleteAllHabits()
                .test()
                .assertError(exception);

        verify(habitDao).deleteAll();
        verifyNoMoreInteractions(habitDao);
    }

    @Test
    public void deleteProgressListSuccess() {

        List<Progress> progressList = generatorData.createProgressList(1, 1, 2);
        List<ProgressData> progressDataList = generatorData.createProgressDataList(1, 1, 2);

        Completable completable = Completable.complete();

        when(progressListConverter.convertFrom(progressList)).thenReturn(progressDataList);
        when(habitDao.deleteProgressList(progressDataList)).thenReturn(completable);

        habitsDatabaseRepository.deleteProgressList(progressList)
                .test()
                .assertNoErrors()
                .assertComplete();

        verify(progressListConverter).convertFrom(progressList);
        verify(habitDao).deleteProgressList(progressDataList);
        verifyNoMoreInteractions(habitDao);
    }

    @Test
    public void deleteProgressListError() {

        List<Progress> progressList = generatorData.createProgressList(1, 1, 2);
        List<ProgressData> progressDataList = generatorData.createProgressDataList(1, 1, 2);

        String msg = "error";
        Exception exception = new Exception(msg);

        Completable completable = Completable.error(exception);

        when(progressListConverter.convertFrom(progressList)).thenReturn(progressDataList);
        when(habitDao.deleteProgressList(progressDataList)).thenReturn(completable);

        habitsDatabaseRepository.deleteProgressList(progressList)
                .test()
                .assertError(exception);

        verify(progressListConverter).convertFrom(progressList);
        verify(habitDao).deleteProgressList(progressDataList);
        verifyNoMoreInteractions(habitDao);
    }


}