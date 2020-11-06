package ch.studer.germanclimatedataanalyser.service.db;

import ch.studer.germanclimatedataanalyser.dao.StationClimateDAO;
import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.database.StationWeatherPerYear;
import ch.studer.germanclimatedataanalyser.model.dto.helper.GpsPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


public class ClimateServiceImpl implements ClimateService {


    @Value("#{new Integer('${climate.calculation.period.year}')}")
    int period;

    @Autowired
    StationClimateDAO stationClimateDAO;


    private static final Logger LOG = LoggerFactory.getLogger(ClimateServiceImpl.class);


    @Override
    public List<StationClimate> getClimateForStation(List<StationWeatherPerYear> stationWeatherPerYears) {
        List<StationClimate> stationClimates = new ArrayList<StationClimate>();

        int start = 0;
        int end = start + period;

        for (StationWeatherPerYear stationWeatherPerYear : stationWeatherPerYears) {

            if (end <= stationWeatherPerYears.size()) {

                StationClimate stationClimate = new StationClimate(stationWeatherPerYears.get(start).getStationID());
                stationClimate.setEndPeriod(stationWeatherPerYears.get(start).getYear());
                stationClimate.setStartPeriod(stationWeatherPerYears.get(end - 1).getYear());

                // Cause we don't start with NULL ,we have to init with zero !
                stationClimate.setJanuar(new BigDecimal(0));
                stationClimate.setFebruar(new BigDecimal(0));
                stationClimate.setMaerz(new BigDecimal(0));
                stationClimate.setApril(new BigDecimal(0));
                stationClimate.setMai(new BigDecimal(0));
                stationClimate.setJuni(new BigDecimal(0));
                stationClimate.setJuli(new BigDecimal(0));
                stationClimate.setAugust(new BigDecimal(0));
                stationClimate.setSeptember(new BigDecimal(0));
                stationClimate.setOktober(new BigDecimal(0));
                stationClimate.setNovember(new BigDecimal(0));
                stationClimate.setDezember(new BigDecimal(0));

                for (int i = start; i < end; i++) {

                    stationClimate.setJanuar(stationClimate.getJanuar().add(stationWeatherPerYears.get(i).getJanuar()));
                    stationClimate.setFebruar(stationClimate.getFebruar().add(stationWeatherPerYears.get(i).getFebruar()));
                    stationClimate.setMaerz(stationClimate.getMaerz().add(stationWeatherPerYears.get(i).getMaerz()));
                    stationClimate.setApril(stationClimate.getApril().add(stationWeatherPerYears.get(i).getApril()));
                    stationClimate.setMai(stationClimate.getMai().add(stationWeatherPerYears.get(i).getMai()));
                    stationClimate.setJuni(stationClimate.getJuni().add(stationWeatherPerYears.get(i).getJuni()));
                    stationClimate.setJuli(stationClimate.getJuli().add(stationWeatherPerYears.get(i).getJuli()));
                    stationClimate.setAugust(stationClimate.getAugust().add(stationWeatherPerYears.get(i).getAugust()));
                    stationClimate.setSeptember(stationClimate.getSeptember().add(stationWeatherPerYears.get(i).getSeptember()));
                    stationClimate.setOktober(stationClimate.getOktober().add(stationWeatherPerYears.get(i).getOktober()));
                    stationClimate.setNovember(stationClimate.getNovember().add(stationWeatherPerYears.get(i).getNovember()));
                    stationClimate.setDezember(stationClimate.getDezember().add(stationWeatherPerYears.get(i).getDezember()));
                }

                // Divide trough Years (Period)
                //stationClimate.setJanuar(januarClimate.divide(BigDecimal.valueOf(period),3 , RoundingMode.HALF_UP));
                stationClimate.setJanuar(stationClimate.getJanuar().divide(BigDecimal.valueOf(period), 3, RoundingMode.HALF_DOWN));
                stationClimate.setFebruar(stationClimate.getFebruar().divide(BigDecimal.valueOf(period), 3, RoundingMode.HALF_DOWN));
                stationClimate.setMaerz(stationClimate.getMaerz().divide(BigDecimal.valueOf(period), 3, RoundingMode.HALF_DOWN));
                stationClimate.setApril(stationClimate.getApril().divide(BigDecimal.valueOf(period), 3, RoundingMode.HALF_DOWN));
                stationClimate.setMai(stationClimate.getMai().divide(BigDecimal.valueOf(period), 3, RoundingMode.HALF_DOWN));
                stationClimate.setJuni(stationClimate.getJuni().divide(BigDecimal.valueOf(period), 3, RoundingMode.HALF_DOWN));
                stationClimate.setJuli(stationClimate.getJuli().divide(BigDecimal.valueOf(period), 3, RoundingMode.HALF_DOWN));
                stationClimate.setAugust(stationClimate.getAugust().divide(BigDecimal.valueOf(period), 3, RoundingMode.HALF_DOWN));
                stationClimate.setSeptember(stationClimate.getSeptember().divide(BigDecimal.valueOf(period), 3, RoundingMode.HALF_DOWN));
                stationClimate.setOktober(stationClimate.getOktober().divide(BigDecimal.valueOf(period), 3, RoundingMode.HALF_DOWN));
                stationClimate.setNovember(stationClimate.getNovember().divide(BigDecimal.valueOf(period), 3, RoundingMode.HALF_DOWN));
                stationClimate.setDezember(stationClimate.getDezember().divide(BigDecimal.valueOf(period), 3, RoundingMode.HALF_DOWN));
                stationClimates.add(stationClimate);
                start++;
                end = start + period;
            }
        }


        return stationClimates;
    }

    @Override
    @Transactional
    public List<StationClimate> getClimateForBundesland(String bundesland) {
        return this.stationClimateDAO.getClimateForBundesland(bundesland);
    }

    @Override
    @Transactional
    public void saveAllClimateAtStationId(List<StationClimate> stationClimates) {
        this.stationClimateDAO.saveAll(stationClimates);
    }

    @Override
    @Transactional
    public List<StationClimate> getClimateForGpsCoordinates(GpsPoint gps1, GpsPoint gps2) {

        return this.stationClimateDAO.getClimateForGpsCoordinates(gps1, gps2);
    }
}
