package ch.studer.germanclimatedataanalyser.dao;

import ch.studer.germanclimatedataanalyser.service.DbLoadRowMapper;

import java.util.List;

public interface DbLoadInformationeDAO {

    public List<DbLoadRowMapper.JobExecutionInformation> getDbLoadInformation();
}
