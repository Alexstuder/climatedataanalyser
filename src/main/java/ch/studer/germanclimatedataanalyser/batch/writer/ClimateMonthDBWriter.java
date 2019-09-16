package ch.studer.germanclimatedataanalyser.batch.writer;

import ch.studer.germanclimatedataanalyser.model.Month;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.util.List;


public class ClimateMonthDBWriter extends JdbcBatchItemWriter<Month> implements ItemWriter<Month> {


    public ClimateMonthDBWriter(DataSource dataSource){
        init(dataSource);
    }

    public void init(DataSource dataSource){

        this.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Month>());
        this.setSql("INSERT INTO month(STATIONS_ID,MESS_DATUM_BEGINN,MESS_DATUM_ENDE,QN_4,MO_N,MO_TT,MO_TX,MO_TN,MO_FK,MX_TX,MX_FX,MX_TN,MO_SD_S,QN_6,MO_RR,MX_RS)"
                + "VALUES (:stationsId,:messDatumBeginn,:messDatumEnde,:qn4,:moN,:moTt,:moTx,:moTn,:moFk,:mxTx,:mxFx,:mxTn,:moSdS,:qn6,:moRr,:mxRs)"
        );
        super.setDataSource(dataSource);
    }

    public void writer(List<Month> months) {

        try {
            super.write(months);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void write(List<? extends Month> items) throws Exception {

    }
}
