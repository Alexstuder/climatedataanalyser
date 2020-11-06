package ch.studer.germanclimatedataanalyser.dao;

import ch.studer.germanclimatedataanalyser.service.ui.dbController.DbLoadRowMapper;

import java.util.List;

public interface DbLoadInformationeDAO {

    List<DbLoadRowMapper.JobExecutionInformation> getDbLoadInformation();

    int getMonthTableCount();
}
