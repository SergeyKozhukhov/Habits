package ru.sergeykozhukhov.habits.model.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Objects;

import ru.sergeykozhukhov.habits.data.converter.DateConverter;

import static androidx.room.ForeignKey.CASCADE;

/**
 * Класс, содержащий информацию о дне выполнения конкретной привычки (data слой)
 */
@Entity(tableName = "progresses", foreignKeys = @ForeignKey(entity = HabitData.class, parentColumns = "id_habit", childColumns = "id_habit", onDelete = CASCADE))
public class ProgressData {

    /**
     * id даты выполнения в базе данных
     */
    @ColumnInfo(name = "id_progress")
    @PrimaryKey(autoGenerate = true)
    @Expose
    private long idProgress;

    /**
     * id даты выполения на сервере
     */
    @ColumnInfo(name = "id_progress_server")
    @SerializedName("id_progress")
    @Expose
    private long idProgressServer;

    /**
     * id привычки, к которой относиться эта дата
     */
    @ColumnInfo(name = "id_habit", index = true)
    private long idHabit;

    /**
     * Дата выполнения
     */
    @ColumnInfo(name = "date")
    @TypeConverters({DateConverter.class})
    @SerializedName("date")
    @Expose
    private Date date;

    @Ignore
    public ProgressData(long idHabit, Date date) {
        this.idHabit = idHabit;
        this.date = date;
    }

    public ProgressData(long idProgress, long idProgressServer, long idHabit, Date date) {
        this.idProgress = idProgress;
        this.idProgressServer = idProgressServer;
        this.idHabit = idHabit;
        this.date = date;
    }

    public long getIdProgress() {
        return idProgress;
    }

    public void setIdProgress(long idProgress) {
        this.idProgress = idProgress;
    }

    public long getIdProgressServer() {
        return idProgressServer;
    }

    public void setIdProgressServer(long idProgressServer) {
        this.idProgressServer = idProgressServer;
    }

    public long getIdHabit() {
        return idHabit;
    }

    public void setIdHabit(long idHabit) {
        this.idHabit = idHabit;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProgressData that = (ProgressData) o;
        return idProgress == that.idProgress &&
                idProgressServer == that.idProgressServer &&
                idHabit == that.idHabit &&
                date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProgress, idProgressServer, idHabit, date);
    }

    @Override
    public String toString() {
        return "ProgressData{" +
                "idProgress=" + idProgress +
                ", idProgressServer=" + idProgressServer +
                ", idHabit=" + idHabit +
                ", date=" + date +
                '}';
    }
}

