package ch.studer.germanclimatedataanalyser.model.database;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;

@MappedSuperclass
public abstract class TemperatureForMonths {

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

    // TODO : the NULL Value should be given trough the application.properties !
    //TODO Inject NULL_TEMPERATURE How ?? it's an abstaract class !?
    // TODO How to inject a BigDecimal !
    private static BigDecimal NULL_TEMPERATURE = new BigDecimal("-999.0000");

    public TemperatureForMonths() {

        this.setJanuar(NULL_TEMPERATURE);
        this.setFebruar(NULL_TEMPERATURE);
        this.setMaerz(NULL_TEMPERATURE);
        this.setApril(NULL_TEMPERATURE);
        this.setMai(NULL_TEMPERATURE);
        this.setJuni(NULL_TEMPERATURE);
        this.setJuli(NULL_TEMPERATURE);
        this.setAugust(NULL_TEMPERATURE);
        this.setSeptember(NULL_TEMPERATURE);
        this.setOktober(NULL_TEMPERATURE);
        this.setNovember(NULL_TEMPERATURE);
        this.setDezember(NULL_TEMPERATURE);
    }

// Getter and Setter

    public TemperatureForMonths getTemperatureForMonths(){
        return this;
    }

    public BigDecimal getJanuar() {
        return januar;
    }

    public void setJanuar(BigDecimal januar) {
        this.januar = januar;
    }

    public BigDecimal getFebruar() {
        return februar;
    }

    public void setFebruar(BigDecimal februar) {
        this.februar = februar;
    }

    public BigDecimal getMaerz() {
        return maerz;
    }

    public void setMaerz(BigDecimal maerz) {
        this.maerz = maerz;
    }

    public BigDecimal getApril() {
        return april;
    }

    public void setApril(BigDecimal april) {
        this.april = april;
    }

    public BigDecimal getMai() {
        return mai;
    }

    public void setMai(BigDecimal mai) {
        this.mai = mai;
    }

    public BigDecimal getJuni() {
        return juni;
    }

    public void setJuni(BigDecimal juni) {
        this.juni = juni;
    }

    public BigDecimal getJuli() {
        return juli;
    }

    public void setJuli(BigDecimal juli) {
        this.juli = juli;
    }

    public BigDecimal getAugust() {
        return august;
    }

    public void setAugust(BigDecimal august) {
        this.august = august;
    }

    public BigDecimal getSeptember() {
        return september;
    }

    public void setSeptember(BigDecimal september) {
        this.september = september;
    }

    public BigDecimal getOktober() {
        return oktober;
    }

    public void setOktober(BigDecimal oktober) {
        this.oktober = oktober;
    }

    public BigDecimal getNovember() {
        return november;
    }

    public void setNovember(BigDecimal november) {
        this.november = november;
    }

    public BigDecimal getDezember() {
        return dezember;
    }

    public void setDezember(BigDecimal dezember) {
        this.dezember = dezember;
    }
}
