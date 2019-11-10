package ch.studer.germanclimatedataanalyser.batch.tasklet;

import ch.studer.germanclimatedataanalyser.TestConfiguration;
import ch.studer.germanclimatedataanalyser.common.Statistic;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


@Import(TestConfiguration.class)
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
class DbCheckTest {



    @Autowired
    private Statistic statistic;


    @Before
    void prepareStatistic(){

    }
    @Test
    void checkDB() {

        DbCheck dbCheck = new DbCheck();
        dbCheck.checkDB();


    }
}