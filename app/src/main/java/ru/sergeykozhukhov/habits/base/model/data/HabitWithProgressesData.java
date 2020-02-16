package ru.sergeykozhukhov.habits.base.model.data;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

/**
 * Класс, содержащий информация о привычке и список дат ее выполнения (data слой)
 */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HabitWithProgressesData that = (HabitWithProgressesData) o;
        return habitData.equals(that.habitData) &&
                Objects.equals(progressDataList, that.progressDataList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(habitData, progressDataList);
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
