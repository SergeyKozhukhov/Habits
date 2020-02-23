package ru.sergeykozhukhov.habits.model.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * Класс, содержащий информацию о привычке (domain слой)
 */
public class Habit implements Parcelable {

    private long idHabit;
    private long idHabitServer;
    private String title;
    private String description;
    private Date startDate;
    private int duration;

    public Habit() {
    }

    public Habit(String title, String description, Date startDate, int duration) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.duration = duration;
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

    public Calendar getStartDateCalendar(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        return calendar;
    }

    public Calendar getEndDateCalendar(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DATE, duration);
        return calendar;
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

    public Date getEndDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DATE, duration);
        return calendar.getTime();
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


    protected Habit(Parcel in) {
        idHabit = in.readLong();
        idHabitServer = in.readLong();
        title = in.readString();
        description = in.readString();
        startDate.setTime(in.readLong());
        duration = in.readInt();
    }

    public static final Creator<Habit> CREATOR = new Creator<Habit>() {
        @Override
        public Habit createFromParcel(Parcel in) {
            return new Habit(in);
        }

        @Override
        public Habit[] newArray(int size) {
            return new Habit[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(idHabit);
        dest.writeLong(idHabitServer);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeLong(startDate.getTime());
        dest.writeInt(duration);
    }
}
