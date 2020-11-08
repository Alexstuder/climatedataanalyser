package ch.studer.germanclimatedataanalyser.model.dto.climaterecords;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClimateRecordTest {

    @Test
    void getHeaderAsDifference() {

        final String EXPECTED_DIFF = "diff.";
        ClimateRecord climateRecord = new ClimateRecord();

        climateRecord.setHeaderAsDifference();
        Assertions.assertEquals(EXPECTED_DIFF,climateRecord.getHeader());

    }

    @Test
    void getHeaderYearToYear() {
        final String YEAR_FROM = "1990";
        final String YEAR_TO = "2020";
        final String EXPECTED_YEAR = YEAR_FROM + " - " + YEAR_TO;

        ClimateRecord climateRecord = new ClimateRecord();
        climateRecord.setHeaderYearToYear("1990","2020");
        Assertions.assertEquals(EXPECTED_YEAR,climateRecord.getHeader());

    }
}
