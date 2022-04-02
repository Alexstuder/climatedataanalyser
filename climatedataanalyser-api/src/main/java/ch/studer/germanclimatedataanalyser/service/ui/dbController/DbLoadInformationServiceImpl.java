package ch.studer.germanclimatedataanalyser.service.ui.dbController;

import ch.studer.germanclimatedataanalyser.dao.DbLoadInformationeDAO;
import ch.studer.germanclimatedataanalyser.model.dto.db.DbLoadResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DbLoadInformationServiceImpl implements DbLoadInformationService {

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    DbLoadInformationeDAO dbLoadInformationeDAO;

    @Autowired
    DbStatusInformationServiceImpl dbStatusInformationService;

    private static final Logger log = LoggerFactory.getLogger(DbLoadInformationServiceImpl.class);


    @Override
    public DbLoadResponseDto getDbLoadInformation() {
        DbLoadResponseDto dbLoadResponseDto = new DbLoadResponseDto(dbLoadInformationeDAO.getDbLoadInformation(), dbStatusInformationService.getDbStatus());
        return dbLoadResponseDto;
    }

    @Override
    public boolean isDbLoaded() {
        return (dbLoadInformationeDAO.getMonthTableCount() > 0);
    }
}
