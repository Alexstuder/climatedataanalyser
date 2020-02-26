package ch.studer.germanclimatedataanalyser.service;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DbLoadRowMapper implements RowMapper<DbLoadRowMapper.JobExecutionInformation> {

        @Override
        public JobExecutionInformation mapRow(ResultSet rs, int rowNum) throws SQLException {
            JobExecutionInformation jobExecutionInformation = new JobExecutionInformation();

            jobExecutionInformation.setEndTime(rs.getString("END_TIME"));
            jobExecutionInformation.setStatus(rs.getString("STATUS"));
            jobExecutionInformation.setStepName(rs.getString("STEP_NAME"));
            jobExecutionInformation.setStartTime(rs.getString("START_TIME"));
            jobExecutionInformation.setStepEndTime(rs.getString("STEP_END_TIME"));
            jobExecutionInformation.setReadCount(rs.getString("READ_COUNT"));
            jobExecutionInformation.setWriteCount(rs.getString("WRITE_COUNT"));
            jobExecutionInformation.setStepStatus(rs.getString("STEP_STATUS"));

            return jobExecutionInformation;
        }

        public class JobExecutionInformation {

                    public String endTime;
                    public String status;
                    public String stepName;
                    public String startTime;
                    public String stepEndTime;
                    public String readCount;
                    public String writeCount;
                    public String stepStatus;

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getStepName() {
                return stepName;
            }

            public void setStepName(String stepName) {
                this.stepName = stepName;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getStepEndTime() {
                return stepEndTime;
            }

            public void setStepEndTime(String stepEndTime) {
                this.stepEndTime = stepEndTime;
            }

            public String getReadCount() {
                return readCount;
            }

            public void setReadCount(String readCount) {
                this.readCount = readCount;
            }

            public String getWriteCount() {
                return writeCount;
            }

            public void setWriteCount(String writeCount) {
                this.writeCount = writeCount;
            }

            public String getStepStatus() {
                return stepStatus;
            }

            public void setStepStatus(String stepStatus) {
                this.stepStatus = stepStatus;
            }
        }
}


