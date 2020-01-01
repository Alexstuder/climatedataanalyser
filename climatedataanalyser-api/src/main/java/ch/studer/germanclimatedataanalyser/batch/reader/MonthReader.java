package ch.studer.germanclimatedataanalyser.batch.reader;

import ch.studer.germanclimatedataanalyser.model.database.Month;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MonthReader {

    @Autowired
    DataSource dataSource;

    public JdbcCursorItemReader<Month> getMonthFromDbReader(){
        return new JdbcCursorItemReaderBuilder<Month>()
                .dataSource(this.dataSource)
                .name("weatherReader")
                .sql("select STATIONS_ID" +
                        ",MESS_DATUM_BEGINN" +
                        ",MESS_DATUM_ENDE" +
                        ",QN_4" +
                        ",MO_N" +
                        ",MO_TT" +
                        ",MO_TX" +
                        ",MO_TN" +
                        ",MO_FK" +
                        ",MX_TX" +
                        ",MX_FX" +
                        ",MX_TN" +
                        ",MO_SD_S" +
                        ",QN_6" +
                        ",MO_RR" +
                        ",MX_RS" +
                        "             from MONTH " +
                        "ORDER BY STATIONS_ID,MESS_DATUM_BEGINN ASC")
                .rowMapper(new MonthRowMapper())
                .build();

    }

}
class MonthRowMapper implements RowMapper<Month> {

    final String STATIONS_ID = "STATIONS_ID";
    final String MESS_DATUM_BEGINN = "MESS_DATUM_BEGINN";
    final String MESS_DATUM_ENDE = "MESS_DATUM_ENDE";
    final String QN_4 = "QN_4";
    final String MO_N = "MO_N";
    final String MO_TT = "MO_TT";
    final String MO_TX = "MO_TX";
    final String MO_TN = "MO_TN";
    final String MO_FK = "MO_FK";
    final String MX_TX = "MX_TX";
    final String MX_FX = "MX_FX";
    final String MX_TN = "MX_TN";
    final String MO_SD_S = "MO_SD_S";
    final String QN_6 = "QN_6";
    final String MO_RR = "MO_RR";
    final String MX_RS = "MX_RS";

    @Override
    public Month mapRow(ResultSet resultSet, int i) throws SQLException {

        Month monthRecord = new Month();
        monthRecord.setStationsId(resultSet.getInt(STATIONS_ID));
        monthRecord.setMessDatumBeginn(resultSet.getDate(MESS_DATUM_BEGINN));
        monthRecord.setMessDatumEnde(resultSet.getDate(MESS_DATUM_ENDE));
        monthRecord.setQn4(resultSet.getInt(QN_4));
        monthRecord.setMoN(resultSet.getBigDecimal(MO_N));
        monthRecord.setMoTt(resultSet.getBigDecimal(MO_TT));
        monthRecord.setMoTx(resultSet.getBigDecimal(MO_TX));
        monthRecord.setMoTn(resultSet.getBigDecimal(MO_TN));
        monthRecord.setMoFk(resultSet.getBigDecimal(MO_FK));
        monthRecord.setMxTx(resultSet.getBigDecimal(MX_TX));
        monthRecord.setMxFx(resultSet.getBigDecimal(MX_FX));
        monthRecord.setMxTn(resultSet.getBigDecimal(MX_TN));
        monthRecord.setMoSdS(resultSet.getBigDecimal(MO_SD_S));
        monthRecord.setQn6(resultSet.getInt(QN_6));
        monthRecord.setMoRr(resultSet.getBigDecimal(MO_RR));
        monthRecord.setMxRs(resultSet.getBigDecimal(MX_RS));

        return monthRecord;
    }
}


