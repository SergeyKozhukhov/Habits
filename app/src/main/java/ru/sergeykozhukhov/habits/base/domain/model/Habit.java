package ru.sergeykozhukhov.habits.base.domain.model;

import java.util.Date;
import java.util.Objects;

public class Habit {

    private long idHabit;
    private long idHabitServer;
    private String title;
    private String description;
    private Date startDate;
    private int duration;

    public Habit() {
    }

    public Habit(long idHabitServer, String title, String description, Date startDate, int duration) {
        this.idHabitServer = idHabitServer;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.duration = duration;
    }

    public Habit(long idHabit, long idHabitServer, String title, String description, Date startDate, int duration) {
        this.idHabit = idHabit;
        this.idHabitServer = idHabitServer;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.duration = duration;
    }

    public Habit copy(){
        return new Habit(
                this.idHabit,
                this.idHabitServer,
                this.title,
                this.description,
                this.startDate,
                this.duration
        );
    }

    public long getIdHabit() {
        return idHabit;
    }

    public void setIdHabit(long idHabit) {
        this.idHabit = idHabit;
    }

    public long getIdHabitServer() {
        return idHabitServer;
    }

    public void setIdHabitServer(long idHabitServer) {
        this.idHabitServer = idHabitServer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Habit habit = (Habit) o;
        return idHabit == habit.idHabit &&
                idHabitServer == habit.idHabitServer &&
                duration == habit.duration &&
                title.equals(habit.title) &&
                Objects.equals(description, habit.description) &&
                startDate.equals(habit.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idHabit, idHabitServer, title, description, startDate, duration);
    }

    @Override
    public String toString() {
        return "Habit{" +
                "idHabit=" + idHabit +
                ", idHabitServer=" + idHabitServer +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", duration=" + duration +
                '}';
    }
}
