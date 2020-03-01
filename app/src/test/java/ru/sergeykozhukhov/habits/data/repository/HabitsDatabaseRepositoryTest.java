package ru.sergeykozhukhov.habits.data.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.GeneratorData;
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
import ru.sergeykozhukhov.habits.model.data.StatisticData;
import ru.sergeykozhukhov.habits.model.domain.Habit;
import ru.sergeykozhukhov.habits.model.domain.HabitWithProgresses;
import ru.sergeykozhukhov.habits.model.domain.Progress;
import ru.sergeykozhukhov.habits.model.domain.Statistic;
import ru.sergeykozhukhov.habits.model.domain.exception.InsertDbException;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Unit тесты на {@link HabitsDatabaseRepository}
 **/
@RunWith(MockitoJUnitRunner.class)
public class HabitsDatabaseRepositoryTest {

    /**
     * Генератор данных для тестирования
     */
    private GeneratorData generatorData;

    /**
     * Репозиторий (база данных)
     */
    private HabitsDatabaseRepository habitsDatabaseRepository;

    /**
     * Интерфейс определяющий возможные операции с базой данных
     */
    @Mock
    private HabitDao habitDao;

    /**
     * Конвертер Habit модели между data и domain слоями
     */
    @Mock
    private HabitConverter habitConverter;

    /**
     * Конвертер списка Habit моделей между data и domain слоями
     */
    @Mock
    private HabitListConverter habitListConverter;

    /**
     * Конвертер списка Progresses моделей между data и domain слоями
     */
    @Mock
    private ProgressListConverter progressListConverter;

    /**
     * Конвертер HabitWithProgresses модели между data и domain слоями
     */
    @Mock
    private HabitWithProgressesConverter habitWithProgressesConverter;

    /**
     * Конвертер списка HabitWithProgresses моделей между data и domain слоями
     */
    @Mock
    private HabitWithProgressesListConverter habitWithProgressesListConverter;

    /**
     * Конвертер списка Statistic моделей между data и domain слоями
     */
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

    /**
     * Тестирование успешного добавления записи о привычки
     */
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

    /**
     * Тестирование на получение ошибки при добавлении записи о привычки
     */
    @Test
    public void insertHabitError() {

        Habit habit = generatorData.createHabit(1);
        HabitData habitData = generatorData.createHabitData(1);

        Exception exception = new Exception();
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

    /**
     * Тестирование успешного добавления списка записей дат выполнения
     */
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

    /**
     * Тестирование на получение ошибки при добавлении списка записей дат выполнения
     */
    @Test
    public void insertProgressListError() {

        List<Progress> progressList = generatorData.createProgressList(1, 1, 2);
        List<ProgressData> progressDataList = generatorData.createProgressDataList(1, 1, 2);

        Exception exception = new Exception();
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

    /**
     * Тестирование успешного добавления списка привычек с соответствующими датами выполнения
     */
    @Test
    public void insertHabitWithProgressesListSuccess() {
        List<HabitWithProgresses> habitWithProgressesList = generatorData.createHabitWithProgressesList();
        List<HabitWithProgressesData> habitWithProgressesDataList = generatorData.createHabitWithProgressesDataList();

        when(habitWithProgressesConverter.convertFrom(habitWithProgressesList.get(0))).thenReturn(habitWithProgressesDataList.get(0));
        when(habitWithProgressesConverter.convertFrom(habitWithProgressesList.get(1))).thenReturn(habitWithProgressesDataList.get(1));
        when(habitDao.insertHabit(habitWithProgressesDataList.get(0).getHabitData())).thenReturn(Single.just(1L));
        when(habitDao.insertHabit(habitWithProgressesDataList.get(1).getHabitData())).thenReturn(Single.just(2L));
        when(habitDao.insertProgressList(habitWithProgressesDataList.get(0).getProgressDataList())).thenReturn(Completable.complete());
        when(habitDao.insertProgressList(habitWithProgressesDataList.get(1).getProgressDataList())).thenReturn(Completable.complete());

        habitsDatabaseRepository.insertHabitWithProgressesList(habitWithProgressesList)
                .test()
                .assertComplete();

        verify(habitWithProgressesConverter).convertFrom(habitWithProgressesList.get(0));
        verify(habitDao).insertHabit(habitWithProgressesDataList.get(0).getHabitData());
        verify(habitDao).insertProgressList(habitWithProgressesDataList.get(0).getProgressDataList());
        verify(habitWithProgressesConverter).convertFrom(habitWithProgressesList.get(1));
        verify(habitDao).insertHabit(habitWithProgressesDataList.get(1).getHabitData());
        verify(habitDao).insertProgressList(habitWithProgressesDataList.get(1).getProgressDataList());
        verifyNoMoreInteractions(habitDao);
    }

    /**
     * Тестирование на получение ошибки вставки в базу данных при добавлении привычки
     * в рамках добавления списка привычек с соответствующими датами выполнения
     */
    @Test
    public void insertHabitWithProgressesListErrorInsertHabit() {
        List<HabitWithProgresses> habitWithProgressesList = generatorData.createHabitWithProgressesList();
        List<HabitWithProgressesData> habitWithProgressesDataList = generatorData.createHabitWithProgressesDataList();

        Exception exception = new Exception();

        when(habitWithProgressesConverter.convertFrom(habitWithProgressesList.get(0))).thenReturn(habitWithProgressesDataList.get(0));
        when(habitDao.insertHabit(habitWithProgressesDataList.get(0).getHabitData())).thenReturn(Single.error(exception));

        habitsDatabaseRepository.insertHabitWithProgressesList(habitWithProgressesList)
                .test()
                .assertError(exception);

        verify(habitWithProgressesConverter).convertFrom(habitWithProgressesList.get(0));

        verify(habitDao).insertHabit(habitWithProgressesDataList.get(0).getHabitData());

        verify(habitDao, never()).insertProgressList(habitWithProgressesDataList.get(0).getProgressDataList());
        verify(habitWithProgressesConverter, never()).convertFrom(habitWithProgressesList.get(1));
        verify(habitDao, never()).insertHabit(habitWithProgressesDataList.get(1).getHabitData());
        verify(habitDao, never()).insertProgressList(habitWithProgressesDataList.get(1).getProgressDataList());

        verifyNoMoreInteractions(habitDao);
    }

    /**
     * Тестирование на получение ошибки вставки в базу данных при добавлении списка привычек
     * в рамках добавления списка привычек с соответствующими датами выполнения
     */
    @Test
    public void insertHabitWithProgressesListErrorInsertProgressList() {
        List<HabitWithProgresses> habitWithProgressesList = generatorData.createHabitWithProgressesList();
        List<HabitWithProgressesData> habitWithProgressesDataList = generatorData.createHabitWithProgressesDataList();

        Exception exception = new Exception();

        when(habitWithProgressesConverter.convertFrom(habitWithProgressesList.get(0))).thenReturn(habitWithProgressesDataList.get(0));
        when(habitDao.insertHabit(habitWithProgressesDataList.get(0).getHabitData())).thenReturn(Single.just(1L));
        when(habitDao.insertProgressList(habitWithProgressesDataList.get(0).getProgressDataList())).thenReturn(Completable.error(exception));

        habitsDatabaseRepository.insertHabitWithProgressesList(habitWithProgressesList)
                .test()
                .assertError(exception);

        verify(habitWithProgressesConverter).convertFrom(habitWithProgressesList.get(0));
        verify(habitDao).insertHabit(habitWithProgressesDataList.get(0).getHabitData());
        verify(habitDao).insertProgressList(habitWithProgressesDataList.get(0).getProgressDataList());

        verify(habitWithProgressesConverter, never()).convertFrom(habitWithProgressesList.get(1));
        verify(habitDao, never()).insertHabit(habitWithProgressesDataList.get(1).getHabitData());
        verify(habitDao, never()).insertProgressList(habitWithProgressesDataList.get(1).getProgressDataList());

        verifyNoMoreInteractions(habitDao);
    }

    /**
     * Тестирование успешной загрузка списка привычек
     */
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

    /**
     * Тестирование на получение ошибки при загрузки списка привычек
     */
    @Test
    public void loadHabitListError() {

        Exception exception = new Exception();
        Flowable<List<HabitData>> flowable = Flowable.error(exception);

        when(habitDao.getHabitList()).thenReturn(flowable);

        habitsDatabaseRepository.loadHabitList()
                .test()
                .assertError(exception)
                .assertValueCount(0);

        verify(habitDao).getHabitList();
        verifyNoMoreInteractions(habitDao);
    }

    /**
     * Тестирование успешного получения записей всех дат выполнения опредленной привычки
     */
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

    /**
     * Тестирование на получение ошибки при получении записей всех дат выполнения опредленной привычки
     */
    @Test
    public void loadProgressListByIdHabitError() {

        long idHabit = 1L;
        Exception exception = new Exception();
        Single<List<ProgressData>> single = Single.error(exception);

        when(habitDao.getProgressByHabit(idHabit)).thenReturn(single);

        habitsDatabaseRepository.loadProgressListByIdHabit(idHabit)
                .test()
                .assertError(exception);

        verify(habitDao).getProgressByHabit(idHabit);
        verifyNoMoreInteractions(habitDao);
    }

    /**
     * Тестирование успешного получение списка всех привычек с соответствующими датами выполнения
     */
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

    /**
     * Тестирование на получение ошибки при получении списка всех привычек с соответствующими датами выполнения
     */
    @Test
    public void loadHabitWithProgressesListError() {

        Exception exception = new Exception();
        Single<List<HabitWithProgressesData>> single = Single.error(exception);

        when(habitDao.getHabitWithProgressesList()).thenReturn(single);

        habitsDatabaseRepository.loadHabitWithProgressesList()
                .test()
                .assertError(exception);

        verify(habitDao).getHabitWithProgressesList();
        verifyNoMoreInteractions(habitDao);
    }

    /**
     * Тестирование успешного получения списка данных по привычкам с указанием количества выполненных дней
     */
    @Test
    public void loadStatisticListSuccess() {

        List<StatisticData> statisticDataList = generatorData.createStatisticDataList();
        List<Statistic> statisticList = generatorData.createStatisticList();

        when(habitDao.getStatisticList()).thenReturn(Single.just(statisticDataList));
        when(statisticListConverter.convertTo(statisticDataList)).thenReturn(statisticList);

        habitsDatabaseRepository.loadStatisticList()
                .test()
                .assertNoErrors()
                .assertValue(statisticList);

        verify(statisticListConverter).convertTo(statisticDataList);
        verify(habitDao).getStatisticList();
        verifyNoMoreInteractions(habitDao);
    }

    /**
     * Тестирование на получение ошибки при получении списка данных по привычкам с указанием количества выполненных дней
     */
    @Test
    public void loadStatisticListError() {

        Exception exception = new Exception();
        Single<List<StatisticData>> single = Single.error(exception);

        when(habitDao.getStatisticList()).thenReturn(single);

        habitsDatabaseRepository.loadStatisticList()
                .test()
                .assertError(exception);

        verify(habitDao).getStatisticList();
        verifyNoMoreInteractions(habitDao);
    }

    /**
     * Тестирование успешного удаления всех привычек
     */
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

    /**
     * Тестирование на получение ошибки при удалении всех привычек
     */
    @Test
    public void deleteAllHabitsError() {
        Exception exception = new Exception();
        Completable completable = Completable.error(exception);

        when(habitDao.deleteAll()).thenReturn(completable);

        habitsDatabaseRepository.deleteAllHabits()
                .test()
                .assertError(exception);

        verify(habitDao).deleteAll();
        verifyNoMoreInteractions(habitDao);
    }

    /**
     * Тестирование успешного удаления указанного списка дат выполнения
     */
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

    /**
     * Тестирование на получение ошибки при удалении указанного списка дат выполнения
     */
    @Test
    public void deleteProgressListError() {

        List<Progress> progressList = generatorData.createProgressList(1, 1, 2);
        List<ProgressData> progressDataList = generatorData.createProgressDataList(1, 1, 2);

        Exception exception = new Exception();

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