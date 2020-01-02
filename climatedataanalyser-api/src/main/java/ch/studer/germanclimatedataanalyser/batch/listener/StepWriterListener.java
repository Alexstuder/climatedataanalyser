package ch.studer.germanclimatedataanalyser.batch.listener;

import ch.studer.germanclimatedataanalyser.model.database.Month;
import org.springframework.batch.core.ItemWriteListener;

import java.util.List;


public class StepWriterListener implements ItemWriteListener<Month> {


    public StepWriterListener() {

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
