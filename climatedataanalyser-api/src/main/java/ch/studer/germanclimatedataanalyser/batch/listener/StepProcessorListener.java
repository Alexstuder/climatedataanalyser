package ch.studer.germanclimatedataanalyser.batch.listener;

import ch.studer.germanclimatedataanalyser.model.database.Month;
import ch.studer.germanclimatedataanalyser.model.file.MonthFile;
import org.springframework.batch.core.ItemProcessListener;


public class StepProcessorListener implements ItemProcessListener<MonthFile,Month> {


    public StepProcessorListener() {


    }


    @Override
    public void beforeProcess(MonthFile monthFile) {



    }

    @Override
    public void afterProcess(MonthFile monthFile, Month result) {



    }

    @Override
    public void onProcessError(MonthFile item, Exception e) {

    }
}
