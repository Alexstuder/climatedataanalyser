package ch.studer.germanclimatedataanalyser.model.dto;

import ch.studer.germanclimatedataanalyser.service.DbLoadRowMapper;

import java.util.ArrayList;
import java.util.List;

public class DbLoadResponseDto {

    private String lastLoad;
    private String status;
    private List<DbLoadStep> dbLoadSteps = new ArrayList<DbLoadStep>();

    public DbLoadResponseDto(List<DbLoadRowMapper.JobExecutionInformation> dbLoadInformation) {
        this.mapToDbLoadResponsDto(dbLoadInformation);
    }


    public void mapToDbLoadResponsDto(List<DbLoadRowMapper.JobExecutionInformation> jobExecutionInformations){
        this.lastLoad = jobExecutionInformations.get(0).endTime;
        this.status = jobExecutionInformations.get(0).status;


        for(DbLoadRowMapper.JobExecutionInformation jobExecutionInformation:jobExecutionInformations){
           DbLoadStep dbLoadStep = new DbLoadStep(
                    jobExecutionInformation.getStepName()
                   ,jobExecutionInformation.getStartTime()
                   ,jobExecutionInformation.getStepEndTime()
                   ,jobExecutionInformation.getReadCount()
                   ,jobExecutionInformation.getWriteCount()
                   ,jobExecutionInformation.getStepStatus()
           );
           dbLoadSteps.add(dbLoadStep);

        }
    }


    public String getLastLoad() {
        return lastLoad;
    }

    public void setLastLoad(String lastLoad) {
        this.lastLoad = lastLoad;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DbLoadStep> getDbLoadSteps() {
        return dbLoadSteps;
    }

    public void setDbLoadSteps(List<DbLoadStep> dbLoadSteps) {
        this.dbLoadSteps = dbLoadSteps;
    }
}
