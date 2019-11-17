package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.model.TemperatureByStationId;

public interface TemperaturesAtStationService {

    public TemperatureByStationId getTemperaturesBy(int stationsId);

    void getTemperaturesForAll();
}
