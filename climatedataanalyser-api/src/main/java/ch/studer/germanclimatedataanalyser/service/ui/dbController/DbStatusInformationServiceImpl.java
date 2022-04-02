package ch.studer.germanclimatedataanalyser.service.ui.dbController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class DbStatusInformationServiceImpl implements DbStatusInformationService, InitializingBean {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private DbStatusEnum dbStatus;

    private static final Logger log = LoggerFactory.getLogger(DbStatusInformationServiceImpl.class);


    /***
     * Getter and Setter
     * @return
     */
    @Override
    public DbStatusEnum getDbStatus() {
        return dbStatus;
    }

    @Override
    public void setDbStatus(DbStatusEnum dbStatus) {
        this.dbStatus = dbStatus;
    }


    public int getMonthTableCount() {

        Integer counter;
        counter = jdbcTemplate.queryForObject("SELECT count(*) FROM CLIMATE.MONTH_;", Integer.class);

        return counter;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (getMonthTableCount() > 0) {
            setDbStatus(DbStatusEnum.loaded);
        } else {
            setDbStatus(DbStatusEnum.empty);
        }

    }
}

