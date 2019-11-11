package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.model.ClimateAtStation;
import ch.studer.germanclimatedataanalyser.model.Month;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public class ClimateAtStationServiceImpl implements ClimateAtStationService {

    @Autowired
    MonthService monthService;

    @Value("climate.calculation.period.year")
    int period;

    private static final Logger log = LoggerFactory.getLogger(ClimateAtStationServiceImpl.class);

    @Override
    public ClimateAtStation getClimateDataBy(int stationsId) {


        ClimateAtStation climateAtStation = new ClimateAtStation(stationsId);

        // Get all Months Records ordered by EndDate desc for a Climate Period (30 years)
        List<Month> months = monthService.getMonthsByIdOrderDesc(stationsId);

        // calculate the average Temperature for every Month
        // Don't forget to proof if all Data are available
        for(Month month : months){

            log.debug(month.toString());



        }

        // Calculate an averag temperature for holes



        return climateAtStation;
    }
}
