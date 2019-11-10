package ch.studer.germanclimatedataanalyser;

import ch.studer.germanclimatedataanalyser.common.Statistic;
import ch.studer.germanclimatedataanalyser.common.StatisticImpl;
import ch.studer.germanclimatedataanalyser.model.StatisticRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;

@org.springframework.boot.test.context.TestConfiguration
@Configuration
public class TestConfiguration {

    @Bean
    Statistic statistic(){
        Statistic statistic = new StatisticImpl();
        statistic.getStatisticRecords().addAll(getStatisticRecords());



        return  statistic;
    }

    private Collection<? extends StatisticRecord> getStatisticRecords() {

        Collection<StatisticRecord> statisticRecords = new ArrayList<StatisticRecord>();

        // Create a Statistic Record and add it to Statistics
        StatisticRecord statisticRecord1 = new StatisticRecord();

        statisticRecord1.setStationsID(4711);
        statisticRecord1.setAnzahlProcess(10);
        statisticRecord1.setAnzahlOnDb(10);

        statisticRecords.add(statisticRecord1);



        // Create another Statistic Record and add it to Statistics
        StatisticRecord statisticRecord2 = new StatisticRecord();
        statisticRecord2.setStationsID(4711);
        statisticRecord2.setAnzahlProcess(10);
        statisticRecord2.setAnzahlOnDb(9);

        statisticRecords.add(statisticRecord2);


        return  statisticRecords;


    }


}
