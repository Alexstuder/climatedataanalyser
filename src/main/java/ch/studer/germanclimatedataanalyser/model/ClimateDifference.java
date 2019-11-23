package ch.studer.germanclimatedataanalyser.model;

public class ClimateDifference {

    private ClimateRecord climateFirstPeriod;

    private ClimateRecord climateSecondPeriod;

    private TemperatureRecord difference;

    // Constructor
    public ClimateDifference(ClimateRecord first ,ClimateRecord second) {
        this.setClimateFirstPeriod(first);
        this.setClimateSecondPeriod(second);

        this.difference = new TemperatureRecord("");

        //Build the Difference
        setDifference();
    }

    // Getter and Setter
    public ClimateRecord getClimateFirstPeriod() {
        return climateFirstPeriod;
    }

    public void setClimateFirstPeriod(ClimateRecord climateFirstPeriod) {
        this.climateFirstPeriod = climateFirstPeriod;
    }

    public ClimateRecord getClimateSecondPeriod() {
        return climateSecondPeriod;
    }

    public void setClimateSecondPeriod(ClimateRecord climateSecondPeriod) {
        this.climateSecondPeriod = climateSecondPeriod;
    }

    public TemperatureRecord getDifference() {
        return difference;
    }

    private void setDifference() {
        this.getDifference().jan = this.getClimateFirstPeriod().getTempJan() - this.getClimateSecondPeriod().getTempJan();
        this.getDifference().feb = this.getClimateFirstPeriod().getTempFeb() - this.getClimateSecondPeriod().getTempFeb();
        this.getDifference().mar = this.getClimateFirstPeriod().getTempMar() - this.getClimateSecondPeriod().getTempMar();
        this.getDifference().apr = this.getClimateFirstPeriod().getTempApr() - this.getClimateSecondPeriod().getTempApr();
        this.getDifference().mai = this.getClimateFirstPeriod().getTempMai() - this.getClimateSecondPeriod().getTempMai();
        this.getDifference().jun = this.getClimateFirstPeriod().getTempJun() - this.getClimateSecondPeriod().getTempJun();
        this.getDifference().jul = this.getClimateFirstPeriod().getTempJul() - this.getClimateSecondPeriod().getTempJul();
        this.getDifference().aug = this.getClimateFirstPeriod().getTempAug() - this.getClimateSecondPeriod().getTempAug();
        this.getDifference().sep = this.getClimateFirstPeriod().getTempSep() - this.getClimateSecondPeriod().getTempSep();
        this.getDifference().oct = this.getClimateFirstPeriod().getTempOkt() - this.getClimateSecondPeriod().getTempOkt();
        this.getDifference().nov = this.getClimateFirstPeriod().getTempNov() - this.getClimateSecondPeriod().getTempNov();
        this.getDifference().dec = this.getClimateFirstPeriod().getTempDez() - this.getClimateSecondPeriod().getTempDez();

    }
}
