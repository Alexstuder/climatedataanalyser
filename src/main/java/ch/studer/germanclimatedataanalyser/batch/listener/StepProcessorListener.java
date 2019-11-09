package ch.studer.germanclimatedataanalyser.batch.listener;

import ch.studer.germanclimatedataanalyser.common.Statistics;
import ch.studer.germanclimatedataanalyser.common.StatisticsImpl;
import ch.studer.germanclimatedataanalyser.model.Month;
import ch.studer.germanclimatedataanalyser.model.MonthFile;
import ch.studer.germanclimatedataanalyser.model.StatisticRecord;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.beans.factory.annotation.Autowired;



public class StepProcessorListener implements ItemProcessListener<MonthFile,Month> {

    //TODO : Ask Tamas why is Autowired not working ? Get null on  statistics !
    //@Autowired
    private Statistics statistics;

    public StepProcessorListener(Statistics statistics) {

        this.statistics = statistics;
    }


    @Override
    public void beforeProcess(MonthFile monthFile) {

        //If New Stations ID ; Add actual to Statistic
        //TODO A.Studer : getStationsID() == 0  Prüfung sollte auf NULL ändern !
        if (statistics.checkIsNewStationsID(monthFile.getStationsId())){

            if (statistics.getActual().getStationsID() == 0 ) {
                statistics.getActual().setStationsID(Integer.valueOf(monthFile.getStationsId().trim()));
                statistics.getActual().setFirstDatum(monthFile.getMessDatumBeginn());
            } else {
                // Add Record to statistic
                statistics.addActualToStatistics();
                // Get a new actual
                statistics.setActual(new StatisticRecord());

            }
        } else {
        }




    }

    @Override
    public void afterProcess(MonthFile monthFile, Month result) {

                // Set the Last MessDatumEnde
                statistics.getActual().setLastDatum(monthFile.getMessDatumEnde());

    }

    @Override
    public void onProcessError(MonthFile item, Exception e) {

    }
}
