package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.model.Month;

import java.util.List;

public interface MonthService {

    void saveAllMonth(List<? extends Month> months);
}
