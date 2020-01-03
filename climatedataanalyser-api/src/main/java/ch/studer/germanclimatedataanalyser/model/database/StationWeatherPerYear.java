package ch.studer.germanclimatedataanalyser.model.database;

import javax.persistence.*;

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
    public StationWeatherPerYear(int stationId,String year){

        super(stationId);
        this.year      = year;
        this.calculatedArtificially = false ;

    };

    public StationWeatherPerYear(StationWeatherPerYear stationWeatherPerYear) {
        super(stationWeatherPerYear.getStationID());
        super.setJanuar(stationWeatherPerYear.getJanuar());
        super.setFebruar(stationWeatherPerYear.getFebruar());
        super.setMaerz(stationWeatherPerYear.getMaerz());
        super.setApril(stationWeatherPerYear.getApril());
        super.setMai(stationWeatherPerYear.getMai());
        super.setJuni(stationWeatherPerYear.getJuni());
        super.setJuli(stationWeatherPerYear.getJuli());
        super.setAugust(stationWeatherPerYear.getAugust());
        super.setSeptember(stationWeatherPerYear.getSeptember());
        super.setOktober(stationWeatherPerYear.getOktober());
        super.setNovember(stationWeatherPerYear.getNovember());
        super.setDezember(stationWeatherPerYear.getDezember());
        this.year = stationWeatherPerYear.getYear();
        this.setCalculatedArtificially(false);
    }

    public int getWeatherId() {
        return weatherId;
    }

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
