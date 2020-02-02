package ru.sergeykozhukhov.habits.notes.database.habit;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import ru.sergeykozhukhov.habits.notes.database.utils.DateConverter;

@Entity // (foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "id_user", onDelete = CASCADE))
public class Habit  implements Parcelable {

    @Expose(serialize = false, deserialize = false)
    @PrimaryKey(autoGenerate = true)
    private long id_habit;

    @SerializedName("id_habit")
    @Expose
    @ColumnInfo
    private long id_habit_server;
    /*@ColumnInfo(index = true)
    private long id_user;*/

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("date_start")
    @Expose
    @TypeConverters({DateConverter.class})
    private Date date_start;

    @SerializedName("duration")
    @Expose
    private int duration;

    @Expose
    private long level;

    @Expose
    private boolean deleted;

    public Habit() {
    }

    @Ignore
    public Habit(String title, String description, Date date_start, int duration) {
        this.title = title;
        this.description = description;
        this.date_start = date_start;
        this.duration = duration;
    }



    public long getId_habit() {
        return id_habit;
    }

    public void setId_habit(long id_habit) {
        this.id_habit = id_habit;
    }

    public long getId_habit_server() {
        return id_habit_server;
    }

    public void setId_habit_server(long id_habit_server) {
        this.id_habit_server = id_habit_server;
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

    public Date getDate_start() {
        return date_start;
    }

    public void setDate_start(Date date_start) {
        this.date_start = date_start;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }


    @Override
    public String toString() {
        return "HabitData{" +
                "id_habit=" + id_habit +
                ", id_habit_server=" + id_habit_server +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date_start=" + date_start +
                ", duration=" + duration +
                ", level=" + level +
                ", deleted=" + deleted +
                '}';
    }

    protected Habit(Parcel in) {
        id_habit = in.readLong();
        id_habit_server = in.readLong();
        title = in.readString();
        description = in.readString();
        date_start.setTime(in.readLong());
        duration = in.readInt();
        level = in.readLong();
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
        dest.writeLong(id_habit);
        dest.writeLong(id_habit_server);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeLong(date_start.getTime());
        dest.writeInt(duration);
        dest.writeLong(level);
    }
}