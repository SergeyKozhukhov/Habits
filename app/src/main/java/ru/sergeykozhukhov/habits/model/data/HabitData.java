package ru.sergeykozhukhov.habits.model.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Objects;

import ru.sergeykozhukhov.habits.data.converter.DateConverter;


/**
 * Класс, содержащий информацию о привычке (data слой)
 */
@Entity (tableName = "habits")
public class HabitData {

    @ColumnInfo(name = "id_habit")
    @PrimaryKey(autoGenerate = true)
    private long idHabit;

    @ColumnInfo(name = "id_habit_server")
    @SerializedName("id_habit")
    @Expose
    private long idHabitServer;

    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Expose
    private String title;

    @ColumnInfo(name = "description")
    @SerializedName("description")
    @Expose
    private String description;

    @ColumnInfo(name = "start_date")
    @TypeConverters({DateConverter.class})
    @SerializedName("date_start")
    @Expose
    private Date startDate;

    @ColumnInfo(name = "duration")
    @SerializedName("duration")
    @Expose
    private int duration;

    public HabitData(long idHabit, long idHabitServer, String title, String description, Date startDate, int duration) {
        this.idHabit = idHabit;
        this.idHabitServer = idHabitServer;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.duration = duration;
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
        HabitData habitData = (HabitData) o;
        return idHabit == habitData.idHabit &&
                idHabitServer == habitData.idHabitServer &&
                duration == habitData.duration &&
                title.equals(habitData.title) &&
                description.equals(habitData.description) &&
                startDate.equals(habitData.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idHabit, idHabitServer, title, description, startDate, duration);
    }

    @Override
    public String toString() {
        return "HabitData{" +
                "idHabit=" + idHabit +
                ", idHabitServer=" + idHabitServer +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", duration=" + duration +
                '}';
    }
}
