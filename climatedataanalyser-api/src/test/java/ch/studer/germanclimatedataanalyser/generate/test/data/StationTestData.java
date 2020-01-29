package ch.studer.germanclimatedataanalyser.generate.test.data;

import ch.studer.germanclimatedataanalyser.model.database.Station;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class StationTestData {



    public static List<Station> getStations(int numberOfStations){
        List<Station> stations = new ArrayList<Station>();

        for (int i=0; i<numberOfStations;i++){

            int stationId = i;
            Date dateBegin = Date.valueOf("1990-01-25");
            Date dateEnd = Date.valueOf("2020-01-25");
            BigDecimal stationHigh = BigDecimal.valueOf(200);
            BigDecimal geoLatitude = BigDecimal.valueOf(50.0001-i);
            BigDecimal geoLength = BigDecimal.valueOf(8.0001 +i);
            String stationName = "noNeed";
            String bundesLand = "noNeed";

            Station station = new Station(stationId,dateBegin,dateEnd,stationHigh,geoLatitude,geoLength,stationName,bundesLand);
            stations.add(station);

        }

        return stations;
    }
}
