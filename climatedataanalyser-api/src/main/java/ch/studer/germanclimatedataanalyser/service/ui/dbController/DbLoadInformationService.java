package ch.studer.germanclimatedataanalyser.service.ui.dbController;

import ch.studer.germanclimatedataanalyser.model.dto.db.DbLoadResponseDto;

public interface DbLoadInformationService {

    DbLoadResponseDto getDbLoadInformation();

    boolean isDbLoaded();


}
