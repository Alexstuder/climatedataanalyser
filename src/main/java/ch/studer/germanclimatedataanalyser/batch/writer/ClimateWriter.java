package ch.studer.germanclimatedataanalyser.batch.writer;

import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.database.StationTemperature;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class ClimateWriter implements ItemWriter<StationTemperature> {
    @Override
    public void write(List<? extends StationTemperature> list) throws Exception {


        // iterate till nex StationID

        // Fill all holes = Temperature = -99.9999

        // caalculate climate periods per Station_ID


        // write all Climate Records into the climate Table

    }
}
