package ch.studer.germanclimatedataanalyser.batch.config;

import ch.studer.germanclimatedataanalyser.batch.listener.JobCompletionNotificationListener;
import ch.studer.germanclimatedataanalyser.batch.processor.ClimateMonthProcessor;
import ch.studer.germanclimatedataanalyser.model.Month;
import ch.studer.germanclimatedataanalyser.model.MonthFile;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import javax.validation.constraints.Null;

@Configuration
@EnableBatchProcessing
public class ClimateMonthBatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Bean
    public FlatFileItemReader<MonthFile> reader() {
        FlatFileItemReader<MonthFile> reader = new FlatFileItemReader<MonthFile>();
        reader.setLinesToSkip(1);
        reader.setResource(new ClassPathResource("InputFiles/produkt_klima_monat_19710301_20181231_00044.txt"));
        reader.setLineMapper(new DefaultLineMapper<MonthFile>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"stationsId"
                                      ,"messDatumBeginn"
                                      ,"messDatumEnde"
                                      ,"qn4"
                                      ,"moN"
                                      ,"moTt"
                                      ,"moTx"
                                      ,"moTn"
                                      ,"moFk"
                                      ,"mxTx"
                                      ,"mxFx"
                                      ,"mxTn"
                                      ,"moSdS"
                                      ,"qn6"
                                      ,"moRr"
                                      ,"mxRs"
                                      ,"eor" });
                setDelimiter(";");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<MonthFile>() {{
                setTargetType(MonthFile.class);
            }});
        }});
        return reader;
    }

    @Bean
    public ClimateMonthProcessor processor() {
        return new ClimateMonthProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Month> writer() {

      JdbcBatchItemWriter<Month> writer = new JdbcBatchItemWriter<Month>();

      writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Month>());
      writer.setSql("INSERT INTO month(STATIONS_ID,MESS_DATUM_BEGINN,MESS_DATUM_ENDE,QN_4,MO_N,MO_TT,MO_TX,MO_TN,MO_FK,MX_TX,MX_FX,MX_TN,MO_SD_S,QN_6,MO_RR,MX_RS)"
                  + "VALUES (:stationsId,:messDatumBeginn,:messDatumEnde,:qn4,:moN,:moTt,:moTx,:moTn,:moFk,:mxTx,:mxFx,:mxTn,:moSdS,:qn6,:moRr,:mxRs)"
                  );
      writer.setDataSource(dataSource);

        return writer;

    }

    @Bean
    public Job importClimateMonthDataJob(JobCompletionNotificationListener listener){
        return jobBuilderFactory.get("importClimateMonthDataJob")
               .incrementer(new RunIdIncrementer())
               .listener(listener)
               .flow(step01())
               .end()
               .build()
                ;
    }

    @Bean
    public Step step01(){
        return stepBuilderFactory.get("step01")
                .<MonthFile,Month> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build()
                ;
    }

}
