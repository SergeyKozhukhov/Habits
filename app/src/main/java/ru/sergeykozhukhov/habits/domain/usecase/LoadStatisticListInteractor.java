package ru.sergeykozhukhov.habits.domain.usecase;

import androidx.annotation.NonNull;

import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.domain.IInreractor.ILoadStatisticListInteractor;
import ru.sergeykozhukhov.habits.model.domain.Chart;
import ru.sergeykozhukhov.habits.model.domain.Statistic;
import ru.sergeykozhukhov.habits.model.exception.LoadDbException;

public class LoadStatisticListInteractor implements ILoadStatisticListInteractor {

    private final IHabitsDatabaseRepository habitsRepository;

    private static final int COLOR_YELLOW = 0xFFFFFF00;
    private static final int COLOR_GOLD = 0xFFFFD700;
    private static final int COLOR_ORANGE = 0xFFFFA500;

    //private List<Float>

    public LoadStatisticListInteractor(IHabitsDatabaseRepository habitsRepository) {
        this.habitsRepository = habitsRepository;
    }

    @NonNull
    @Override
    public Single<List<Statistic>> loadStatisticsList() {
        return habitsRepository.loadStatisticList()
                .onErrorResumeNext(throwable ->
                        Single.error(new LoadDbException(R.string.load_db_exception, throwable)));
    }

   /* public Single<List<Bar>> loadChartList(){

        //Add a list of bar entries
        List<BarEntry> entries = new ArrayList<>();

        List<Integer> listColor = new ArrayList<>(statisticList.size());

        float indexX = 0f;
        for (Statistic statistic : statisticList) {
            float percent = 100f * (float) statistic.getCurrentQuantity() / (float) (statistic.getDuration());
            entries.add(new BarEntry(indexX, percent));
            int color = 0;
            if (percent < 33.3f) {
                //color = R.color.orange;
                color = COLOR_ORANGE;
            } else if (percent > 66.6f) {
                //color = R.color.green;
                color = COLOR_YELLOW;
            } else {
                //color = R.color.yellow;
                color = COLOR_GOLD;
            }
            //listColor.add(requireContext().getResources().getColor(color));
            listColor.add(color);
            indexX = indexX + 1.0f;
        }


        return null;
    }*/
}
