package ru.sergeykozhukhov.habits.model.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.GeneratorData;
import ru.sergeykozhukhov.habits.model.domain.HabitWithProgresses;
import ru.sergeykozhukhov.habits.model.domain.Progress;
import ru.sergeykozhukhov.habits.model.domain.exception.BuildException;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class HabitWithProgressesDataTest {

    private GeneratorData generatorData;
    private SimpleDateFormat simpleDateFormat;

    private Gson gson;

    @Before
    public void setUp() {

        String pattern = "dd-MM-yyyy";

        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        builder.setDateFormat(pattern);
        gson = builder.create();

        simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());

        generatorData = new GeneratorData();
    }

    @Test
    public void deserializeConfidentialityJsonSuccess(){

        /*String jsonInput = "[{\"habit\":{\"id_habit\":100,\"title\":\"title0\",\"description\":\"description0\",\"date_start\":\"01-01-1970\",\"duration\":10},\"progresses\":[{\"idProgress\":0,\"id_progress\":100,\"date\":\"01-01-1970\"},{\"idProgress\":1,\"id_progress\":100,\"date\":\"01-01-1970\"},{\"idProgress\":2,\"id_progress\":100,\"date\":\"01-01-1970\"}]},{\"habit\":{\"id_habit\":100,\"title\":\"title0\",\"description\":\"description0\",\"date_start\":\"01-01-1970\",\"duration\":10},\"progresses\":[{\"idProgress\":0,\"id_progress\":100,\"date\":\"01-01-1970\"},{\"idProgress\":1,\"id_progress\":100,\"date\":\"01-01-1970\"},{\"idProgress\":2,\"id_progress\":100,\"date\":\"01-01-1970\"}]}]";
        List<HabitWithProgressesData> habitWithProgressesDataListExpected = generatorData.createHabitWithProgressesDataList();

        Type targetClassType = new TypeToken<List<HabitWithProgressesData>>() { }.getType();

        List<HabitWithProgressesData> targetCollectionOutput = gson.fromJson(jsonInput, targetClassType);
        //assertThat(targetCollectionOutput, instanceOf(ArrayList.class));
        assertThat(targetCollectionOutput, is(habitWithProgressesDataListExpected));*/
    }

    @Test
    public void serializeConfidentialityJsonSuccess() {

        List<HabitWithProgressesData> habitWithProgressesDataList = generatorData.createHabitWithProgressesDataList();

        String jsonOutput = gson.toJson(habitWithProgressesDataList);

        String jsonExpected = "[{\"habit\":" +
                "{\"id_habit\":101,\"title\":\"title1\",\"description\":\"description1\",\"date_start\":\"01-01-1970\",\"duration\":11}," +
                "\"progresses\":" +
                "[{\"idProgress\":1,\"id_progress\":101,\"date\":\"01-01-1970\"}," +
                "{\"idProgress\":2,\"id_progress\":101,\"date\":\"01-01-1970\"}," +
                "{\"idProgress\":3,\"id_progress\":101,\"date\":\"01-01-1970\"}]}," +
                "{\"habit\":" +
                "{\"id_habit\":102,\"title\":\"title2\",\"description\":\"description2\",\"date_start\":\"01-01-1970\",\"duration\":12}," +
                "\"progresses\":" +
                "[{\"idProgress\":4,\"id_progress\":104,\"date\":\"01-01-1970\"}," +
                "{\"idProgress\":5,\"id_progress\":104,\"date\":\"01-01-1970\"}," +
                "{\"idProgress\":6,\"id_progress\":104,\"date\":\"01-01-1970\"}]}]";

        assertThat(jsonOutput, is(jsonExpected));

       /* StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < habitWithProgressesDataList.size(); i++) {
            stringBuilder.append("[{\"habit\":");

            HabitData habitData = habitWithProgressesDataList.get(i).getHabitData();
            stringBuilder.append("{\"id_habit\":");
            stringBuilder.append(habitData.getIdHabitServer());
            stringBuilder.append(",\"title\":");
            stringBuilder.append(habitData.getTitle());
            stringBuilder.append(",\"description\":");
            stringBuilder.append(habitData.getDescription());
            stringBuilder.append(",\"date_start\":");
            stringBuilder.append(simpleDateFormat.format(habitData.getStartDate()));
            stringBuilder.append(",\"duration\"},");
            stringBuilder.append(habitData.getDuration());

            stringBuilder.append("\"progresses\":[");

            List<ProgressData> progressDataList = habitWithProgressesDataList.get(i).getProgressDataList();
            if (!progressDataList.isEmpty()){
                for (int j = 0; j<progressDataList.size(); j++){
                    stringBuilder.append("\"{idProgress\":");
                    stringBuilder.append(progressDataList.get(i).getIdProgress());
                    stringBuilder.append(",\"id_progress\":");
                    stringBuilder.append(progressDataList.get(i).getIdProgressServer());
                    stringBuilder.append(",\"date\"}");
                    stringBuilder.append(simpleDateFormat.format(progressDataList.get(i).getDate()));
                    if (j<progressDataList.size()-1)
                        stringBuilder.append(",");
                    else
                        stringBuilder.append("]");
                }
            }
            stringBuilder.append("}]}");
            if (i == habitWithProgressesDataList.size()-1){
                stringBuilder.append("]}]");
            }
        }*/


        // assertThat(jsonOutput, is(stringBuilder.toString()));
    }
}