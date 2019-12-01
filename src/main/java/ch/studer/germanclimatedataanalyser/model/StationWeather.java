package ch.studer.germanclimatedataanalyser.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="WEATHER")
public class StationWeather {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "WEATHER_ID")
    private int weatherId;

    //TODO STATION as an Object !
    @Column(name="STATION_ID")
    private final int stationId;

    @Column(name="YEAR")
    private final String year;

    @Column(name="JANUAR")
    private final BigDecimal januar;

    @Column(name="FEBRUAR")
    private final BigDecimal februar;

    @Column(name="MAERZ")
    private final BigDecimal maerz;

    @Column(name="APRIL")
    private final BigDecimal april;

    @Column(name="MAI")
    private final BigDecimal mai;

    @Column(name="JUNI")
    private final BigDecimal juni;

    @Column(name="JULI")
    private final BigDecimal juli;

    @Column(name="AUGUST")
    private final BigDecimal august;

    @Column(name="SEPTEMBER")
    private final BigDecimal september;

    @Column(name="OKTOBER")
    private final BigDecimal oktober;

    @Column(name="NOVEMBER")
    private final BigDecimal november;

    @Column(name="DEZEMBER")
    private final BigDecimal dezember;


    public StationWeather(int stationId, String year, BigDecimal januar, BigDecimal februar, BigDecimal maerz, BigDecimal april, BigDecimal mai, BigDecimal juni, BigDecimal juli, BigDecimal august, BigDecimal september, BigDecimal oktober, BigDecimal november, BigDecimal dezember) {
        this.stationId = stationId;
        this.year = year;
        this.januar = januar;
        this.februar = februar;
        this.maerz = maerz;
        this.april = april;
        this.mai = mai;
        this.juni = juni;
        this.juli = juli;
        this.august = august;
        this.september = september;
        this.oktober = oktober;
        this.november = november;
        this.dezember = dezember;
    }

    public int getStationId() {
        return stationId;
    }

    public String getYear() {
        return year;
    }

    public BigDecimal getJanuar() {
        return januar;
    }

    public BigDecimal getFebruar() {
        return februar;
    }

    public BigDecimal getMaerz() {
        return maerz;
    }

    public BigDecimal getApril() {
        return april;
    }

    public BigDecimal getMai() {
        return mai;
    }

    public BigDecimal getJuni() {
        return juni;
    }

    public BigDecimal getJuli() {
        return juli;
    }

    public BigDecimal getAugust() {
        return august;
    }

    public BigDecimal getSeptember() {
        return september;
    }

    public BigDecimal getOktober() {
        return oktober;
    }

    public BigDecimal getNovember() {
        return november;
    }

    public BigDecimal getDezember() {
        return dezember;
    }
}
