package ch.studer.germanclimatedataanalyser.batch.tasklet;


import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class DirectoryUtilityTest {

    @Autowired
    private ApplicationContext applicationContext;
    private String directoryName = "TestFtpData";
    private Resource[] resources;

    @Before
    void setUp() {
        // get the directory
        System.out.println("Hier test");
        try {
            resources = (Resource[]) applicationContext.getResources("classpath*:/" + directoryName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(resources.length!=0){
           // TODO :  remove testDirectory so we can test : mkdir / getdir and removeDir
            for (Resource dir : resources){
                try {
                    dir.getFile().delete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    void test() {
        //   create Directory


        // get Directory


        // remove Directory


    }

}
