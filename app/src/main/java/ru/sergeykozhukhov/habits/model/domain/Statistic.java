package ru.sergeykozhukhov.habits.model.domain;

import java.util.Objects;

/**
 * Класс, содержащий информацию о количестве выполненных дней для достижения цели по конкретной привычке (domain слой)
 */
public class Statistic {

    /**
     * id привычки
     */
    private long idHabit;

    /**
     * Название привычки
     */
    private String title;

    /**
     * Продолжительность привычки
     */
    private int duration;

    /**
     * Колличество дней выполения
     */
    private int currentQuantity;

    public Statistic(String title, int duration, int currentQuantity) {
        this.title = title;
        this.duration = duration;
        this.currentQuantity = currentQuantity;
    }

    public Statistic(long idHabit, String title, int duration, int currentQuantity) {
        this.idHabit = idHabit;
        this.title = title;
        this.duration = duration;
        this.currentQuantity = currentQuantity;
    }

    public long getIdHabit() {
        return idHabit;
    }

    public void setIdHabit(long idHabit) {
        this.idHabit = idHabit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(int currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statistic statistic = (Statistic) o;
        return idHabit == statistic.idHabit &&
                duration == statistic.duration &&
                currentQuantity == statistic.currentQuantity &&
                title.equals(statistic.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idHabit, title, duration, currentQuantity);
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "idHabit=" + idHabit +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                ", currentQuantity=" + currentQuantity +
                '}';
    }
}
