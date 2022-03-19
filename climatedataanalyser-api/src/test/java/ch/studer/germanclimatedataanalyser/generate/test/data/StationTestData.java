package ch.studer.germanclimatedataanalyser.generate.test.data;

import ch.studer.germanclimatedataanalyser.model.database.Station;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class StationTestData {


    public static List<Station> getStations(int numberOfStations, String bundesland) {
        List<Station> stations = new ArrayList<Station>();
        String bundeslandName = "";
        if (bundesland == null) {
            bundeslandName = "noNeed";
        } else {
            bundeslandName = bundesland;
        }
        for (int stationId = 0; stationId < numberOfStations; stationId++) {

            Date dateBegin = Date.valueOf("1990-01-25");
            Date dateEnd = Date.valueOf("2020-01-25");
            BigDecimal stationHigh = BigDecimal.valueOf(200);
            BigDecimal geoLatitude = BigDecimal.valueOf(50.0001 - stationId);
            BigDecimal geoLength = BigDecimal.valueOf(8.0001 + stationId);
            String stationName = "noNeed";

            Station station = new Station(stationId, dateBegin, dateEnd, stationHigh, geoLatitude, geoLength, stationName, bundeslandName);
            stations.add(station);

        }

        return stations;
    }

}
