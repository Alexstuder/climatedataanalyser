package ch.studer.germanclimatedataanalyser.batch.writer;

import ch.studer.germanclimatedataanalyser.model.MonthTemperatureAtStationRecord;
import ch.studer.germanclimatedataanalyser.model.StationWeather;
import ch.studer.germanclimatedataanalyser.model.TemperatureRecord;
import ch.studer.germanclimatedataanalyser.service.StationWeatherService;
import ch.studer.germanclimatedataanalyser.service.TemperaturesAtStationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class WeatherWriter implements ItemWriter<MonthTemperatureAtStationRecord> {

    @Autowired
    TemperaturesAtStationService temperaturesAtStationService;

    @Autowired
    StationWeatherService stationWeatherService;
    StationWeather stationWeather ;

    private static final Logger LOG = LoggerFactory.getLogger(WeatherWriter.class);


    @Override
    public void write(List<? extends MonthTemperatureAtStationRecord> list) throws Exception {


        List<StationWeather> write = new ArrayList<StationWeather>();
        MonthTemperatureAtStationRecord l = null;

        String processingEndDate = getActualYear(list.get(0).getMessDatumEnd());
        String actualEndDate = getActualYear(list.get(0).getMessDatumEnd());

        // get first StationWeather Record
        stationWeather = new StationWeather(Integer.valueOf(list.get(0).getStationId()));

        for (MonthTemperatureAtStationRecord m : list){
            // check if last station ID = new StationID

            if (!processingEndDate.contentEquals((actualEndDate))){

                stationWeather.setYear(processingEndDate);
                write.add(stationWeather);
                stationWeather = new StationWeather(Integer.valueOf(m.getStationId()));
                processingEndDate = getActualYear(m.getMessDatumEnd());
            }

           normalise(m);
           actualEndDate = getActualYear(m.getMessDatumEnd());

        }

        stationWeatherService.saveAll(write);



    }

    private void normalise(MonthTemperatureAtStationRecord m) {

        switch (getActualMonth(m.getMessDatumEnd())){
            case "12":
                stationWeather.setDezember(m.getTemperatur());
                break;
            case "11":
                stationWeather.setNovember(m.getTemperatur());
                break;
            case "10":
                stationWeather.setOktober(m.getTemperatur());
                break;
            case "09":
                stationWeather.setSeptember(m.getTemperatur());
                break;
            case "08":
                stationWeather.setAugust(m.getTemperatur());
                break;
            case "07":
                stationWeather.setJuli(m.getTemperatur());
                break;
            case "06":
                stationWeather.setJuni(m.getTemperatur());
                break;
            case "05":
                stationWeather.setMai(m.getTemperatur());
                break;
            case "04":
                stationWeather.setApril(m.getTemperatur());
                break;
            case "03":
                stationWeather.setMaerz(m.getTemperatur());
                break;
            case "02":
                stationWeather.setFebruar(m.getTemperatur());
                break;
            case "01":
                stationWeather.setJanuar(m.getTemperatur());
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
