package ch.studer.germanclimatedataanalyser.model.database;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@MappedSuperclass
public  class TemperatureForMonths {

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

    private void initWithZero(){
        this.setJanuar(BigDecimal.ZERO);
        this.setFebruar(BigDecimal.ZERO);
        this.setMaerz(BigDecimal.ZERO);
        this.setApril(BigDecimal.ZERO);
        this.setMai(BigDecimal.ZERO);
        this.setJuni(BigDecimal.ZERO);
        this.setJuli(BigDecimal.ZERO);
        this.setAugust(BigDecimal.ZERO);
        this.setSeptember(BigDecimal.ZERO);
        this.setOktober(BigDecimal.ZERO);
        this.setNovember(BigDecimal.ZERO);
        this.setDezember(BigDecimal.ZERO);
    }


    public TemperatureForMonths getAverage(List<TemperatureForMonths> temperatureForMonthsList){
        TemperatureForMonths averageTemperature = new TemperatureForMonths();
        averageTemperature.initWithZero();

        for (TemperatureForMonths temperatureForMonths : temperatureForMonthsList){

            averageTemperature.setJanuar(averageTemperature.getJanuar().add(temperatureForMonths.getJanuar()));
            averageTemperature.setFebruar(averageTemperature.getFebruar().add(temperatureForMonths.getFebruar()));
            averageTemperature.setMaerz(averageTemperature.getMaerz().add(temperatureForMonths.getMaerz()));
            averageTemperature.setApril(averageTemperature.getApril().add(temperatureForMonths.getApril()));
            averageTemperature.setMai(averageTemperature.getMai().add(temperatureForMonths.getMai()));
            averageTemperature.setJuni(averageTemperature.getJuni().add(temperatureForMonths.getJuni()));
            averageTemperature.setJuli(averageTemperature.getJuli().add(temperatureForMonths.getJuli()));
            averageTemperature.setAugust(averageTemperature.getAugust().add(temperatureForMonths.getAugust()));
            averageTemperature.setSeptember(averageTemperature.getSeptember().add(temperatureForMonths.getSeptember()));
            averageTemperature.setOktober(averageTemperature.getOktober().add(temperatureForMonths.getOktober()));
            averageTemperature.setNovember(averageTemperature.getNovember().add(temperatureForMonths.getNovember()));
            averageTemperature.setDezember(averageTemperature.getDezember().add(temperatureForMonths.getDezember()));


        }

        // get Average by division temperature / ListSize
        averageTemperature.setJanuar(averageTemperature.getJanuar().divide(BigDecimal.valueOf(temperatureForMonthsList.size()), 3, RoundingMode.HALF_DOWN));
        averageTemperature.setFebruar(averageTemperature.getFebruar().divide(BigDecimal.valueOf(temperatureForMonthsList.size()), 3, RoundingMode.HALF_DOWN));
        averageTemperature.setMaerz(averageTemperature.getMaerz().divide(BigDecimal.valueOf(temperatureForMonthsList.size()), 3, RoundingMode.HALF_DOWN));
        averageTemperature.setApril(averageTemperature.getApril().divide(BigDecimal.valueOf(temperatureForMonthsList.size()), 3, RoundingMode.HALF_DOWN));
        averageTemperature.setMai(averageTemperature.getMai().divide(BigDecimal.valueOf(temperatureForMonthsList.size()), 3, RoundingMode.HALF_DOWN));
        averageTemperature.setJuni(averageTemperature.getJuni().divide(BigDecimal.valueOf(temperatureForMonthsList.size()), 3, RoundingMode.HALF_DOWN));
        averageTemperature.setJuli(averageTemperature.getJuli().divide(BigDecimal.valueOf(temperatureForMonthsList.size()), 3, RoundingMode.HALF_DOWN));
        averageTemperature.setAugust(averageTemperature.getAugust().divide(BigDecimal.valueOf(temperatureForMonthsList.size()), 3, RoundingMode.HALF_DOWN));
        averageTemperature.setSeptember(averageTemperature.getSeptember().divide(BigDecimal.valueOf(temperatureForMonthsList.size()), 3, RoundingMode.HALF_DOWN));
        averageTemperature.setOktober(averageTemperature.getOktober().divide(BigDecimal.valueOf(temperatureForMonthsList.size()), 3, RoundingMode.HALF_DOWN));
        averageTemperature.setNovember(averageTemperature.getNovember().divide(BigDecimal.valueOf(temperatureForMonthsList.size()), 3, RoundingMode.HALF_DOWN));
        averageTemperature.setDezember(averageTemperature.getDezember().divide(BigDecimal.valueOf(temperatureForMonthsList.size()), 3, RoundingMode.HALF_DOWN));


        return averageTemperature;
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
