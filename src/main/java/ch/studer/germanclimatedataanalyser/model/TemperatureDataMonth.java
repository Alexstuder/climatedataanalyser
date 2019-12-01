package ch.studer.germanclimatedataanalyser.model;

import java.math.BigDecimal;

public class TemperatureDataMonth {


    private String year;

    private BigDecimal temperature ;




    // ###########################################
    // #   Constructor
    // ###########################################

    public TemperatureDataMonth(String year, BigDecimal temperature) {
        this.year = year;
        this.temperature = temperature;
    }


    // ###########################################
    // #   Getter and Setter
    // ###########################################

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }
}
