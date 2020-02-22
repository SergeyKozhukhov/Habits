package ru.sergeykozhukhov.habits.model.domain;

import java.util.Objects;

public class Chart {

    private long idHabit;
    private String title;
    private float percent;

    public Chart(long idHabit, String title, float percent) {
        this.idHabit = idHabit;
        this.title = title;
        this.percent = percent;
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

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chart chart = (Chart) o;
        return idHabit == chart.idHabit &&
                Float.compare(chart.percent, percent) == 0 &&
                title.equals(chart.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idHabit, title, percent);
    }

    @Override
    public String toString() {
        return "Chart{" +
                "idHabit=" + idHabit +
                ", title='" + title + '\'' +
                ", percent=" + percent +
                '}';
    }
}
