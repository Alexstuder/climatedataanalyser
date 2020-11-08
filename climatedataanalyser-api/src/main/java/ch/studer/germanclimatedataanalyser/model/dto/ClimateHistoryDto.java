package ch.studer.germanclimatedataanalyser.model.dto;

import ch.studer.germanclimatedataanalyser.model.database.TemperatureForMonths;

public class ClimateHistoryDto {

    private String endPeriod;
    private String startPeriod;
    private TemperatureForMonthDto climates;

    public ClimateHistoryDto() {

        endPeriod = "";
        startPeriod = "";

        // inits all MonthTemp with 0 ;
        climates = new TemperatureForMonthDto();

    }

    public String getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(String endPeriod) {
        this.endPeriod = endPeriod;
    }

    public String getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(String startPeriod) {
        this.startPeriod = startPeriod;
    }

    public TemperatureForMonthDto getClimates() {
        return climates;
    }

    public void setClimates(TemperatureForMonthDto climates) {
        this.climates = climates;
    }


    public void setClimatesMapFrom(TemperatureForMonths temperatureForMonth) {
        this.getClimates().setJanuar(temperatureForMonth.getJanuar());
        this.getClimates().setFebruar(temperatureForMonth.getFebruar());
        this.getClimates().setMaerz(temperatureForMonth.getMaerz());
        this.getClimates().setApril(temperatureForMonth.getApril());
        this.getClimates().setMai(temperatureForMonth.getMai());
        this.getClimates().setJuni(temperatureForMonth.getJuni());
        this.getClimates().setJuli(temperatureForMonth.getJuli());
        this.getClimates().setAugust(temperatureForMonth.getAugust());
        this.getClimates().setSeptember(temperatureForMonth.getSeptember());
        this.getClimates().setOktober(temperatureForMonth.getOktober());
        this.getClimates().setNovember(temperatureForMonth.getNovember());
        this.getClimates().setDezember(temperatureForMonth.getDezember());

    }

}
