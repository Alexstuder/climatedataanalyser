package ch.studer.germanclimatedataanalyser.batch.listener;

import ch.studer.germanclimatedataanalyser.common.Statistic;
import ch.studer.germanclimatedataanalyser.model.Month;
import ch.studer.germanclimatedataanalyser.model.file.MonthFile;
import ch.studer.germanclimatedataanalyser.model.StatisticRecord;
import org.springframework.batch.core.ItemProcessListener;


public class StepProcessorListener implements ItemProcessListener<MonthFile,Month> {

    //TODO : Ask Tamas why is Autowired not working ? Get null on  statistic !
    //@Autowired
    private Statistic statistic;

    public StepProcessorListener(Statistic statistic) {

        this.statistic = statistic;
    }


    @Override
    public void beforeProcess(MonthFile monthFile) {

        //If New Stations ID ; Add actual to Statistic
        //TODO A.Studer : getStationsID() == 0  Prüfung sollte auf NULL ändern !
        if (statistic.checkIsNewStationsID(monthFile.getStationsId())){

            if (statistic.getActual().getStationsID() == 0 ) {
                statistic.getActual().setStationsID(Integer.valueOf(monthFile.getStationsId().trim()));
                statistic.getActual().setFirstDatum(monthFile.getMessDatumBeginn());
            } else {
                // Add Record to statistic
                statistic.addActualToStatistics();
                // Get a new actual
                statistic.setActual(new StatisticRecord());

            }
        } else {
        }




    }

    @Override
    public void afterProcess(MonthFile monthFile, Month result) {

                // Set the Last MessDatumEnde
                statistic.getActual().setLastDatum(monthFile.getMessDatumEnde());

    }

    @Override
    public void onProcessError(MonthFile item, Exception e) {

    }
}
