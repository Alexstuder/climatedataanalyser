package ch.studer.germanclimatedataanalyser.model.dto;

import ch.studer.germanclimatedataanalyser.model.database.TemperatureForMonths;

import java.math.BigDecimal;

/*
 */
public class TemperatureForMonthDto {

    private BigDecimal januar;
    private BigDecimal februar;
    private BigDecimal maerz;
    private BigDecimal april;
    private BigDecimal mai;
    private BigDecimal juni;
    private BigDecimal juli;
    private BigDecimal august;
    private BigDecimal september;
    private BigDecimal oktober;
    private BigDecimal november;
    private BigDecimal dezember;


    public TemperatureForMonthDto() {
        setJanuar(new BigDecimal("0"));
        setFebruar(new BigDecimal("0"));
        setMaerz(new BigDecimal("0"));
        setApril(new BigDecimal("0"));
        setMai(new BigDecimal("0"));
        setJuni(new BigDecimal("0"));
        setJuli(new BigDecimal("0"));
        setAugust(new BigDecimal("0"));
        setSeptember(new BigDecimal("0"));
        setOktober(new BigDecimal("0"));
        setNovember(new BigDecimal("0"));
        setDezember(new BigDecimal("0"));
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

    public TemperatureForMonthDto mapFrom(TemperatureForMonths temperatureForMonths) {
        TemperatureForMonthDto temperatureForMonthDto = new TemperatureForMonthDto();

        temperatureForMonthDto.setJanuar(temperatureForMonths.getJanuar());
        temperatureForMonthDto.setFebruar(temperatureForMonths.getFebruar());
        temperatureForMonthDto.setMaerz(temperatureForMonths.getMaerz());
        temperatureForMonthDto.setApril(temperatureForMonths.getApril());
        temperatureForMonthDto.setMai(temperatureForMonths.getMai());
        temperatureForMonthDto.setJuni(temperatureForMonths.getJuni());
        temperatureForMonthDto.setJuli(temperatureForMonths.getJuli());
        temperatureForMonthDto.setAugust(temperatureForMonths.getAugust());
        temperatureForMonthDto.setSeptember(temperatureForMonths.getSeptember());
        temperatureForMonthDto.setOktober(temperatureForMonths.getOktober());
        temperatureForMonthDto.setNovember(temperatureForMonths.getNovember());
        temperatureForMonthDto.setDezember(temperatureForMonths.getDezember());


        return temperatureForMonthDto;
    }
    public void mapAndSetFrom(TemperatureForMonths temperatureForMonths) {
        TemperatureForMonthDto temperatureForMonthDto = new TemperatureForMonthDto();

        this.setJanuar(temperatureForMonths.getJanuar());
        this.setFebruar(temperatureForMonths.getFebruar());
        this.setMaerz(temperatureForMonths.getMaerz());
        this.setApril(temperatureForMonths.getApril());
        this.setMai(temperatureForMonths.getMai());
        this.setJuni(temperatureForMonths.getJuni());
        this.setJuli(temperatureForMonths.getJuli());
        this.setAugust(temperatureForMonths.getAugust());
        this.setSeptember(temperatureForMonths.getSeptember());
        this.setOktober(temperatureForMonths.getOktober());
        this.setNovember(temperatureForMonths.getNovember());
        this.setDezember(temperatureForMonths.getDezember());

    }

    public boolean isNotZero() {
        boolean status = false;

        return !this.getJanuar().add(
                this.getFebruar().add(
                        this.getMaerz().add(
                                this.getApril().add(
                                        this.getMai().add(
                                                this.getJuni().add(
                                                        this.getJuli().add(
                                                                this.getAugust().add(
                                                                        this.getSeptember().add(
                                                                                this.getOktober().add(
                                                                                        this.getNovember().add(
                                                                                                this.getDezember()))))))))))).equals(new BigDecimal("0"));


    }
}
