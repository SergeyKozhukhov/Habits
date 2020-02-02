package ru.sergeykozhukhov.habits.base.presentation;

public interface IInsertHabitPresenter {

    void onHabitInserted(Long id);
    void onError();

    class Empty implements IInsertHabitPresenter {

        @Override
        public void onHabitInserted(Long id) {

        }

        @Override
        public void onError() {

        }
    }

}
