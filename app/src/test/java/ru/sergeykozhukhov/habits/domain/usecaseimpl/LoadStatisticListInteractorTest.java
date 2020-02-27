package ru.sergeykozhukhov.habits.domain.usecaseimpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Single;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.GeneratorData;
import ru.sergeykozhukhov.habits.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.model.domain.Statistic;
import ru.sergeykozhukhov.habits.model.domain.exception.LoadDbException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoadStatisticListInteractorTest {

    private GeneratorData generatorData;

    private LoadStatisticListInteractor loadStatisticListInteractor;
    @Mock
    private IHabitsDatabaseRepository habitsDatabaseRepository;

    @Before
    public void setUp() {
        loadStatisticListInteractor = new LoadStatisticListInteractor(habitsDatabaseRepository);
        generatorData = new GeneratorData();
    }

    @Test
    public void loadStatisticsListSuccess() {
        List<Statistic> statisticList = generatorData.createStatisticList();
        Single<List<Statistic>> single = Single.just(statisticList);
        when(habitsDatabaseRepository.loadStatisticList()).thenReturn(single);

        loadStatisticListInteractor.loadStatisticsList()
                .test()
                .assertNoErrors()
                .assertValue(statisticList);

        verify(habitsDatabaseRepository).loadStatisticList();
    }

    @Test
    public void loadStatisticsListError() {
        Single<List<Statistic>> single = Single.error(new Exception());
        when(habitsDatabaseRepository.loadStatisticList()).thenReturn(single);

        loadStatisticListInteractor.loadStatisticsList()
                .test()
                .assertError(throwable -> {
                    if (throwable instanceof LoadDbException)
                        return R.string.load_db_exception == ((LoadDbException) throwable).getMessageRes();
                    return false;
                });

        verify(habitsDatabaseRepository).loadStatisticList();
    }
}