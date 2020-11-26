package ch.studer.germanclimatedataanalyser.dao;

import ch.studer.germanclimatedataanalyser.service.ui.dbController.DbLoadRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DbLoadInformationeImpl implements DbLoadInformationeDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger LOG = LoggerFactory.getLogger(DbLoadInformationeImpl.class);


    @Override
    public List<DbLoadRowMapper.JobExecutionInformation> getDbLoadInformation() {
        return  jdbcTemplate.query(
                "SELECT j.End_Time\n" +
                        "      ,j.Status\n" +
                        "      ,s.Step_Name\n" +
                        "      ,s.Start_Time\n" +
                        "      ,s.End_Time as Step_End_Time\n" +
                        "      ,s.Read_Count\n" +
                        "      ,s.Write_Count\n" +
                        "      ,s.Status as Step_Status\n" +
                        "      \n" +
                        "      FROM climate.batch_job_execution j ,climate.batch_step_execution s\n" +
                        "\n" +
                        "\n" +
                        "where j.Job_execution_id = s.job_execution_id \n" +
                        "and j.job_execution_id = (select max(JOB_EXECUTION_ID) from climate.batch_job_execution)\n" +
                        "order by s.step_execution_id;", new DbLoadRowMapper());
    }

    @Override
    public int getMonthTableCount() {

        Integer counter;
        counter = jdbcTemplate.queryForObject("SELECT count(*) FROM climate.month;", Integer.class);

        return counter;
    }


}
