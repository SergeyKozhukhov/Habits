package ru.sergeykozhukhov.habits.model.domain;

import java.util.List;
import java.util.Objects;

/**
 * Класс, содержащий информация о привычке и список дат ее выполнения (domain слой)
 */
public class HabitWithProgresses {

    /**
     * Привычка
     */
    private Habit habit;

    /**
     * Список дней выполнения
     */
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HabitWithProgresses that = (HabitWithProgresses) o;
        return habit.equals(that.habit) &&
                Objects.equals(progressList, that.progressList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(habit, progressList);
    }

    @Override
    public String toString() {
        return "HabitWithProgresses{" +
                "habit=" + habit +
                ", progressList=" + progressList +
                '}';
    }
}
