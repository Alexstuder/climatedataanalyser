package ch.studer.germanclimatedataanalyser.batch.writer;

import ch.studer.germanclimatedataanalyser.common.Statistics;
import ch.studer.germanclimatedataanalyser.dao.MonthDAO;
import ch.studer.germanclimatedataanalyser.model.Month;
import ch.studer.germanclimatedataanalyser.service.MonthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.scope.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.batch.runtime.StepExecution;
import javax.sql.DataSource;
import java.util.List;


public class ClimateMonthDBWriter implements ItemWriter<Month> {

    private static final Logger log = LoggerFactory.getLogger(ClimateMonthDBWriter.class);

    @Autowired
    private Statistics statistics;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MonthService monthService;

    public ClimateMonthDBWriter(){
    }

    @Override
    public void write(List<? extends Month> months) throws Exception {

        monthService.saveAllMonth(months);



    }
}
