package ch.studer.germanclimatedataanalyser.batch.writer;

import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.database.StationTemperature;
import ch.studer.germanclimatedataanalyser.model.database.StationWeatherPerYear;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.util.ArrayList;
import java.util.List;

public class ClimateWriter implements ItemWriter<StationWeatherPerYear> {


    private static final Logger LOG = LoggerFactory.getLogger(ClimateWriter.class);

    @Override
    public void write(List<? extends StationWeatherPerYear> list) throws Exception {

        LOG.debug("Hier Log CLimate Writer !!!!");

        // iterate till nex StationID

        int actualStationId = list.get(0).getStationId();

        //Get List to calculate climate to a Station
        List<StationWeatherPerYear> stationWeatherPerYears = new ArrayList<StationWeatherPerYear>();

        for (StationWeatherPerYear stationWeatherPerYear : list){

            //add actual Weather Redcord to the stationWeathers List

            if (actualStationId == stationWeatherPerYear.getStationId()){
                stationWeatherPerYears.add(stationWeatherPerYear);

            } else {
                // fill the holes

                // calculate the climate Record

                // write ClimateRecord to DB

                // get a new WeatherList

                // fill the actual Record to weatherList
            }
            LOG.debug(" " + stationWeatherPerYear.getStationId());


        }
    }
}
