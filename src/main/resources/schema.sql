-- ## Pre-Work

-- User:climateDataUser PWD:climateDataUser
-- Copy following sql and drop into MySQL
-- The User has no Limitation at all !!
-- and is granted with all privileges

-- CREATE USER 'climateUser'@'localhost' IDENTIFIED BY 'climateUser'
--     WITH MAX_QUERIES_PER_HOUR 0
--              MAX_UPDATES_PER_HOUR 0
--              MAX_CONNECTIONS_PER_HOUR 0
--              MAX_USER_CONNECTIONS 0;
-- GRANT ALL PRIVILEGES ON climate.* TO 'climateUser'@'localhost';
-- create database if not exists climate;

-- ##########################
-- Drop Tables
-- ##########################
DROP  TABLE if exists month ;
DROP  TABLE if exists station ;


-- ##########################
-- Create Tables
-- ##########################
CREATE TABLE `month` (
                         MONTH_ID BIGINT auto_increment NOT NULL PRIMARY KEY ,
                         STATIONS_ID int NOT NULL,
                         MESS_DATUM_BEGINN DATE NOT NULL,
                         MESS_DATUM_ENDE DATE NOT NULL,
                         QN_4 int DEFAULT NULL,
                         MO_N double DEFAULT NULL,
                         MO_TT double DEFAULT NULL,
                         MO_TX double DEFAULT NULL,
                         MO_TN double DEFAULT NULL,
                         MO_FK double DEFAULT NULL,
                         MX_TX double DEFAULT NULL,
                         MX_FX double DEFAULT NULL,
                         MX_TN double DEFAULT NULL,
                         MO_SD_S double DEFAULT NULL,
                         QN_6 int DEFAULT NULL,
                         MO_RR double DEFAULT NULL,
                         MX_RS double DEFAULT NULL
);

CREATE TABLE `station` (
                        ID BIGINT auto_increment NOT NULL PRIMARY KEY ,
                        STATION_ID int NOT NULL,
                        DATE_BEGIN DATE NOT NULL,
                        DATE_END DATE NOT NULL,
                        STATION_HIGH DECIMAL(9,3) NOT NULL,
                        GEO_LATITUDE DECIMAL(7,4) NOT NULL,
                        GEO_LENGTH DECIMAL(7,4) NOT NULL,
                        STATION_NAME VARCHAR(100) NOT NULL,
                        BUNDES_LAND VARCHAR(100) NOT NULL

);
