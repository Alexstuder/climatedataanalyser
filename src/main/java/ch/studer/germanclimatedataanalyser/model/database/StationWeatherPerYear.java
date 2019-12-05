package ch.studer.germanclimatedataanalyser.model.database;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="WEATHER")
public class StationWeatherPerYear extends  StationTemperature{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "WEATHER_ID")
    private int weatherId;

    @Column(name="YEAR")
    private String year;




    public StationWeatherPerYear() {
    }

    public StationWeatherPerYear(int stationId){

        super(stationId);
        this.year      = null;
        super.setJanuar(new BigDecimal(-99.9999));
        this.setFebruar(new BigDecimal(-99.9999));
        this.setMaerz(new BigDecimal(-99.9999));
        this.setApril(new BigDecimal(-99.9999));
        this.setMai(new BigDecimal(-99.9999));
        this.setJuni(new BigDecimal(-99.9999));
        this.setJuli(new BigDecimal(-99.9999));
        this.setAugust(new BigDecimal(-99.9999));
        this.setSeptember(new BigDecimal(-99.9999));
        this.setOktober(new BigDecimal(-99.9999));
        this.setNovember(new BigDecimal(-99.9999));
        this.setDezember(new BigDecimal(-99.9999));
    };

    public String getYear() {
        return year;
    }

    public int getStationID() {return super.getStationId();
    }

    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
