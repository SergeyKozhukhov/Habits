package ru.sergeykozhukhov.habits.base.presentation;

import java.util.List;

import ru.sergeykozhukhov.habits.base.domain.model.Habit;


public interface LoadHabitsView {

    void onHabitsLoaded(List<Habit> habitList);
    void onError();

    class Empty implements LoadHabitsView{

        @Override
        public void onHabitsLoaded(List<Habit> habitList) {

        }

        @Override
        public void onError() {

        }
    }
}
