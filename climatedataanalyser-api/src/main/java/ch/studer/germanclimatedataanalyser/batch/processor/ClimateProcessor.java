package ch.studer.germanclimatedataanalyser.batch.processor;

import ch.studer.germanclimatedataanalyser.model.database.StationWeatherPerYear;
import org.springframework.batch.item.ItemProcessor;

public class ClimateProcessor implements ItemProcessor<StationWeatherPerYear, StationWeatherPerYear> {
    @Override
    public StationWeatherPerYear process(StationWeatherPerYear stationWeatherPerYear) throws Exception {
        // till now, there is nothing to convert !
        return stationWeatherPerYear;
    }
}
