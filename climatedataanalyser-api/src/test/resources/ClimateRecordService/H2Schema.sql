-- ## Pre-Work

--
-- DROP user if exists climateRUN@localhost;
-- DROP user if exists climateDBA@localhost;
-- flush privileges;
--
-- DROP database if exists climate;
-- commit;
-- -- Create the Database
-- CREATE database if not exists climate;
--
-- -- Create the Run user
-- -- this is the User used in the application
-- CREATE USER 'climateRUN'@'localhost' IDENTIFIED BY 'climateRUN'
--     WITH MAX_QUERIES_PER_HOUR 0
--         MAX_UPDATES_PER_HOUR 0
--         MAX_CONNECTIONS_PER_HOUR 0
--         MAX_USER_CONNECTIONS 0;
-- -- GRANT CREATE,INSERT,SELECT,DELETE,UPDATE,DROP ON climate.* TO 'climateRUN'@'localhost';
--  GRANT ALL privileges ON climate.* TO 'climateRUN'@'localhost';
--
-- -- This User is used to monitor the DB actions
-- CREATE USER 'climateDBA'@'localhost' IDENTIFIED BY 'climateDBA'
--    WITH MAX_QUERIES_PER_HOUR 0
--         MAX_UPDATES_PER_HOUR 0
--         MAX_CONNECTIONS_PER_HOUR 0
--         MAX_USER_CONNECTIONS 0;
--
-- GRANT ALL PRIVILEGES ON climate.* TO 'climateDBA'@'localhost';
-- create database if not exists climate;
--
-- -- ##########################
-- -- Drop Tables
-- -- ##########################
DROP TABLE if exists month_;
DROP TABLE if exists station;
DROP TABLE if exists weather;
DROP TABLE if exists climate;



-- ##########################
-- Create Tables
-- ##########################
CREATE TABLE IF NOT EXISTS `month`
(
    MONTH_ID          BIGINT auto_increment NOT NULL PRIMARY KEY,
    STATIONS_ID       int                   NOT NULL,
    MESS_DATUM_BEGINN DATE                  NOT NULL,
    MESS_DATUM_ENDE   DATE                  NOT NULL,
    QN_4              int           DEFAULT NULL,
    MO_N              DECIMAL(7, 4) DEFAULT NULL,
    MO_TT             DECIMAL(7, 4) DEFAULT NULL,
    MO_TX             DECIMAL(7, 4) DEFAULT NULL,
    MO_TN             DECIMAL(7, 4) DEFAULT NULL,
    MO_FK             DECIMAL(7, 4) DEFAULT NULL,
    MX_TX             DECIMAL(7, 4) DEFAULT NULL,
    MX_FX             DECIMAL(7, 4) DEFAULT NULL,
    MX_TN             DECIMAL(7, 4) DEFAULT NULL,
    MO_SD_S           DECIMAL(7, 4) DEFAULT NULL,
    QN_6              int           DEFAULT NULL,
    MO_RR             DECIMAL(7, 4) DEFAULT NULL,
    MX_RS             DECIMAL(7, 4) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `station`
(
    ID           BIGINT auto_increment NOT NULL PRIMARY KEY,
    STATION_ID   int                   NOT NULL,
    DATE_BEGIN   DATE                  NOT NULL,
    DATE_END     DATE                  NOT NULL,
    STATION_HIGH DECIMAL(9, 3)         NOT NULL,
    GEO_LATITUDE DECIMAL(7, 4)         NOT NULL,
    GEO_LENGTH   DECIMAL(7, 4)         NOT NULL,
    STATION_NAME VARCHAR(100)          NOT NULL,
    BUNDES_LAND  VARCHAR(100)          NOT NULL

);

CREATE TABLE IF NOT EXISTS `weather`
(
    WEATHER_ID              BIGINT auto_increment NOT NULL PRIMARY KEY,
    STATION_ID              int                   NOT NULL,
    YEAR_                    VARCHAR(4) COMMENT 'Weather Temperature for the year : yyyy',
    CALCULATED_ARTIFICIALLY BOOLEAN               Not Null Comment 'Marks a record as calculated and not as delivered',
    JANUAR                  DECIMAL(7, 4)         NOT NULL,
    FEBRUAR                 DECIMAL(7, 4)         NOT NULL,
    MAERZ                   DECIMAL(7, 4)         NOT NULL,
    APRIL                   DECIMAL(7, 4)         NOT NULL,
    MAI                     DECIMAL(7, 4)         NOT NULL,
    JUNI                    DECIMAL(7, 4)         NOT NULL,
    JULI                    DECIMAL(7, 4)         NOT NULL,
    AUGUST                  DECIMAL(7, 4)         NOT NULL,
    SEPTEMBER               DECIMAL(7, 4)         NOT NULL,
    OKTOBER                 DECIMAL(7, 4)         NOT NULL,
    NOVEMBER                DECIMAL(7, 4)         NOT NULL,
    DEZEMBER                DECIMAL(7, 4)         NOT NULL

);

CREATE TABLE IF NOT EXISTS `climate`
(
    CLIMATE_ID   BIGINT auto_increment NOT NULL PRIMARY KEY,
    STATION_ID   int                   NOT NULL,
    END_PERIOD   VARCHAR(4) COMMENT 'Climate Data end Period : yyyy  --> 2018 : for a 30 Year Climate Period !',
    START_PERIOD VARCHAR(4) COMMENT 'Climate Data start Period : yyyy  --> 1987',
    JANUAR       DECIMAL(7, 4)         NOT NULL,
    FEBRUAR      DECIMAL(7, 4)         NOT NULL,
    MAERZ        DECIMAL(7, 4)         NOT NULL,
    APRIL        DECIMAL(7, 4)         NOT NULL,
    MAI          DECIMAL(7, 4)         NOT NULL,
    JUNI         DECIMAL(7, 4)         NOT NULL,
    JULI         DECIMAL(7, 4)         NOT NULL,
    AUGUST       DECIMAL(7, 4)         NOT NULL,
    SEPTEMBER    DECIMAL(7, 4)         NOT NULL,
    OKTOBER      DECIMAL(7, 4)         NOT NULL,
    NOVEMBER     DECIMAL(7, 4)         NOT NULL,
    DEZEMBER     DECIMAL(7, 4)         NOT NULL

);
