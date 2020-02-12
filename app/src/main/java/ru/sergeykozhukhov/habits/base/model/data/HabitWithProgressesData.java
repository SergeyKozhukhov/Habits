package ru.sergeykozhukhov.habits.base.model.data;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HabitWithProgressesData {

    @SerializedName("habit")
    @Expose
    @Embedded
    private HabitData habitData;

    @SerializedName("progresses")
    @Expose
    @Relation(parentColumn = "id_habit", entityColumn = "id_habit")
    private List<ProgressData> progressDataList;


    public HabitData getHabitData() {
        return habitData;
    }

    public void setHabitData(HabitData habitData) {
        this.habitData = habitData;
    }

    public List<ProgressData> getProgressDataList() {
        return progressDataList;
    }

    public void setProgressDataList(List<ProgressData> progressDataList) {
        this.progressDataList = progressDataList;
    }

    private String getDescr(){
        StringBuilder s = new StringBuilder();
        for (ProgressData progressData : progressDataList){
            s.append(progressData.toString());
        }
        return s.toString();
    }

    @Override
    public String toString() {
        return "HabitWithProgressesData{" +
                "habitData=" + habitData.toString() +
                ", progressDataList=" + getDescr() +
                '}';
    }
}
