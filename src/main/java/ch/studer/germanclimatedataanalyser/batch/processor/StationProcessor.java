package ch.studer.germanclimatedataanalyser.batch.processor;

import ch.studer.germanclimatedataanalyser.model.Station;
import ch.studer.germanclimatedataanalyser.model.StationFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class StationProcessor  implements ItemProcessor<StationFile, Station> {


    private static final Logger log = LoggerFactory.getLogger(StationProcessor.class);

    @Override
    public Station process(StationFile stationFile) throws Exception {

       final int stationsId = Integer.valueOf(stationFile.getStationsId());
      final Date dateBegin  = getDate(stationFile.getDateBegin());
      final Date dateEnd  = getDate(stationFile.getDateEnd());
      final BigDecimal stationHigh  = new BigDecimal(stationFile.getStationHigh());
      final BigDecimal geoLatitude  = new BigDecimal(stationFile.getGeoLatitude());
      final BigDecimal geoLength  = new BigDecimal(stationFile.getGeoLength());
      final String stationName  = stationFile.getStationName();
      final String bundesLand  = stationFile.getBundesLand();

        return new Station(stationsId,dateBegin,dateEnd,stationHigh,geoLatitude,geoLength,stationName,bundesLand);
    }

    private Date getDate(String d){return Date.valueOf(d.substring(0,4)+"-"+d.substring(4,6)+"-"+d.substring(6,8)); }




}
