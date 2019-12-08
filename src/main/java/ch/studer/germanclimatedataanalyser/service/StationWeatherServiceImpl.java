package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.dao.StationWeatherDAO;
import ch.studer.germanclimatedataanalyser.model.database.StationTemperature;
import ch.studer.germanclimatedataanalyser.model.database.StationWeatherPerYear;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class StationWeatherServiceImpl implements StationWeatherService {

    @Autowired
    StationWeatherDAO stationWeatherDAO;

    private static final Logger LOG = LoggerFactory.getLogger(StationWeatherServiceImpl.class);

    @Value("#{new Integer('${climate.temperature.big.decimal.null.value}')}")
    private static BigDecimal NULL_TEMPERATURE;

    @Value("#{new Integer('${climate.calculation.period.year}')}")
    private int period;

    private static final String JANUAR = "JANUAR";
    private static final String FEBRUAR = "FEBRUAR";
    private static final String MAERZ = "MAERZ";
    private static final String APRIL = "APRIL";
    private static final String MAI = "MAI";
    private static final String JUNI = "JUNI";
    private static final String JULI = "JULI";
    private static final String AUGUST = "AUGUST";
    private static final String SEPTEMBER = "SEPTEMBER";
    private static final String OCTOBER = "OKTOBER";
    private static final String NOVEMBER = "NOVEMBER";
    private static final String DEZEMBER = "DEZEMBER";

    @Override
    public void saveAll(List<StationWeatherPerYear> stationWeatherPerYears) {
      stationWeatherDAO.saveAll(stationWeatherPerYears);
    }

    @Override
    public List<StationWeatherPerYear> fillHoles(List<? extends StationWeatherPerYear> stationWeatherPerYears) {


        //First : Make sure the list does not contain any annual gaps
        List<StationWeatherPerYear> completed = complete(stationWeatherPerYears);

        // TODO fill Holes !
        List<StationWeatherPerYear> stationWeatherPerYearsFilledHoles = new ArrayList<StationWeatherPerYear>();


        for(int i = 0 ; i < completed.size();i++){

            if(completed.get(i).getJanuar().compareTo(NULL_TEMPERATURE) == 0) completed.get(i).setJanuar(getAverageTemperatur(completed,JANUAR,i));


            stationWeatherPerYearsFilledHoles.add(completed.get(0));
        }

        return stationWeatherPerYearsFilledHoles;
    }

    private BigDecimal getAverageTemperatur(List<StationWeatherPerYear> completed, String month, int index) {

        //BigDecimal result = new BigDecimal(0);
        BigDecimal result = new BigDecimal(0);

        int start = 0 ;
        if((index -(period/2)) > 0) start=index;

        int end = (completed.size() - 1) ;
        if((index+(period/2)) < end) end =(index+(period/2));

        for (int i= start ; i < end ; i++ ){

            if(month.contentEquals(JANUAR)) result.add(completed.get(i).getJanuar());
            if(month == FEBRUAR) result.add(completed.get(i).getFebruar());
            if(month == MAERZ) result.add(completed.get(i).getMaerz());
            if(month == APRIL) result.add(completed.get(i).getApril());
            if(month == MAI) result.add(completed.get(i).getMai());
            if(month == JUNI) result.add(completed.get(i).getJuni());
            if(month == JULI) result.add(completed.get(i).getJuli());
            if(month == AUGUST) result.add(completed.get(i).getAugust());
            if(month == SEPTEMBER) result.add(completed.get(i).getSeptember());
            if(month == OCTOBER) result.add(completed.get(i).getOktober());
            if(month == NOVEMBER) result.add(completed.get(i).getNovember());
            if(month == DEZEMBER) result.add(completed.get(i).getDezember());


        }


        // To eleminate the -99.999 Null value !
        result = result.add(NULL_TEMPERATURE.multiply(BigDecimal.valueOf(-1)));

        return result.divide(new BigDecimal(period));
    }

    private List<StationWeatherPerYear> complete(List<? extends StationWeatherPerYear> stationWeatherPerYears) {

        //
        List<StationWeatherPerYear> completedList = new ArrayList<StationWeatherPerYear>();

        int actualStationId ;
        String expectedYear = "";

        // Proof if List is filled !
        if (stationWeatherPerYears.size()>0){
            // Add first Record into completed List

            completedList.add(stationWeatherPerYears.get(0));
            actualStationId = stationWeatherPerYears.get(0).getStationID();
            expectedYear = getExpectedNextYear(stationWeatherPerYears.get(0).getYear());

            for (int i = 1 ; i < stationWeatherPerYears.size()  ; i++){

               if(stationWeatherPerYears.get(i).getYear().contentEquals(expectedYear)){
                   completedList.add(stationWeatherPerYears.get(i));
                   expectedYear = getExpectedNextYear(stationWeatherPerYears.get(i).getYear());
               } else {
                   // Insert a Nulled StationWeather to close the gap !
                   StationWeatherPerYear n = new StationWeatherPerYear(actualStationId);
                   n.setYear(expectedYear);
                   n.setCalculatedArtificially(true);
                   completedList.add(n);

                   // get the next Expected Year !
                   expectedYear = getExpectedNextYear(expectedYear);
                   //correct the index ; just to proof the same record again !
                   i--;
               }



            }
        } else {
            LOG.debug("No List to complete received !");
        }
        return  completedList;
    }

    private String getExpectedNextYear(String year) {
        String nextYear = "";

        nextYear = String.valueOf(Integer.valueOf(year)-1);

        return nextYear;
    }
}
