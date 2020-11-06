package ch.studer.germanclimatedataanalyser.batch.writer;

import ch.studer.germanclimatedataanalyser.model.database.Month;
import ch.studer.germanclimatedataanalyser.service.db.MonthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.util.List;


public class TemperatureForMonthDBWriter implements ItemWriter<Month> {

    private static final Logger log = LoggerFactory.getLogger(TemperatureForMonthDBWriter.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MonthService monthService;

    public TemperatureForMonthDBWriter() {
    }

    @Override
    public void write(List<? extends Month> months) throws Exception {

        monthService.saveAllMonth(months);


    }
}
