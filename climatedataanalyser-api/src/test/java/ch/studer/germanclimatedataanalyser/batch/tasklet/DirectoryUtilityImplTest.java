package ch.studer.germanclimatedataanalyser.batch.tasklet;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DirectoryUtilityImplTest {

    private static final String folderName = "TestDirectory";
    private static final Logger log = LoggerFactory.getLogger(DirectoryUtilityImplTest.class);

    @Test
    public void test() {
        DirectoryUtilityImpl.createDir(folderName);
        DirectoryUtilityImpl.getDirectory(folderName);
    }
}
