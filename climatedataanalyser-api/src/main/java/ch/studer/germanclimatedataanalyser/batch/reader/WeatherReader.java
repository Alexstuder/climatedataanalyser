package ch.studer.germanclimatedataanalyser.batch.reader;

import ch.studer.germanclimatedataanalyser.model.database.StationWeatherPerYear;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WeatherReader {

    @Autowired
    DataSource dataSource;

    public JdbcCursorItemReader<StationWeatherPerYear> getWeatherFromDbReader() {
        return new JdbcCursorItemReaderBuilder<StationWeatherPerYear>()
                .dataSource(this.dataSource)
                .name("weatherReader")
                .sql("select STATION_ID" +
                        ",YEAR" +
                        ",JANUAR" +
                        ",FEBRUAR" +
                        ",MAERZ" +
                        ",APRIL" +
                        ",MAI" +
                        ",JUNI" +
                        ",JULI" +
                        ",AUGUST" +
                        ",SEPTEMBER" +
                        ",OKTOBER" +
                        ",NOVEMBER" +
                        ",DEZEMBER" +
                        "             from WEATHER" +
                        " ORDER BY STATION_ID, YEAR DESC")
                .rowMapper(new StationWeatherRowMapper())
                .build();

    }

}

class StationWeatherRowMapper implements RowMapper<StationWeatherPerYear> {

    final String STATION_ID = "STATION_ID";
    final String YEAR = "YEAR";
    final String JANUAR = "JANUAR";
    final String FEBRUAR = "FEBRUAR";
    final String MAERZ = "MAERZ";
    final String APRIL = "APRIL";
    final String MAI = "MAI";
    final String JUNI = "JUNI";
    final String JULI = "JULI";
    final String AUGUST = "AUGUST";
    final String SEPTEMBER = "SEPTEMBER";
    final String OKTOBER = "OKTOBER";
    final String NOVEMBER = "NOVEMBER";
    final String DEZEMBER = "DEZEMBER";

    @Override
    public StationWeatherPerYear mapRow(ResultSet resultSet, int i) throws SQLException {

        StationWeatherPerYear stationWeatherPerYear = new StationWeatherPerYear(resultSet.getInt(STATION_ID));
        stationWeatherPerYear.setYear(resultSet.getString(YEAR));
        stationWeatherPerYear.setJanuar(resultSet.getBigDecimal(JANUAR));
        stationWeatherPerYear.setFebruar(resultSet.getBigDecimal(FEBRUAR));
        stationWeatherPerYear.setMaerz(resultSet.getBigDecimal(MAERZ));
        stationWeatherPerYear.setApril(resultSet.getBigDecimal(APRIL));
        stationWeatherPerYear.setMai(resultSet.getBigDecimal(MAI));
        stationWeatherPerYear.setJuni(resultSet.getBigDecimal(JUNI));
        stationWeatherPerYear.setJuli(resultSet.getBigDecimal(JULI));
        stationWeatherPerYear.setAugust(resultSet.getBigDecimal(AUGUST));
        stationWeatherPerYear.setSeptember(resultSet.getBigDecimal(SEPTEMBER));
        stationWeatherPerYear.setOktober(resultSet.getBigDecimal(OKTOBER));
        stationWeatherPerYear.setNovember(resultSet.getBigDecimal(NOVEMBER));
        stationWeatherPerYear.setDezember(resultSet.getBigDecimal(DEZEMBER));

        return stationWeatherPerYear;
    }
}


