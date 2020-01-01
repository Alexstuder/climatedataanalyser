package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.dao.StationWeatherDAO;
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

    /*
    range: defines the range to calculate the NULL_TEMPERATURE holes.
    If its defined period/2 you get a range of 15 .

    That means to calculate the NULL_TEMPERATURE holes it will take 15 before and after year from the starting year:
    for example: the year 1989 is NULL_TEMPERATURE :
    Take the month temperature from (2004 - 1990 and 1988 - 1974) / 30 .
    n.b: 1989 was NULL !

     */
    @Value("#{new Integer('${climate.calculation.range.year}')}")
    private int range;

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

        // calculate for every null (-999.000) an average temperature
        List<StationWeatherPerYear> stationWeatherPerYearsFilledHoles = calculateHoles(completed);

        return stationWeatherPerYearsFilledHoles;
    }

    private List<StationWeatherPerYear> calculateHoles(List<StationWeatherPerYear> completed) {

        List<StationWeatherPerYear> stationWeatherPerYearsFilledHoles = new ArrayList<StationWeatherPerYear>();

        for(int i = 0 ; i < completed.size();i++){

            //Get a copy of the original record
            StationWeatherPerYear t = new StationWeatherPerYear(completed.get(i));


            if(t.getJanuar().compareTo(NULL_TEMPERATURE) == 0) t.setJanuar(getAverageTemperatur(completed,JANUAR,i));
            if(t.getFebruar().compareTo(NULL_TEMPERATURE) == 0) t.setFebruar(getAverageTemperatur(completed,FEBRUAR,i));
            if(t.getMaerz().compareTo(NULL_TEMPERATURE) == 0) t.setMaerz(getAverageTemperatur(completed,MAERZ,i));
            if(t.getApril().compareTo(NULL_TEMPERATURE) == 0) t.setApril(getAverageTemperatur(completed,APRIL,i));
            if(t.getMai().compareTo(NULL_TEMPERATURE) == 0) t.setMai(getAverageTemperatur(completed,MAI,i));
            if(t.getJuni().compareTo(NULL_TEMPERATURE) == 0) t.setJuni(getAverageTemperatur(completed,JUNI,i));
            if(t.getJuli().compareTo(NULL_TEMPERATURE) == 0) t.setJuli(getAverageTemperatur(completed,JULI,i));
            if(t.getAugust().compareTo(NULL_TEMPERATURE) == 0) t.setAugust(getAverageTemperatur(completed,AUGUST,i));
            if(t.getSeptember().compareTo(NULL_TEMPERATURE) == 0) t.setSeptember(getAverageTemperatur(completed,SEPTEMBER,i));
            if(t.getOktober().compareTo(NULL_TEMPERATURE) == 0) t.setOktober(getAverageTemperatur(completed,OCTOBER,i));
            if(t.getNovember().compareTo(NULL_TEMPERATURE) == 0) t.setNovember(getAverageTemperatur(completed,NOVEMBER,i));
            if(t.getDezember().compareTo(NULL_TEMPERATURE) == 0) t.setDezember(getAverageTemperatur(completed,DEZEMBER,i));


            // Only Records without NULL Value would be added to list
            if (hasNoNullTemperature(t)){
                stationWeatherPerYearsFilledHoles.add(t);
            }
        }

        return stationWeatherPerYearsFilledHoles;
    }

    private boolean hasNoNullTemperature(StationWeatherPerYear stationWeatherPerYear) {
     boolean status = true ;

         if ((stationWeatherPerYear.getJanuar().equals(NULL_TEMPERATURE)) ||
             (stationWeatherPerYear.getFebruar().equals(NULL_TEMPERATURE)) ||
             (stationWeatherPerYear.getMaerz().equals(NULL_TEMPERATURE))||
             (stationWeatherPerYear.getApril().equals(NULL_TEMPERATURE))||
             (stationWeatherPerYear.getMai().equals(NULL_TEMPERATURE))||
             (stationWeatherPerYear.getJuni().equals(NULL_TEMPERATURE))||
             (stationWeatherPerYear.getJuli().equals(NULL_TEMPERATURE))||
             (stationWeatherPerYear.getAugust().equals(NULL_TEMPERATURE))||
             (stationWeatherPerYear.getSeptember().equals(NULL_TEMPERATURE))||
             (stationWeatherPerYear.getOktober().equals(NULL_TEMPERATURE))||
             (stationWeatherPerYear.getNovember().equals(NULL_TEMPERATURE))||
             (stationWeatherPerYear.getDezember().equals(NULL_TEMPERATURE))){
             status = false;

        }


     return status;
    }

    private BigDecimal getAverageTemperatur(List<StationWeatherPerYear> completed, String month, int index) {

        //Cause we got one temperature with the value NULL = -99.999 we start with +99.999 = bit tricky but does the job !
        BigDecimal result = new BigDecimal("0");


        int start = 0 ;
        if((index -(range)) > 0) start=(index-(range));

        int end = (completed.size() - 1) ;
        if((index+(range)) < end) end =(index+(range));

        int counter = 0 ;
        for (int i= start ; i <= end ; i++ ){


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

        LOG.debug("Station_Id {} , Month {} , counter {}",completed.get(0).getStationID(),month,counter);

        // if there are more then 30 years of NULL_TEMPERATURE , the climate Record can not be calculated !
        result = (counter == 0)? NULL_TEMPERATURE :  result.divide(BigDecimal.valueOf(counter), 3, RoundingMode.HALF_UP);
        return result;
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
                   LOG.debug("Insert a nulled Station Weather Record to close the gap ; Station_Id :{} , Year : {} " , actualStationId, expectedYear );
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
