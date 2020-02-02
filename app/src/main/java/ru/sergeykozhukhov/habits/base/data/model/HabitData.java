package ru.sergeykozhukhov.habits.base.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import ru.sergeykozhukhov.habits.base.data.DateConverter;


@Entity (tableName = "habits")
public class HabitData {

    @ColumnInfo(name = "id_habit")
    @PrimaryKey(autoGenerate = true)
    private long idHabit;

    @SerializedName("id_habit")
    @ColumnInfo(name = "id_habit_server")
    private long idHabitServer;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "start_date")
    @TypeConverters({DateConverter.class})
    private Date startDate;

    @SerializedName("duration")
    private int duration;

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
}
