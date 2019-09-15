package ch.studer.germanclimatedataanalyser.batch.processor;

import ch.studer.germanclimatedataanalyser.model.Month;

import ch.studer.germanclimatedataanalyser.model.MonthFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class ClimateMonthProcessor  implements ItemProcessor<MonthFile,Month> {

    private static final Logger log = LoggerFactory.getLogger(ClimateMonthProcessor.class);



    @Override
    public Month process(final MonthFile monthFile) {
        final int STATIONS_ID          = Integer.valueOf(monthFile.getStationsId().trim());
        final int MESS_DATUM_BEGINN    = Integer.valueOf(monthFile.getMessDatumBeginn().trim());
        final int MESS_DATUM_ENDE      = Integer.valueOf(monthFile.getMessDatumEnde().trim());
        final int QN_4                 = Integer.valueOf(monthFile.getQn4().trim());
        final double MO_N              = Double.valueOf(monthFile.getMoN().trim());
        final double  MO_TT            = Double.valueOf(monthFile.getMoTt().trim());
        final double MO_TX             = Double.valueOf(monthFile.getMoTx().trim());
        final double MO_TN             = Double.valueOf(monthFile.getMoTn().trim());
        final double MO_FK             = Double.valueOf(monthFile.getMoFk().trim());
        final double MX_TX             = Double.valueOf(monthFile.getMxTx().trim());
        final double MX_FX             = Double.valueOf(monthFile.getMxFx().trim());
        final double MX_TN             = Double.valueOf(monthFile.getMxTn().trim());
        final double MO_SD_S           = Double.valueOf(monthFile.getMoSdS().trim());
        final int QN_6                 = Integer.valueOf(monthFile.getQn6().trim());
        final double MO_RR             = Double.valueOf(monthFile.getMoRr().trim());
        final double MX_RS             = Double.valueOf(monthFile.getMxRs().trim());

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

        log.info("Station" + STATIONS_ID + "for Period "+ MESS_DATUM_BEGINN +" - " + MESS_DATUM_ENDE  +"transformed");
        return transformedMonth;
    }
}
