package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.dao.StationClimateDAO;
import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.database.StationWeatherPerYear;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


public class ClimateServiceImpl implements ClimateService {


    @Value("#{new Integer('${climate.calculation.period.year}')}")
    int period;

    // Inludes all Cimate Records for a Station
    //ClimateOLDAtStation climateAtStation ;


    @Autowired
    StationWeatherService stationWeatherService;

    @Autowired
    StationClimateDAO stationClimateDAO;

    BigDecimal januarClimate ;
    BigDecimal februarClimate ;
    BigDecimal maerzClimate ;
    BigDecimal aprilClimate ;
    BigDecimal maiClimate ;
    BigDecimal juniClimate ;
    BigDecimal juliClimate ;
    BigDecimal augustClimate ;
    BigDecimal septemberClimate ;
    BigDecimal octoberClimate ;
    BigDecimal novemberClimate ;
    BigDecimal dezemberClimate ;


    private static final Logger LOG = LoggerFactory.getLogger(ClimateServiceImpl.class);


    @Override
    public List<StationClimate> getClimateForStation(List<StationWeatherPerYear> stationWeatherPerYears) {
        List<StationClimate> stationClimates = new ArrayList<StationClimate>();

        int start = 0;
        int end = start + period;

        for(StationWeatherPerYear stationWeatherPerYear:stationWeatherPerYears){

            if(end < stationWeatherPerYears.size()){
                StationClimate stationClimate = new StationClimate(stationWeatherPerYears.get(start).getStationID());
                stationClimate.setEndPeriod(stationWeatherPerYears.get(start).getYear());
                stationClimate.setStartPeriod(stationWeatherPerYears.get(end-1).getYear());

                stationClimate.setJanuar(new BigDecimal(0));

                januarClimate = new BigDecimal(0);
                februarClimate = new BigDecimal(0);
                maerzClimate = new BigDecimal(0);
                aprilClimate = new BigDecimal(0);
                maiClimate = new BigDecimal(0);
                juniClimate = new BigDecimal(0);
                juliClimate = new BigDecimal(0);
                augustClimate = new BigDecimal(0);
                septemberClimate = new BigDecimal(0);
                octoberClimate = new BigDecimal(0);
                novemberClimate = new BigDecimal(0);
                dezemberClimate = new BigDecimal(0);

                for (int i =start ; i < end ; i ++ ){

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
//                    januarClimate = januarClimate.add(stationWeatherPerYears.get(i).getJanuar());
//                    februarClimate = februarClimate.add(stationWeatherPerYears.get(i).getFebruar());
//                    maerzClimate = maerzClimate.add(stationWeatherPerYears.get(i).getMaerz());
//                    aprilClimate = aprilClimate.add(stationWeatherPerYears.get(i).getApril());
//                    maiClimate = maiClimate.add(stationWeatherPerYears.get(i).getMai());
//                    juniClimate = juniClimate.add(stationWeatherPerYears.get(i).getJuni());
//                    juliClimate = juliClimate.add(stationWeatherPerYears.get(i).getJuli());
//                    augustClimate = augustClimate.add(stationWeatherPerYears.get(i).getAugust());
//                    septemberClimate = septemberClimate.add(stationWeatherPerYears.get(i).getSeptember());
//                    octoberClimate = octoberClimate.add(stationWeatherPerYears.get(i).getOktober());
//                    novemberClimate = novemberClimate.add(stationWeatherPerYears.get(i).getNovember());
//                    dezemberClimate = dezemberClimate.add(stationWeatherPerYears.get(i).getDezember());
                }

                // Divide trough Years (Period)
                //stationClimate.setJanuar(januarClimate.divide(BigDecimal.valueOf(period),3 , RoundingMode.HALF_UP));
                stationClimate.setJanuar(stationClimate.getJanuar().divide(BigDecimal.valueOf(period),3,RoundingMode.HALF_DOWN));
                stationClimate.setFebruar(stationClimate.getFebruar().divide(BigDecimal.valueOf(period),3,RoundingMode.HALF_DOWN));
                stationClimate.setMaerz(stationClimate.getMaerz().divide(BigDecimal.valueOf(period),3,RoundingMode.HALF_DOWN));
                stationClimate.setApril(stationClimate.getApril().divide(BigDecimal.valueOf(period),3,RoundingMode.HALF_DOWN));
                stationClimate.setMai(stationClimate.getMai().divide(BigDecimal.valueOf(period),3,RoundingMode.HALF_DOWN));
                stationClimate.setJuni(stationClimate.getJuni().divide(BigDecimal.valueOf(period),3,RoundingMode.HALF_DOWN));
                stationClimate.setJuli(stationClimate.getJuli().divide(BigDecimal.valueOf(period),3,RoundingMode.HALF_DOWN));
                stationClimate.setAugust(stationClimate.getAugust().divide(BigDecimal.valueOf(period),3,RoundingMode.HALF_DOWN));
                stationClimate.setSeptember(stationClimate.getSeptember().divide(BigDecimal.valueOf(period),3,RoundingMode.HALF_DOWN));
                stationClimate.setOktober(stationClimate.getOktober().divide(BigDecimal.valueOf(period),3,RoundingMode.HALF_DOWN));
                stationClimate.setNovember(stationClimate.getNovember().divide(BigDecimal.valueOf(period),3,RoundingMode.HALF_DOWN));
                stationClimate.setDezember(stationClimate.getDezember().divide(BigDecimal.valueOf(period),3,RoundingMode.HALF_DOWN));
//                stationClimate.setFebruar(februarClimate.divide(BigDecimal.valueOf(period),3 , RoundingMode.HALF_UP));
//                stationClimate.setMaerz(maerzClimate.divide(BigDecimal.valueOf(period),3 , RoundingMode.HALF_UP));
//                stationClimate.setApril(aprilClimate.divide(BigDecimal.valueOf(period),3 , RoundingMode.HALF_UP));
//                stationClimate.setMai(maiClimate.divide(BigDecimal.valueOf(period),3 , RoundingMode.HALF_UP));
//                stationClimate.setJuni(juniClimate.divide(BigDecimal.valueOf(period),3 , RoundingMode.HALF_UP));
//                stationClimate.setJuli(juliClimate.divide(BigDecimal.valueOf(period),3 , RoundingMode.HALF_UP));
//                stationClimate.setAugust(augustClimate.divide(BigDecimal.valueOf(period),3 , RoundingMode.HALF_UP));
//                stationClimate.setSeptember(septemberClimate.divide(BigDecimal.valueOf(period),3 , RoundingMode.HALF_UP));
//                stationClimate.setOktober(octoberClimate.divide(BigDecimal.valueOf(period),3 , RoundingMode.HALF_UP));
//                stationClimate.setNovember(novemberClimate.divide(BigDecimal.valueOf(period),3 , RoundingMode.HALF_UP));
//                stationClimate.setDezember(dezemberClimate.divide(BigDecimal.valueOf(period),3 , RoundingMode.HALF_UP));
                stationClimates.add(stationClimate);
                start++;
                end = start + period;
            }
        }

//        // get the first StaionId
//        if(list.size()>0) {
//            int actualStationId = list.get(0).getStationId();
//
//            // Transform all WeatherRecords from 1 Station to a new separated List
//            // fill all the holes
//            // calculate the CLimaRecords
//            // Write into DB
//            List<StationWeatherPerYear> stationWeatherPerYears = new ArrayList<StationWeatherPerYear>();
//            for (StationWeatherPerYear stationWeatherPerYear : list) {
//
//                //add actual Weather Record to the stationWeathers List
//
//                if (actualStationId == stationWeatherPerYear.getStationId()) {
//                    stationWeatherPerYears.add(stationWeatherPerYear);
//
//                } else {
//
//                    // fill the holes
//                    List<StationWeatherPerYear> stationWeatherPerYearsFilledHoles = stationWeatherService.fillHoles(stationWeatherPerYears);
//
//                    // calculate the climate Record
//                    List<StationClimate> stationClimates = climateService.getClimateForStation(stationWeatherPerYearsFilledHoles);
//
//                    // write ClimateRecord to DB
//                    if (stationClimates.size() > 0) {
//                        climateService.saveAllClimateAtStationId(stationClimates);
//                    }
//                    // get a new WeatherList
//
//                    // fill the actual Record to weatherList
//                }
//                LOG.debug(" " + stationWeatherPerYear.getStationId());
//
//            }
//        } else {
//            LOG.debug("No WeatherRecords available to calculate Climate Records. List.size = {} ",list.size());
//        }





        return stationClimates;
    }

    @Override
    public void saveAllClimateAtStationId(List<StationClimate> stationClimates) {
       this.stationClimateDAO.saveAll(stationClimates);
    }
}
