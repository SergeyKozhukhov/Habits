package ru.sergeykozhukhov.habits.base.presentation;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.concurrent.Executors;

import ru.sergeykozhukhov.habits.base.data.HabitConverter;
import ru.sergeykozhukhov.habits.base.data.HabitsConverter;
import ru.sergeykozhukhov.habits.base.data.repository.HabitsRepository;
import ru.sergeykozhukhov.habits.base.domain.IHabitsRepository;
import ru.sergeykozhukhov.habits.base.domain.usecase.InsertHabitInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.LoadHabitsInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.UpdateHabitInteractor;

public class HabitsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private Context context;

    public HabitsViewModelFactory(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if(HabitsViewModel.class.equals(modelClass)){
            IHabitsRepository habitsRepository = new HabitsRepository(
                    context, Executors.newSingleThreadExecutor(),
                    new HabitConverter(), new HabitsConverter());

            LoadHabitsInteractor loadHabitsInteractor = new LoadHabitsInteractor(habitsRepository);
            InsertHabitInteractor insertHabitInteractor = new InsertHabitInteractor(habitsRepository);
            UpdateHabitInteractor updateHabitInteractor = new UpdateHabitInteractor(habitsRepository);

            // noinspection unchecked
            return (T) new HabitsViewModel(
                    loadHabitsInteractor,
                    insertHabitInteractor,
                    updateHabitInteractor);
        }
        else{
            return super.create(modelClass);
        }
    }
}
