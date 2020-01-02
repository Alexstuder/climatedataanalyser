package ch.studer.germanclimatedataanalyser.batch.processor;

import ch.studer.germanclimatedataanalyser.model.database.Month;
import ch.studer.germanclimatedataanalyser.model.file.MonthFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.math.BigDecimal;
import java.sql.Date;

public class TemperatureForMonthProcessor implements ItemProcessor<MonthFile,Month> {

    private static final Logger log = LoggerFactory.getLogger(TemperatureForMonthProcessor.class);

    @Override
    public Month process(final MonthFile monthFile) {
        final int STATIONS_ID          = Integer.valueOf(monthFile.getStationsId().trim());
        final Date MESS_DATUM_BEGINN   = getSQLDate(monthFile.getMessDatumBeginn().trim());
        final Date MESS_DATUM_ENDE     = getSQLDate(monthFile.getMessDatumEnde().trim());
        final int QN_4                 = Integer.valueOf(monthFile.getQn4().trim());
        final BigDecimal MO_N          = BigDecimal.valueOf(Double.valueOf(monthFile.getMoN().trim()));
        final BigDecimal  MO_TT        = BigDecimal.valueOf(Double.valueOf(monthFile.getMoTt().trim()));
        final BigDecimal MO_TX         = BigDecimal.valueOf(Double.valueOf(monthFile.getMoTx().trim()));
        final BigDecimal MO_TN         = BigDecimal.valueOf(Double.valueOf(monthFile.getMoTn().trim()));
        final BigDecimal MO_FK         = BigDecimal.valueOf(Double.valueOf(monthFile.getMoFk().trim()));
        final BigDecimal MX_TX         = BigDecimal.valueOf(Double.valueOf(monthFile.getMxTx().trim()));
        final BigDecimal MX_FX         = BigDecimal.valueOf(Double.valueOf(monthFile.getMxFx().trim()));
        final BigDecimal MX_TN         = BigDecimal.valueOf(Double.valueOf(monthFile.getMxTn().trim()));
        final BigDecimal MO_SD_S       = BigDecimal.valueOf(Double.valueOf(monthFile.getMoSdS().trim()));
        final int QN_6                 = Integer.valueOf(monthFile.getQn6().trim());
        final BigDecimal MO_RR         = BigDecimal.valueOf(Double.valueOf(monthFile.getMoRr().trim()));
        final BigDecimal MX_RS         = BigDecimal.valueOf(Double.valueOf(monthFile.getMxRs().trim()));

        final Month transformedMonth = new Month(STATIONS_ID
                                           ,MESS_DATUM_BEGINN
                                           ,MESS_DATUM_ENDE
                                           ,QN_4
                                           ,MO_N
                                           ,MO_TT
                                           ,MO_TX
                                           ,MO_TN
                                           ,MO_FK
                                           ,MX_TX
                                           ,MX_FX
                                           ,MX_TN
                                           ,MO_SD_S
                                           ,QN_6
                                           ,MO_RR
                                           ,MX_RS);

        return transformedMonth;
    }

    private Date getSQLDate(String dateIn) {

        return   Date.valueOf(dateIn.substring(0,4)+"-"+dateIn.substring(4,6)+"-"+dateIn.substring(6,8));

    }

}
