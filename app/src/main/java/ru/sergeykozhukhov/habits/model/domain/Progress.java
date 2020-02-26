package ru.sergeykozhukhov.habits.model.domain;

import java.util.Date;
import java.util.Objects;

/**
 * Класс, содержащий информацию о дне выполнения конкретной привычки (domain слой)
 */
public class Progress {

    /**
     * id даты выполнения в базе данных
     */
    private long idProgress;

    /**
     * id даты выполения на сервере
     */
    private long idProgressServer;

    /**
     * id привычки, к которой относиться эта дата
     */
    private long idHabit;

    /**
     * Дата выполнения
     */
    private Date date;

    public Progress(long idHabit, Date date) {
        this.idHabit = idHabit;
        this.date = date;
    }

    public Progress(long idProgress, long idProgressServer, long idHabit, Date date) {
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
        Progress progress = (Progress) o;
        return idProgress == progress.idProgress &&
                idProgressServer == progress.idProgressServer &&
                idHabit == progress.idHabit &&
                date.equals(progress.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProgress, idProgressServer, idHabit, date);
    }

    @Override
    public String toString() {
        return "Progress{" +
                "idProgress=" + idProgress +
                ", idProgressServer=" + idProgressServer +
                ", idHabit=" + idHabit +
                ", date=" + date +
                '}';
    }
}
