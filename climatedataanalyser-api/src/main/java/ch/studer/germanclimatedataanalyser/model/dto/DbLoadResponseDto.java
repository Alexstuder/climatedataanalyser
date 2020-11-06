package ch.studer.germanclimatedataanalyser.model.dto;

import ch.studer.germanclimatedataanalyser.service.ui.dbController.DbLoadRowMapper;

import java.util.ArrayList;
import java.util.List;

public class DbLoadResponseDto {

    private String isDbLoaded;
    private String lastLoad;
    private String status;
    private List<DbLoadStep> dbLoadSteps = new ArrayList<DbLoadStep>();

    public DbLoadResponseDto(List<DbLoadRowMapper.JobExecutionInformation> dbLoadInformation, boolean isDbLoaded) {
        this.mapToDbLoadResponsDto(dbLoadInformation, isDbLoaded);

    }


    public void mapToDbLoadResponsDto(List<DbLoadRowMapper.JobExecutionInformation> jobExecutionInformations, boolean isDbLoaded) {
        this.isDbLoaded = getTextForIsDbLoaded(isDbLoaded);
        this.lastLoad = jobExecutionInformations.get(0).endTime;
        this.status = jobExecutionInformations.get(0).status;


        for (DbLoadRowMapper.JobExecutionInformation jobExecutionInformation : jobExecutionInformations) {
            DbLoadStep dbLoadStep = new DbLoadStep(
                    jobExecutionInformation.getStepName()
                    , jobExecutionInformation.getStartTime()
                    , jobExecutionInformation.getStepEndTime()
                    , jobExecutionInformation.getReadCount()
                    , jobExecutionInformation.getWriteCount()
                    , jobExecutionInformation.getStepStatus()
            );
            dbLoadSteps.add(dbLoadStep);

        }
    }

    private String getTextForIsDbLoaded(boolean isDbLoaded) {
        String text = "";

        if (isDbLoaded) {
            text = "DB is loaded!";
        } else {

            text = "DB is not loaded!";
        }


        return text;
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

    public String getIsDbLoaded() {
        return isDbLoaded;
    }

    public void setIsDbLoaded(String isDbLoaded) {
        this.isDbLoaded = isDbLoaded;
    }
}
