package ch.studer.germanclimatedataanalyser.batch.reader;

import ch.studer.germanclimatedataanalyser.model.RawMonthTemperatureAtStationRecord;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MonthReader {

    @Autowired
    DataSource dataSource;

    public JdbcCursorItemReader<RawMonthTemperatureAtStationRecord> getMonthFromDbReader(){
        return new JdbcCursorItemReaderBuilder<RawMonthTemperatureAtStationRecord>()
                .dataSource(this.dataSource)
                .name("weatherReader")
                .sql("select STATIONS_ID,MESS_DATUM_BEGINN,MESS_DATUM_ENDE,MO_TT from MONTH")
                .rowMapper(new MonthRowMapper())
                .build();

    }

}
class MonthRowMapper implements RowMapper<RawMonthTemperatureAtStationRecord> {

    final String STATIONS_ID = "STATIONS_ID";
    final String MESS_DATUM_BEGINN = "MESS_DATUM_BEGINN";
    final String MESS_DATUM_ENDE = "MESS_DATUM_ENDE";
    final String MO_TT = "MO_TT";

    @Override
    public RawMonthTemperatureAtStationRecord mapRow(ResultSet resultSet, int i) throws SQLException {
        RawMonthTemperatureAtStationRecord monthRecord = new RawMonthTemperatureAtStationRecord();

        monthRecord.setStationId(resultSet.getString(STATIONS_ID));
        monthRecord.setMessDatumBeginn(resultSet.getString(MESS_DATUM_BEGINN));
        monthRecord.setMessDatumEnd(resultSet.getString(MESS_DATUM_ENDE));
        monthRecord.setTemperatur(resultSet.getString(MO_TT));

        return monthRecord;
    }
}


