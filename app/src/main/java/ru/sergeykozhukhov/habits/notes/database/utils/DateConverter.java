package ru.sergeykozhukhov.habits.notes.database.utils;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {

    @TypeConverter
    public long fromDate(Date date){
        return date.getTime();
    }

    @TypeConverter
    public Date toDate(long date){
        return new Date(date);
    }
}
