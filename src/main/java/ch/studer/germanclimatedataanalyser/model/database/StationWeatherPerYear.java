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

    @Column(name="CALCULATED_ARTIFICIALLY")
    private boolean calculatedArtificially;




//    public StationWeatherPerYear() {
//    }

    public StationWeatherPerYear(int stationId){

        super(stationId);
        this.year      = null;
        this.calculatedArtificially = false ;

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

    public boolean isCalculatedArtificially() {
        return calculatedArtificially;
    }

    public void setCalculatedArtificially(boolean calculatedArtificially) {
        this.calculatedArtificially = calculatedArtificially;
    }
}