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
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class StationWeatherServiceImpl implements StationWeatherService {

    @Autowired
    StationWeatherDAO stationWeatherDAO;

    private static final Logger LOG = LoggerFactory.getLogger(StationWeatherServiceImpl.class);

    @Value("#{new String('${climate.temperature.big.decimal.null.value}')}")
    private String NULL_TEMPERATURE_INIT;


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

    private BigDecimal NULL_TEMPERATURE;

    @Override
    public void saveAll(List<StationWeatherPerYear> stationWeatherPerYears) {
      stationWeatherDAO.saveAll(stationWeatherPerYears);
    }

    @Override
    public List<StationWeatherPerYear> fillHoles(List<? extends StationWeatherPerYear> stationWeatherPerYears) {

        // Init the Null cause !! No way to Inject BigDecimal
        NULL_TEMPERATURE = new BigDecimal(NULL_TEMPERATURE_INIT);

        LOG.debug("StationId {}, size{}",stationWeatherPerYears.get(0).getStationID(),stationWeatherPerYears.size());
        //First : Make sure the list does not contain any annual gaps
        List<StationWeatherPerYear> completed = complete(stationWeatherPerYears);

        // TODO fill Holes !
        List<StationWeatherPerYear> stationWeatherPerYearsFilledHoles = new ArrayList<StationWeatherPerYear>();


        for(int i = 0 ; i < completed.size();i++){

            if(completed.get(i).getJanuar().compareTo(NULL_TEMPERATURE) == 0) completed.get(i).setJanuar(getAverageTemperatur(completed,JANUAR,i));
            if(completed.get(i).getFebruar().compareTo(NULL_TEMPERATURE) == 0) completed.get(i).setFebruar(getAverageTemperatur(completed,FEBRUAR,i));
            if(completed.get(i).getMaerz().compareTo(NULL_TEMPERATURE) == 0) completed.get(i).setMaerz(getAverageTemperatur(completed,MAERZ,i));
            if(completed.get(i).getApril().compareTo(NULL_TEMPERATURE) == 0) completed.get(i).setApril(getAverageTemperatur(completed,APRIL,i));
            if(completed.get(i).getMai().compareTo(NULL_TEMPERATURE) == 0) completed.get(i).setMai(getAverageTemperatur(completed,MAI,i));
            if(completed.get(i).getJuni().compareTo(NULL_TEMPERATURE) == 0) completed.get(i).setJuni(getAverageTemperatur(completed,JUNI,i));
            if(completed.get(i).getJuli().compareTo(NULL_TEMPERATURE) == 0) completed.get(i).setJuli(getAverageTemperatur(completed,JULI,i));
            if(completed.get(i).getAugust().compareTo(NULL_TEMPERATURE) == 0) completed.get(i).setAugust(getAverageTemperatur(completed,AUGUST,i));
            if(completed.get(i).getSeptember().compareTo(NULL_TEMPERATURE) == 0) completed.get(i).setSeptember(getAverageTemperatur(completed,SEPTEMBER,i));
            if(completed.get(i).getOktober().compareTo(NULL_TEMPERATURE) == 0) completed.get(i).setOktober(getAverageTemperatur(completed,OCTOBER,i));
            if(completed.get(i).getNovember().compareTo(NULL_TEMPERATURE) == 0) completed.get(i).setNovember(getAverageTemperatur(completed,NOVEMBER,i));
            if(completed.get(i).getDezember().compareTo(NULL_TEMPERATURE) == 0) completed.get(i).setDezember(getAverageTemperatur(completed,DEZEMBER,i));


            stationWeatherPerYearsFilledHoles.add(completed.get(i));
        }

        return stationWeatherPerYearsFilledHoles;
    }

    private BigDecimal getAverageTemperatur(List<StationWeatherPerYear> completed, String month, int index) {

        //Cause we got one temperature with the value NULL = -99.999 we start with +99.999 = bit tricky but does the job !
        BigDecimal result = new BigDecimal("0");


        int start = 0 ;
        if((index -(period/2)) > 0) start=(index-(period/2));

        int end = (completed.size() - 1) ;
        if((index+(period/2)) < end) end =(index+(period/2));

        int counter = 0 ;
        for (int i= start ; i < end ; i++ ){


            switch (month){

            case JANUAR: if (!completed.get(i).getJanuar().equals(NULL_TEMPERATURE)){result = result.add(completed.get(i).getJanuar());counter++;}break;
            case FEBRUAR: if (!completed.get(i).getFebruar().equals(NULL_TEMPERATURE)){result = result.add(completed.get(i).getFebruar());counter++;}break;
            case MAERZ: if (!completed.get(i).getMaerz().equals(NULL_TEMPERATURE)){result = result.add(completed.get(i).getMaerz());counter++;}break;
            case APRIL: if (!completed.get(i).getApril().equals(NULL_TEMPERATURE)){result = result.add(completed.get(i).getApril());counter++;}break;
            case MAI: if (!completed.get(i).getMai().equals(NULL_TEMPERATURE)){result = result.add(completed.get(i).getMai());counter++;}break;
            case JUNI: if (!completed.get(i).getJuni().equals(NULL_TEMPERATURE)){result = result.add(completed.get(i).getJuni());counter++;}break;
            case JULI: if (!completed.get(i).getJuli().equals(NULL_TEMPERATURE)){result = result.add(completed.get(i).getJuli());counter++;}break;
            case AUGUST: if (!completed.get(i).getAugust().equals(NULL_TEMPERATURE)){result = result.add(completed.get(i).getAugust());counter++;}break;
            case SEPTEMBER: if (!completed.get(i).getSeptember().equals(NULL_TEMPERATURE)){result = result.add(completed.get(i).getSeptember());counter++;}break;
            case OCTOBER: if (!completed.get(i).getOktober().equals(NULL_TEMPERATURE)){result = result.add(completed.get(i).getOktober());counter++;}break;
            case NOVEMBER: if (!completed.get(i).getNovember().equals(NULL_TEMPERATURE)){result = result.add(completed.get(i).getNovember());counter++;}break;
            case DEZEMBER: if (!completed.get(i).getDezember().equals(NULL_TEMPERATURE)){result = result.add(completed.get(i).getDezember());counter++;}break;

            default: break;
            }

        }

        // To eleminate the -99.999 Null value !
        //result = result.add(NULL_TEMPERATURE.multiply(BigDecimal.valueOf(-1)));
        LOG.debug("Month {}",month);
        return result.divide(BigDecimal.valueOf(counter), 3, RoundingMode.HALF_UP);
    }

    private List<StationWeatherPerYear> complete(List<? extends StationWeatherPerYear> stationWeatherPerYears) {

        LOG.debug("StationId {}, size{}",stationWeatherPerYears.get(0).getStationID(),stationWeatherPerYears.size());
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
