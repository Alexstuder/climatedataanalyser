package ch.studer.germanclimatedataanalyser.service.ui.dbController;

import ch.studer.germanclimatedataanalyser.dao.DbLoadInformationeDAO;
import ch.studer.germanclimatedataanalyser.model.dto.db.DbLoadResponseDto;
import org.springframework.beans.factory.annotation.Autowired;

public class DbLoadInformationServiceImpl implements DbLoadInformationService {

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    DbLoadInformationeDAO dbLoadInformationeDAO;


    @Override
    public DbLoadResponseDto getDbLoadInformation() {
        return new DbLoadResponseDto(dbLoadInformationeDAO.getDbLoadInformation(), isDbLoaded());
    }

    @Override
    public boolean isDbLoaded() {
        return (dbLoadInformationeDAO.getMonthTableCount() > 0);
    }
}
