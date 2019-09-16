package ch.studer.germanclimatedataanalyser.batch.listener;

import ch.studer.germanclimatedataanalyser.common.Statistics;
import ch.studer.germanclimatedataanalyser.model.Month;
import ch.studer.germanclimatedataanalyser.model.MonthFile;
import ch.studer.germanclimatedataanalyser.model.StatisticRecord;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.ItemWriteListener;

import java.util.List;


public class StepWriterListener implements ItemWriteListener<Month> {

   private Statistics statistics;

    public StepWriterListener(Statistics statistics) {

        this.statistics = statistics;
    }

    @Override
    public void beforeWrite(List<? extends Month> items) {

    }

    @Override
    public void afterWrite(List<? extends Month> items) {


    }

    @Override
    public void onWriteError(Exception exception, List<? extends Month> items) {

    }
}
