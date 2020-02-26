package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.dao.DbLoadInformationeDAO;
import ch.studer.germanclimatedataanalyser.model.dto.DbLoadResponseDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DbLoadInformationServiceImpl implements DbLoadInformationService {

    @Autowired
    DbLoadInformationeDAO dbLoadInformationeDAO;


    @Override
    public DbLoadResponseDto getDbLoadInformation() {

        //List<DbLoadRowMapper.JobExecutionInformation> data = dbLoadInformationeDAO.getDbLoadInformation();
        DbLoadResponseDto dbLoadResponseDto = new DbLoadResponseDto(dbLoadInformationeDAO.getDbLoadInformation());



        return dbLoadResponseDto;
    }
}
