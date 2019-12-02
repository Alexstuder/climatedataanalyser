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
    private int stationId;

    @Column(name="YEAR")
    private String year;

    @Column(name="JANUAR")
    private BigDecimal januar;

    @Column(name="FEBRUAR")
    private BigDecimal februar;

    @Column(name="MAERZ")
    private BigDecimal maerz;

    @Column(name="APRIL")
    private BigDecimal april;

    @Column(name="MAI")
    private BigDecimal mai;

    @Column(name="JUNI")
    private BigDecimal juni;

    @Column(name="JULI")
    private BigDecimal juli;

    @Column(name="AUGUST")
    private BigDecimal august;

    @Column(name="SEPTEMBER")
    private BigDecimal september;

    @Column(name="OKTOBER")
    private BigDecimal oktober;

    @Column(name="NOVEMBER")
    private BigDecimal november;

    @Column(name="DEZEMBER")
    private  BigDecimal dezember;


    //TODO : Do we really need that Constructor ?
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

    public StationWeather(int stationId){

        this.stationId = stationId;
        this.year      = null;
        this.januar    = new BigDecimal(-99.9999);
        this.februar   = new BigDecimal(-99.9999);
        this.maerz     = new BigDecimal(-99.9999);
        this.april     = new BigDecimal(-99.9999);
        this.mai       = new BigDecimal(-99.9999);
        this.juni      = new BigDecimal(-99.9999);
        this.juli      = new BigDecimal(-99.9999);
        this.august    = new BigDecimal(-99.9999);
        this.september = new BigDecimal(-99.9999);
        this.oktober   = new BigDecimal(-99.9999);
        this.november  = new BigDecimal(-99.9999);
        this.dezember  = new BigDecimal(-99.9999);
    };

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

    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setJanuar(BigDecimal januar) {
        this.januar = januar;
    }

    public void setFebruar(BigDecimal februar) {
        this.februar = februar;
    }

    public void setMaerz(BigDecimal maerz) {
        this.maerz = maerz;
    }

    public void setApril(BigDecimal april) {
        this.april = april;
    }

    public void setMai(BigDecimal mai) {
        this.mai = mai;
    }

    public void setJuni(BigDecimal juni) {
        this.juni = juni;
    }

    public void setJuli(BigDecimal juli) {
        this.juli = juli;
    }

    public void setAugust(BigDecimal august) {
        this.august = august;
    }

    public void setSeptember(BigDecimal september) {
        this.september = september;
    }

    public void setOktober(BigDecimal oktober) {
        this.oktober = oktober;
    }

    public void setNovember(BigDecimal november) {
        this.november = november;
    }

    public void setDezember(BigDecimal dezember) {
        this.dezember = dezember;
    }
}
