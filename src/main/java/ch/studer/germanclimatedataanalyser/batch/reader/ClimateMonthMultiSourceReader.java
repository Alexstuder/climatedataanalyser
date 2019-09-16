package ch.studer.germanclimatedataanalyser.batch.reader;

import ch.studer.germanclimatedataanalyser.model.Month;
import ch.studer.germanclimatedataanalyser.model.MonthFile;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;

public class ClimateMonthMultiSourceReader extends MultiResourceItemReader<MonthFile> implements ItemReader<MonthFile> {

    public Resource[] inputResources = null;
    public MultiResourceItemReader<MonthFile> resourceItemReader ;
    public FlatFileItemReader<MonthFile> reader;

    public ClimateMonthMultiSourceReader(){

        resourceItemReader = new MultiResourceItemReader<MonthFile>();
        System.out.println("Consturctor Aufruf");

        FileSystemXmlApplicationContext patternResolver = new FileSystemXmlApplicationContext();
        try {
            inputResources = patternResolver.getResources("src/main/resources/InputFiles/produkt*.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }


      resourceItemReader.setResources(inputResources);
      resourceItemReader.setDelegate(reader());
    }

    /*public MultiResourceItemReader<MonthFile> multiSourceItemReader(){


      return resourceItemReader;

    }*/

    public FlatFileItemReader<MonthFile> reader()
    {
        //Create reader instance
        reader = new FlatFileItemReader<MonthFile>();

        //Set number of lines to skips. Use it if file has header rows.
        reader.setLinesToSkip(1);

        //Configure how each line will be parsed and mapped to different values
        reader.setLineMapper(new DefaultLineMapper<MonthFile>() {
            {
                //3 columns in each row
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
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
                    }
                });
                //Set values in Employee class
                setFieldSetMapper(new BeanWrapperFieldSetMapper<MonthFile>() {
                    {
                        setTargetType(MonthFile.class);
                    }
                });
            }
        });
        return reader;
    }


}
