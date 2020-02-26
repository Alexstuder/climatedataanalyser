package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.model.dto.DbLoadResponseDto;

public interface DbLoadInformationService {

   public DbLoadResponseDto getDbLoadInformation();

   public boolean isDbLoaded();


}
