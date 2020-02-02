package ru.sergeykozhukhov.habits.notes.database.progress;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.sergeykozhukhov.habits.notes.database.habit.Habit;
import ru.sergeykozhukhov.habits.notes.database.utils.DateConverter;

import static androidx.room.ForeignKey.CASCADE;

@Entity (foreignKeys = @ForeignKey(entity = Habit.class, parentColumns = "id_habit", childColumns = "id_habit", onDelete = CASCADE))
public class Progress {

    @Expose
    @PrimaryKey(autoGenerate = true)
    private long id_progress;

    @Expose
    @ColumnInfo(index = true)
    private long id_habit;

    @Expose
    @TypeConverters({DateConverter.class})
    private Date date_completion;

    public Progress() {
    }

    public long getId_progress() {
        return id_progress;
    }

    public void setId_progress(long id_progress) {
        this.id_progress = id_progress;
    }

    public long getId_habit() {
        return id_habit;
    }

    public void setId_habit(long id_habit) {
        this.id_habit = id_habit;
    }

    public Date getDate_completion() {
        return date_completion;
    }

    public void setDate_completion(Date date_completion) {
        this.date_completion = date_completion;
    }

    @NonNull
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy", // шаблон форматирования
                Locale.getDefault() // язык отображения (получение языка по-умолчанию)
        );

        return dateFormat.format(date_completion);
    }
}
