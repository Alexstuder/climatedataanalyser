package ch.studer.germanclimatedataanalyser.model.dto.db;

import ch.studer.germanclimatedataanalyser.service.ui.dbController.DbLoadRowMapper;
import ch.studer.germanclimatedataanalyser.service.ui.dbController.DbStatusEnum;

import java.util.ArrayList;
import java.util.List;

public class DbLoadResponseDto {

    private boolean dbLoadStatus;
    private String lastLoad;
    private String status;
    private List<DbLoadStep> dbLoadSteps = new ArrayList<DbLoadStep>();

    public DbLoadResponseDto(List<DbLoadRowMapper.JobExecutionInformation> dbLoadInformation, DbStatusEnum dbStatus) {
        this.mapToDbLoadResponsDto(dbLoadInformation, dbStatus);

    }


    public void mapToDbLoadResponsDto(List<DbLoadRowMapper.JobExecutionInformation> jobExecutionInformations, DbStatusEnum dbStatus) {
        this.dbLoadStatus = dbStatus == DbStatusEnum.loaded;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean getDbLoadStatus() {
        return dbLoadStatus;
    }

    public void setDbLoadStatus(boolean dbLoadStat) {
        dbLoadStatus = dbLoadStat;
    }

    public String getLastLoad() {
        return lastLoad;
    }

    public void setLastLoad(String lastLoad) {
        this.lastLoad = lastLoad;
    }

    public List<DbLoadStep> getDbLoadSteps() {
        return dbLoadSteps;
    }

    public void setDbLoadSteps(List<DbLoadStep> dbLoadSteps) {
        this.dbLoadSteps = dbLoadSteps;
    }
}
