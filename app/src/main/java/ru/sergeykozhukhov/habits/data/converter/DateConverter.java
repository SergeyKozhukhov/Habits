package ru.sergeykozhukhov.habits.data.converter;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * Конвертер даты для чтения/записи Date параметра из/в БД
 */
public class DateConverter {

    /**
     * Конвертация в формат доступный базе данных
     *
     * @param date дата в Date представлении
     * @return колличество миллисекунд (unix представление даты)
     */
    @TypeConverter
    public long fromDate(Date date) {
        return date.getTime();
    }

    /**
     * Конвертация в Date представление даты из базы данных
     *
     * @param date колличество миллисекунд (unix представление даты)
     * @return дата в Date представлении
     */
    @TypeConverter
    public Date toDate(long date) {
        return new Date(date);
    }
}
