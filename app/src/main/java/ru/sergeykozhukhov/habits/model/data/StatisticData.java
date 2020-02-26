package ru.sergeykozhukhov.habits.model.data;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;

import java.util.Objects;


/**
 * Класс, содержащий информацию о количестве выполненных дней для достижения цели по конкретной привычке (data слой)
 */
public class StatisticData {

    /**
     * id привычки
     */
    @ColumnInfo(name = "id_habit")
    private long idHabit;

    /**
     * Название привычки
     */
    @ColumnInfo(name = "title")
    private String title;

    /**
     * Продолжительность привычки
     */
    @ColumnInfo(name = "duration")
    private int duration;

    /**
     * Колличество дней выполения
     */
    @ColumnInfo(name = "current_quantity")
    private int currentQuantity;

    @Ignore
    public StatisticData(String title, int duration, int currentQuantity) {
        this.title = title;
        this.duration = duration;
        this.currentQuantity = currentQuantity;
    }

    public StatisticData(long idHabit, String title, int duration, int currentQuantity) {
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
        StatisticData that = (StatisticData) o;
        return idHabit == that.idHabit &&
                duration == that.duration &&
                currentQuantity == that.currentQuantity &&
                title.equals(that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idHabit, title, duration, currentQuantity);
    }

    @Override
    public String toString() {
        return "StatisticData{" +
                "idHabit=" + idHabit +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                ", currentQuantity=" + currentQuantity +
                '}';
    }
}
