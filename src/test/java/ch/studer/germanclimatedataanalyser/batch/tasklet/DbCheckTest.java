package ch.studer.germanclimatedataanalyser.batch.tasklet;

import ch.studer.germanclimatedataanalyser.common.Statistic;
import ch.studer.germanclimatedataanalyser.model.StatisticRecord;
import ch.studer.germanclimatedataanalyser.service.MonthService;
import org.junit.Before;
import org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class DbCheckTest {



    @Mock
    Statistic statistic;

    @Mock
    MonthService monthService;

    @InjectMocks
    DbCheck dbCheck;

    @Before
    public void prepareStatistic(){

    }
    @Test
    public void checkDB() {

        when(statistic.getStatisticRecords()).thenReturn(getStatisticRecords());
        //when(monthService.getCountOnDb(1)).thenReturn(getMonthService());
        assertEquals(true,dbCheck.addCountOnDbToStatisticRecord());



    }



    private ArrayList<StatisticRecord> getStatisticRecords(){
        ArrayList<StatisticRecord> statisticRecords = new ArrayList<StatisticRecord>();

        StatisticRecord statisticRecord = new StatisticRecord();
        statisticRecord.setStationsID(1);

        statisticRecords.add(statisticRecord);


     return  statisticRecords;
    }

    private int getMonthService(){
        int countOnDb = 1;

        return countOnDb;
    }

}