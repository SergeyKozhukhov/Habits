package ru.sergeykozhukhov.habits.data.converter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.sergeykozhukhov.habits.domain.IConverter;
import ru.sergeykozhukhov.habits.model.data.ProgressData;
import ru.sergeykozhukhov.habits.model.domain.Progress;

public class ProgressListConverter implements IConverter<List<ProgressData>, List<Progress>> {

    private final ProgressConverter progressConverter;

    public ProgressListConverter(ProgressConverter progressConverter) {
        this.progressConverter = progressConverter;
    }

    @NonNull
    @Override
    public List<Progress> convertTo(@NonNull List<ProgressData> progressDataList) {

        List<Progress> progressList = new ArrayList<>(progressDataList.size());
        for (ProgressData progressData: progressDataList){
            progressList.add(progressConverter.convertTo(progressData));
        }
        return progressList;

    }

    @NonNull
    @Override
    public List<ProgressData> convertFrom(@NonNull List<Progress> progressList) {
        List<ProgressData> progressDataList = new ArrayList<>(progressList.size());
        for(Progress progress : progressList){
            progressDataList.add(progressConverter.convertFrom(progress));
        }
        return progressDataList;
    }
}