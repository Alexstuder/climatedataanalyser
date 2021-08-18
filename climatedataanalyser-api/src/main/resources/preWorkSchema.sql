-- THIS SCRIPT DROP&CREATES USERS and DATABASES

DROP user if exists climateRUN@localhost;
DROP user if exists climateDBA@localhost;
flush privileges;

DROP database if exists climate;
commit;
-- Create the Database
CREATE database if not exists climate;

-- Create the Run user
-- this is the User used in the application
CREATE USER 'climateRUN'@'localhost' IDENTIFIED BY 'sk93?69cj3kc0a2v'
    WITH MAX_QUERIES_PER_HOUR 0
        MAX_UPDATES_PER_HOUR 0
        MAX_CONNECTIONS_PER_HOUR 0
        MAX_USER_CONNECTIONS 0;
-- GRANT CREATE,INSERT,SELECT,DELETE,UPDATE,DROP ON climate.* TO 'climateRUN'@'localhost';
 GRANT ALL privileges ON climate.* TO 'climateRUN'@'localhost';

-- This User is used to monitor the DB actions
CREATE USER 'climateDBA'@'localhost' IDENTIFIED BY 'al349vbn*mvc(.cu"+u'
   WITH MAX_QUERIES_PER_HOUR 0
        MAX_UPDATES_PER_HOUR 0
        MAX_CONNECTIONS_PER_HOUR 0
        MAX_USER_CONNECTIONS 0;

GRANT ALL PRIVILEGES ON climate.* TO 'climateDBA'@'localhost';

-- ##########################
-- Drop Tables
-- ##########################
DROP  TABLE if exists month ;
DROP  TABLE if exists station ;
DROP  TABLE if exists weather ;
DROP  TABLE if exists climate ;


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


-- CREATE Spring Batch Tabels

DROP TABLE IF EXISTS BATCH_STEP_EXECUTION_CONTEXT ;
DROP TABLE IF EXISTS BATCH_JOB_EXECUTION_CONTEXT ;
DROP TABLE IF EXISTS BATCH_STEP_EXECUTION ;
DROP TABLE IF EXISTS BATCH_JOB_EXECUTION_PARAMS ;
DROP TABLE IF EXISTS BATCH_JOB_EXECUTION ;
DROP TABLE IF EXISTS BATCH_JOB_INSTANCE ;

DROP TABLE IF EXISTS BATCH_STEP_EXECUTION_SEQ ;
DROP TABLE IF EXISTS BATCH_JOB_EXECUTION_SEQ ;
DROP TABLE IF EXISTS BATCH_JOB_SEQ ;

-- Autogenerated: do not edit this file

CREATE TABLE BATCH_JOB_INSTANCE  (
                                     JOB_INSTANCE_ID BIGINT  NOT NULL PRIMARY KEY ,
                                     VERSION BIGINT ,
                                     JOB_NAME VARCHAR(100) NOT NULL,
                                     JOB_KEY VARCHAR(32) NOT NULL,
                                     constraint JOB_INST_UN unique (JOB_NAME, JOB_KEY)
) ENGINE=InnoDB;

CREATE TABLE BATCH_JOB_EXECUTION  (
                                      JOB_EXECUTION_ID BIGINT  NOT NULL PRIMARY KEY ,
                                      VERSION BIGINT  ,
                                      JOB_INSTANCE_ID BIGINT NOT NULL,
                                      CREATE_TIME DATETIME NOT NULL,
                                      START_TIME DATETIME DEFAULT NULL ,
                                      END_TIME DATETIME DEFAULT NULL ,
                                      STATUS VARCHAR(10) ,
                                      EXIT_CODE VARCHAR(2500) ,
                                      EXIT_MESSAGE VARCHAR(2500) ,
                                      LAST_UPDATED DATETIME,
                                      JOB_CONFIGURATION_LOCATION VARCHAR(2500) NULL,
                                      constraint JOB_INST_EXEC_FK foreign key (JOB_INSTANCE_ID)
                                          references BATCH_JOB_INSTANCE(JOB_INSTANCE_ID)
) ENGINE=InnoDB;

CREATE TABLE BATCH_JOB_EXECUTION_PARAMS  (
                                             JOB_EXECUTION_ID BIGINT NOT NULL ,
                                             TYPE_CD VARCHAR(6) NOT NULL ,
                                             KEY_NAME VARCHAR(100) NOT NULL ,
                                             STRING_VAL VARCHAR(250) ,
                                             DATE_VAL DATETIME DEFAULT NULL ,
                                             LONG_VAL BIGINT ,
                                             DOUBLE_VAL DOUBLE PRECISION ,
                                             IDENTIFYING CHAR(1) NOT NULL ,
                                             constraint JOB_EXEC_PARAMS_FK foreign key (JOB_EXECUTION_ID)
                                                 references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ENGINE=InnoDB;

CREATE TABLE BATCH_STEP_EXECUTION  (
                                       STEP_EXECUTION_ID BIGINT  NOT NULL PRIMARY KEY ,
                                       VERSION BIGINT NOT NULL,
                                       STEP_NAME VARCHAR(100) NOT NULL,
                                       JOB_EXECUTION_ID BIGINT NOT NULL,
                                       START_TIME DATETIME NOT NULL ,
                                       END_TIME DATETIME DEFAULT NULL ,
                                       STATUS VARCHAR(10) ,
                                       COMMIT_COUNT BIGINT ,
                                       READ_COUNT BIGINT ,
                                       FILTER_COUNT BIGINT ,
                                       WRITE_COUNT BIGINT ,
                                       READ_SKIP_COUNT BIGINT ,
                                       WRITE_SKIP_COUNT BIGINT ,
                                       PROCESS_SKIP_COUNT BIGINT ,
                                       ROLLBACK_COUNT BIGINT ,
                                       EXIT_CODE VARCHAR(2500) ,
                                       EXIT_MESSAGE VARCHAR(2500) ,
                                       LAST_UPDATED DATETIME,
                                       constraint JOB_EXEC_STEP_FK foreign key (JOB_EXECUTION_ID)
                                           references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ENGINE=InnoDB;

CREATE TABLE BATCH_STEP_EXECUTION_CONTEXT  (
                                               STEP_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
                                               SHORT_CONTEXT VARCHAR(2500) NOT NULL,
                                               SERIALIZED_CONTEXT TEXT ,
                                               constraint STEP_EXEC_CTX_FK foreign key (STEP_EXECUTION_ID)
                                                   references BATCH_STEP_EXECUTION(STEP_EXECUTION_ID)
) ENGINE=InnoDB;

CREATE TABLE BATCH_JOB_EXECUTION_CONTEXT  (
                                              JOB_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
                                              SHORT_CONTEXT VARCHAR(2500) NOT NULL,
                                              SERIALIZED_CONTEXT TEXT ,
                                              constraint JOB_EXEC_CTX_FK foreign key (JOB_EXECUTION_ID)
                                                  references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ENGINE=InnoDB;

CREATE TABLE BATCH_STEP_EXECUTION_SEQ (
                                          ID BIGINT NOT NULL,
                                          UNIQUE_KEY CHAR(1) NOT NULL,
                                          constraint UNIQUE_KEY_UN unique (UNIQUE_KEY)
) ENGINE=InnoDB;

INSERT INTO BATCH_STEP_EXECUTION_SEQ (ID, UNIQUE_KEY) select * from (select 0 as ID, '0' as UNIQUE_KEY) as tmp where not exists(select * from BATCH_STEP_EXECUTION_SEQ);

CREATE TABLE BATCH_JOB_EXECUTION_SEQ (
                                         ID BIGINT NOT NULL,
                                         UNIQUE_KEY CHAR(1) NOT NULL,
                                         constraint UNIQUE_KEY_UN unique (UNIQUE_KEY)
) ENGINE=InnoDB;

INSERT INTO BATCH_JOB_EXECUTION_SEQ (ID, UNIQUE_KEY) select * from (select 0 as ID, '0' as UNIQUE_KEY) as tmp where not exists(select * from BATCH_JOB_EXECUTION_SEQ);

CREATE TABLE BATCH_JOB_SEQ (
                               ID BIGINT NOT NULL,
                               UNIQUE_KEY CHAR(1) NOT NULL,
                               constraint UNIQUE_KEY_UN unique (UNIQUE_KEY)
) ENGINE=InnoDB;

INSERT INTO BATCH_JOB_SEQ (ID, UNIQUE_KEY) select * from (select 0 as ID, '0' as UNIQUE_KEY) as tmp where not exists(select * from BATCH_JOB_SEQ);



