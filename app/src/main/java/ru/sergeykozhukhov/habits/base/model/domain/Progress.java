package ru.sergeykozhukhov.habits.base.model.domain;

import java.util.Date;

public class Progress {

    private long idProgress;
    private long idProgressServer;
    private long idHabit;
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
    public String toString() {
        return "Progress{" +
                "idProgress=" + idProgress +
                ", idProgressServer=" + idProgressServer +
                ", idHabit=" + idHabit +
                ", date=" + date +
                '}';
    }
}
