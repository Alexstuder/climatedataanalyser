package ch.studer.germanclimatedataanalyser.model;

public class TemperatureDataMonth {


    private String year;

    private double temperature ;




    // ###########################################
    // #   Constructor
    // ###########################################

    public TemperatureDataMonth(String year, double temperature) {
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

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
