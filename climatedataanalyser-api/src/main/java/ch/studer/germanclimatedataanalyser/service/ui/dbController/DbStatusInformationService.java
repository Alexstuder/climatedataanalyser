package ch.studer.germanclimatedataanalyser.service.ui.dbController;

public interface DbStatusInformationService {
    DbStatusEnum getDbStatus();

    void setDbStatus(DbStatusEnum dbStatus);

}
