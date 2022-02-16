-- ##########################
-- Create Tables
-- ##########################
CREATE TABLE IF NOT EXISTS `MONTH`
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

CREATE TABLE IF NOT EXISTS `Station`
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

CREATE TABLE IF NOT EXISTS `WEATHER`
(
    WEATHER_ID              BIGINT auto_increment NOT NULL PRIMARY KEY,
    STATION_ID              int                   NOT NULL,
    YEAR                    VARCHAR(4) COMMENT 'Weather Temperature for the year : yyyy',
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

CREATE TABLE IF NOT EXISTS `CLIMATE`
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
