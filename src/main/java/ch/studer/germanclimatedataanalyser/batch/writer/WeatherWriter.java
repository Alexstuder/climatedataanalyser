package ch.studer.germanclimatedataanalyser.batch.writer;

import ch.studer.germanclimatedataanalyser.model.database.Month;
import ch.studer.germanclimatedataanalyser.model.database.StationWeatherPerYear;
import ch.studer.germanclimatedataanalyser.service.StationWeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class WeatherWriter implements ItemWriter<Month> {

    @Autowired
    StationWeatherService stationWeatherService;

    StationWeatherPerYear stationWeatherPerYear;

    private static final Logger LOG = LoggerFactory.getLogger(WeatherWriter.class);


    @Override
    public void write(List<? extends Month> list) throws Exception {


        List<StationWeatherPerYear> write = new ArrayList<StationWeatherPerYear>();

        String processingEndDate = getActualYear(list.get(0).getMessDatumEnde().toString());
        String actualEndDate = getActualYear(list.get(0).getMessDatumEnde().toString());

        // get first StationTemperature Record
        stationWeatherPerYear = new StationWeatherPerYear(Integer.valueOf(list.get(0).getStationsId()));

        for (Month m : list){
            // check if last station ID = new StationID

            if (!processingEndDate.contentEquals((actualEndDate))){

                stationWeatherPerYear.setYear(processingEndDate);
                write.add(stationWeatherPerYear);
                stationWeatherPerYear = new StationWeatherPerYear(Integer.valueOf(m.getStationsId()));
                processingEndDate = getActualYear(m.getMessDatumEnde().toString());
            }

           normalise(m);
           actualEndDate = getActualYear(m.getMessDatumEnde().toString());

        }

        stationWeatherService.saveAll(write);



    }

    private void normalise(Month m) {

        switch (getActualMonth(m.getMessDatumEnde().toString())){
            case "12":
                stationWeatherPerYear.setDezember(m.getMoTt());
                break;
            case "11":
                stationWeatherPerYear.setNovember(m.getMoTt());
                break;
            case "10":
                stationWeatherPerYear.setOktober(m.getMoTt());
                break;
            case "09":
                stationWeatherPerYear.setSeptember(m.getMoTt());
                break;
            case "08":
                stationWeatherPerYear.setAugust(m.getMoTt());
                break;
            case "07":
                stationWeatherPerYear.setJuli(m.getMoTt());
                break;
            case "06":
                stationWeatherPerYear.setJuni(m.getMoTt());
                break;
            case "05":
                stationWeatherPerYear.setMai(m.getMoTt());
                break;
            case "04":
                stationWeatherPerYear.setApril(m.getMoTt());
                break;
            case "03":
                stationWeatherPerYear.setMaerz(m.getMoTt());
                break;
            case "02":
                stationWeatherPerYear.setFebruar(m.getMoTt());
                break;
            case "01":
                stationWeatherPerYear.setJanuar(m.getMoTt());
                break;
            default:
                LOG.info("Something went wrong !");
                break;
        }



    }
    private String getActualMonth(String messDatumEnde) {return messDatumEnde.toString().substring(5,7);
    }
    private String getActualYear(String messDatumEnde) {return messDatumEnde.toString().substring(0,4);}
}
