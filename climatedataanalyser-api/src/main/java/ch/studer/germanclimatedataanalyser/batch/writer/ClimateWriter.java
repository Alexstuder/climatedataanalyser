package ch.studer.germanclimatedataanalyser.batch.writer;

import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.database.StationWeatherPerYear;
import ch.studer.germanclimatedataanalyser.service.db.ClimateService;
import ch.studer.germanclimatedataanalyser.service.db.StationWeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

public class ClimateWriter implements ItemWriter<StationWeatherPerYear> {

    @Autowired
    ClimateService climateService;

    @Autowired
    StationWeatherService stationWeatherService;

    @Value("#{new Integer('${climate.calculation.period.year}')}")
    int period;

    private static final Logger LOG = LoggerFactory.getLogger(ClimateWriter.class);

    @Override
    public void write(List<? extends StationWeatherPerYear> list) {

        if (list.size() > 0) {

            List<StationWeatherPerYear> stationWeatherPerYearsGroupedByStationID = new ArrayList<StationWeatherPerYear>();
            int actualStationId = list.get(0).getStationID();

            for (StationWeatherPerYear stationWeatherPerYear : list) {

                if (stationWeatherPerYear.getStationId() == actualStationId) {
                    stationWeatherPerYearsGroupedByStationID.add(stationWeatherPerYear);
                } else {

                    // build the climate record ; if we have enough weather record
                    if (stationWeatherPerYearsGroupedByStationID.size() >= period) {

                        List<StationWeatherPerYear> stationWeatherPerYearsFilledHoles = stationWeatherService.fillHoles(stationWeatherPerYearsGroupedByStationID);
                        List<StationClimate> stationClimates = climateService.getClimateForStation(stationWeatherPerYearsFilledHoles);

                        if (stationClimates.size() > 0) {
                            climateService.saveAllClimateAtStationId(stationClimates);
                        } else {
                            LOG.debug("There was nothing to persist on database! ");
                        }
                    }

                    stationWeatherPerYearsGroupedByStationID = new ArrayList<StationWeatherPerYear>();
                    stationWeatherPerYearsGroupedByStationID.add(stationWeatherPerYear);
                    actualStationId = stationWeatherPerYear.getStationID();

                }
            }
        }
    }
}
