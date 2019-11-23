package ch.studer.germanclimatedataanalyser.batch.processor;

import ch.studer.germanclimatedataanalyser.model.Station;
import ch.studer.germanclimatedataanalyser.model.StationFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.math.BigDecimal;
import java.sql.Date;

public class StationProcessor  implements ItemProcessor<StationFile, Station> {


    private static final Logger log = LoggerFactory.getLogger(StationProcessor.class);

    @Override
    public Station process(StationFile stationFile) throws Exception {
      final int stationsId = Integer.valueOf(stationFile.getStationsId());
      final Date dateBegin  = Date.valueOf(stationFile.getDateBegin());
      final Date dateEnd  = Date.valueOf(stationFile.getDateEnd());
      final BigDecimal stationHigh  = BigDecimal.valueOf(Long.valueOf(stationFile.getStationHigh()));
      final BigDecimal geoLatitude  = BigDecimal.valueOf(Long.valueOf(stationFile.getGeoLatitude()));
      final BigDecimal geoLength  = BigDecimal.valueOf(Long.valueOf(stationFile.getGeoLength()));
      final String stationName  = stationFile.getStationName();
      final String bundesLand  = stationFile.getBundesLand();

        return new Station(stationsId,dateBegin,dateEnd,stationHigh,geoLatitude,geoLength,stationName,bundesLand);
    }
}
