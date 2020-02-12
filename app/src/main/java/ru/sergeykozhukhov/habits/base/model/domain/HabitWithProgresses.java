package ru.sergeykozhukhov.habits.base.model.domain;

import java.util.List;

public class HabitWithProgresses {

    private Habit habit;
    private List<Progress> progressList;

    public HabitWithProgresses(Habit habit, List<Progress> progressList) {
        this.habit = habit;
        this.progressList = progressList;
    }

    public Habit getHabit() {
        return habit;
    }

    public void setHabit(Habit habit) {
        this.habit = habit;
    }

    public List<Progress> getProgressList() {
        return progressList;
    }

    public void setProgressList(List<Progress> progressList) {
        this.progressList = progressList;
    }

    @Override
    public String toString() {
        return "HabitWithProgresses{" +
                "habit=" + habit +
                ", progressList=" + progressList +
                '}';
    }
}
