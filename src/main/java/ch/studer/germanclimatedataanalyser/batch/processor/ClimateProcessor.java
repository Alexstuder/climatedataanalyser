package ch.studer.germanclimatedataanalyser.batch.processor;

import ch.studer.germanclimatedataanalyser.model.database.StationTemperature;
import org.springframework.batch.item.ItemProcessor;

public class ClimateProcessor implements ItemProcessor<StationTemperature, StationTemperature> {
    @Override
    public StationTemperature process(StationTemperature stationTemperature) throws Exception {
        // till now, there is nothing to convert !
        return stationTemperature;
    }
}
