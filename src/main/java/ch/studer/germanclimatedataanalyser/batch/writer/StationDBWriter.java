package ch.studer.germanclimatedataanalyser.batch.writer;

import ch.studer.germanclimatedataanalyser.model.Station;
import ch.studer.germanclimatedataanalyser.service.StationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.util.List;


public class StationDBWriter implements ItemWriter<Station> {

    private static final Logger LOG = LoggerFactory.getLogger(StationDBWriter.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private StationService stationService;

    public StationDBWriter(){
    }

    @Override
    public void write(List<? extends Station> stations) throws Exception {

        stationService.saveAllStation(stations);




    }
}
