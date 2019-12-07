package ch.studer.germanclimatedataanalyser.batch.writer;

import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.database.StationTemperature;
import ch.studer.germanclimatedataanalyser.model.database.StationWeatherPerYear;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ClimateWriter implements ItemWriter<StationWeatherPerYear> {

    @Value("#{new Integer('${climate.calculation.period.year}')}")
    int period;

        final BigDecimal NULL_TEMPERATURE = new BigDecimal(-99.9999);
    private static final Logger LOG = LoggerFactory.getLogger(ClimateWriter.class);

    @Override
    public void write(List<? extends StationWeatherPerYear> list) throws Exception {

        // get the first StaionId
        if(list.size()>0) {
            int actualStationId = list.get(0).getStationId();

            // Transform all WeatherRecords from 1 Station to a new separated List
            // fill all the holes
            // calculate the CLimaRecords
            // Write into DB
            List<StationWeatherPerYear> stationWeatherPerYears = new ArrayList<StationWeatherPerYear>();
            for (StationWeatherPerYear stationWeatherPerYear : list) {

                //add actual Weather Record to the stationWeathers List

                if (actualStationId == stationWeatherPerYear.getStationId()) {
                    stationWeatherPerYears.add(stationWeatherPerYear);

                } else {

                    // fill the holes
                    fillHoles(stationWeatherPerYears);
                    // calculate the climate Record

                    // write ClimateRecord to DB

                    // get a new WeatherList

                    // fill the actual Record to weatherList
                }
                LOG.debug(" " + stationWeatherPerYear.getStationId());

            }
        } else {
            LOG.debug("No WeatherRecords available to calculate Climate Records. List.size = {} ",list.size());
        }
    }

    private void fillHoles(List<StationWeatherPerYear> stationWeatherPerYears) {

        //Iterate trough List and get the hole
        for(int i = 0 ; i < stationWeatherPerYears.size();i++ ){

            if(stationWeatherPerYears.get(i).getJanuar().equals(NULL_TEMPERATURE)){stationWeatherPerYears.get(i).setJanuar(getAverageTemperature("Januar",i));}
        }
        // Get the 14 Temperature before and after the Date and get the average of the temperatures


    }

    private BigDecimal getAverageTemperature(String januar, int i) {

        BigDecimal averageTemp = NULL_TEMPERATURE;
        // Get Temperature from Month with Index !
        // TODO Get the algo for average !
        return averageTemp;
    }
}
