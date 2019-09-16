package ch.studer.germanclimatedataanalyser.batch.reader;

import ch.studer.germanclimatedataanalyser.model.MonthFile;
import org.springframework.batch.item.*;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;


public class ClimatMonthFlatFileReader extends FlatFileItemReader<MonthFile>  implements ItemReader<MonthFile> {

        //Index for ItemReader
        private int nextItem;

        //Has the actual Month
        private MonthFile actualMonthFile = null;

        // If we want to read all $files from a directory ,we ned first all Filenames
        private File[] files ;

        public ClimatMonthFlatFileReader() {

                //Get all Files in Input directory
               files = getAllFilesfromDirectory("InputFiles");
                for (File file : files){
                        System.out.println(file.getName());
                }
                  // First Initialisation
                  initFlatFileItemReader("produkt_klima_monat_19710301_20181231_00044.txt");
                  //initFlatFileItemReader(files[0].getName());

        }

        private File[] getAllFilesfromDirectory(String directory) {
                ClassLoader loader = Thread.currentThread().getContextClassLoader();
                URL url = loader.getResource(directory);

                File[] files;
                try {
                String path = url.getPath();
                files = new File(path).listFiles();

                } catch (Exception e) {
                  throw new RuntimeException("No Input File in /InputFile to read !!");
                }


                return files ;

        }

        public void initFlatFileItemReader(String fileName) {

                // Set the index on 0
                nextItem = 0;

                //Skip first Line from File ; Header !
                this.setLinesToSkip(1);
                this.setResource(new ClassPathResource("InputFiles/" + fileName ));
                this.setLineMapper(new DefaultLineMapper<MonthFile>() {{
                        setLineTokenizer(new DelimitedLineTokenizer() {{
                                setNames(new String[]{"stationsId"
                                        , "messDatumBeginn"
                                        , "messDatumEnde"
                                        , "qn4"
                                        , "moN"
                                        , "moTt"
                                        , "moTx"
                                        , "moTn"
                                        , "moFk"
                                        , "mxTx"
                                        , "mxFx"
                                        , "mxTn"
                                        , "moSdS"
                                        , "qn6"
                                        , "moRr"
                                        , "mxRs"
                                        , "eor"});
                                setDelimiter(";");
                        }});
                        setFieldSetMapper(new BeanWrapperFieldSetMapper<MonthFile>() {{

                                setTargetType(MonthFile.class);
                        }});
                }});


        }

        @Override
        public MonthFile read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

                for (File file : files) {
                        System.out.println("Read File :" + file.getName());
                        // Initiate the FlatItemReader
                        initFlatFileItemReader(file.getName());

                        // Open the File
                        this.doOpen();

                        // Read till EOF
                        nextItem = getCurrentItemCount();
                        super.jumpToItem(nextItem++);
                        actualMonthFile = super.read();

                        if (actualMonthFile == null) {
                                super.doClose();
                        }

                        // if the actualMonthFile == null , the processor has to handel !
                        return actualMonthFile;

                }






        return null;
        }
}