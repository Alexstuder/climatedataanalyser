package ch.studer.germanclimatedataanalyser.model.dto.climaterecords;

import ch.studer.germanclimatedataanalyser.model.dto.TemperatureForMonthDto;

public class ClimateRecord extends TemperatureForMonthDto {

    private final static String DIFF = "diff.";

    //contains year to year or Text "diff"
    private String header;

    public ClimateRecord() {
        super();
        this.header = "";

    }

    public void setHeaderAsDifference() {
        this.header = DIFF;
    }

    public void setHeaderYearToYear(String from, String to) {

        this.header = from + " - " + to;
    }

    public String getHeader() {
        return header;
    }
}
