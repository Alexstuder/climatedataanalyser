package ch.studer.germanclimatedataanalyser.service.ui.dbController;

import ch.studer.germanclimatedataanalyser.common.BatchStepName;
import ch.studer.germanclimatedataanalyser.model.dto.db.DbLoadResponseDto;
import ch.studer.germanclimatedataanalyser.model.dto.db.DbLoadStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class DbStatusInformationServiceImpl implements DbStatusInformationService, InitializingBean {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DbLoadInformationService dbLoadInformationService;

    private DbStatusEnum dbStatus;

    private static final Logger log = LoggerFactory.getLogger(DbStatusInformationServiceImpl.class);

    public DbStatusInformationServiceImpl() {
    }

    @Override
    public DbStatusEnum getDbStatus() {
        return dbStatus;
    }

    @Override
    public void setDbStatus(DbStatusEnum dbStatus) {
        this.dbStatus = dbStatus;
    }

    public int getMonthTableCount() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM MONTH_;", Integer.class);

    }

    public int getClimateTableCount() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM CLIMATE;", Integer.class);
    }

    public int getStationTableCount() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM STATION;", Integer.class);
    }

    public int getWeatherTableCount() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM WEATHER;", Integer.class);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (isDbFullyLoaded()) {
            setDbStatus(DbStatusEnum.loaded);
        } else {
            setDbStatus(DbStatusEnum.empty);
        }
    }

    boolean isDbFullyLoaded() {
        boolean isFullyLoaded = false;

        int climateCount = 0;
        int stationCount = 0;
        int monthCount = 0;
        int weatherCount = 0;

        DbLoadResponseDto dbLoadResponseDto = dbLoadInformationService.getDbLoadInformation();

        for (DbLoadStep dbLoadStep : dbLoadResponseDto.getDbLoadSteps()) {

            BatchStepName nextStepName = BatchStepName.valueOf(dbLoadStep.getStepName());
            switch (nextStepName) {
                case import_climate_records:
                    climateCount = Integer.valueOf(dbLoadStep.getWriteCount());
                    break;
                case import_station_records:
                    stationCount = Integer.valueOf(dbLoadStep.getWriteCount());
                    break;
                case import_temperature_records:
                    monthCount = Integer.valueOf(dbLoadStep.getWriteCount());
                    break;
                case import_weather_records:
                    weatherCount = Integer.valueOf(dbLoadStep.getWriteCount());
                    break;
                default:
                    break;
            }
        }


        // When all Tabels still got the las load amount of Records ,the DB is fully loaded
        if (climateCount == getClimateTableCount()
                && stationCount == getStationTableCount()
                && monthCount == getMonthTableCount()
                && weatherCount == getWeatherTableCount()) {
            isFullyLoaded = true;
        }

        return isFullyLoaded;
    }
}

