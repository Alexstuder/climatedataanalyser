package ch.studer.germanclimatedataanalyser.service.ui.climateRecords;

import ch.studer.germanclimatedataanalyser.dao.StationClimateDAO;
import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.dto.climaterecords.ClimateRecord;
import ch.studer.germanclimatedataanalyser.model.dto.climaterecords.ClimateRecordsDto;
import ch.studer.germanclimatedataanalyser.model.dto.helper.Bundesland;
import ch.studer.germanclimatedataanalyser.model.dto.helper.GpsPoint;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClimateRecordServiceImpl implements ClimateRecordService {

    @Autowired
    StationClimateDAO stationClimateDAO;

    @Autowired
    Bundesland bundeslandProofer;

    //InputParameter
    private Bundesland bundesland;
    private GpsPoint Gps1;
    private GpsPoint Gps2;
    private String year;
    private int distanceYear;

    public ClimateRecordServiceImpl() {
        this.bundesland = new Bundesland();
        this.year = "";
        this.distanceYear = 0;
    }

    // Return
    private ClimateRecordsDto climateRecordsDto = new ClimateRecordsDto();


    private Collection<? extends ClimateRecord> getDifferences(List<ClimateRecord> climateRecords) {

        List<ClimateRecord> calculatedClimateRecords = new ArrayList<ClimateRecord>();
        int currentIndex = 0;
        for (int i = 0; i < climateRecords.size(); ) {
            //persist the record
            calculatedClimateRecords.add(climateRecords.get(i));
            calculatedClimateRecords.add(getDiff(climateRecords.get(i), climateRecords.get(i++)));


        }


        return calculatedClimateRecords;
    }

    public ClimateRecordsDto getClimateRecords(String bundesland
            , String gps1Lat
            , String gps1Long
            , String gps2Lat
            , String gps2Long
            , String yearFrom
            , String distanceYear) {


        // Proof input :cause all @GetMapping parameters are String
        climateRecordsDto.setErrorMsg(proofInput(bundesland, gps1Lat, gps1Long, gps2Lat, gps2Long, yearFrom, distanceYear));

        //proceed only if errorMsg is empty
        if (climateRecordsDto.getErrorMsg().isEmpty()) {

            //Persist the InputParameters
            this.bundesland.setName(bundesland);
            this.Gps1 = new GpsPoint(Double.parseDouble(gps1Lat), Double.parseDouble(gps1Long));
            this.Gps2 = new GpsPoint(Double.parseDouble(gps2Lat), Double.parseDouble(gps2Long));
            this.year = yearFrom;
            this.distanceYear = Integer.parseInt(distanceYear);

            // ****************************************************************
            // get all Bundesland ClimateRecords from yearFrom
            // ****************************************************************
            List<StationClimate> stationClimates = new ArrayList<StationClimate>();
            if (this.bundesland.getName().isEmpty()) {
                stationClimates = getStationClimatesFromYearWithDistance(yearFrom, distanceYear, stationClimateDAO.getClimateForGpsCoordinates(Gps1, Gps2));
            } else {
                stationClimates = getStationClimatesFromYearWithDistance(yearFrom, distanceYear, stationClimateDAO.getClimateForBundesland(bundesland));
            }

            // ***************************************************************************
            // Agregate alle Stations in StationClimates to 1 ClimateRecord / year
            // ***************************************************************************

            // ***************************************************
            // Calculate the difference between each ClimateRecord
            // ***************************************************

        }

        return climateRecordsDto;
    }

    /*
    Hier werden die einzelnen KlimaRecords /station aggregiert im Bezug auf das Jahr
    Es wird erwartet , dass die eingegebenen StationClimate aufsteigend nach der StartPeriode sortiert sind .

     */
    private List<StationClimate> getStationClimatesFromYearWithDistance(String yearFrom, String distanceYear, List<StationClimate> stationClimatesIn) {

        List<StationClimate> stationClimatesRe = new ArrayList<StationClimate>();

        int currentYear = Integer.valueOf(year);
        int next = 0;
        int currentIndex = 0;


        // get the right start year
        if (currentYear < Integer.valueOf(stationClimatesIn.get(0).getStartPeriod())) {
            currentYear = Integer.valueOf(stationClimatesIn.get(0).getStartPeriod());
        } else {
            //Read to the first record
            while (currentYear > Integer.valueOf(stationClimatesIn.get(currentIndex).getStartPeriod())) {
                currentIndex++;
            }
        }

        while (currentIndex < stationClimatesIn.size()) {
            if (currentYear == Integer.parseInt(stationClimatesIn.get(currentIndex).getStartPeriod())) {
                stationClimatesRe.add(stationClimatesIn.get(currentIndex));
            } else {
                // Get the next current year , just add the input parameter distanceYear to the current year
                if (currentYear < Integer.parseInt(stationClimatesIn.get(currentIndex).getStartPeriod())) {
                    currentYear = currentYear + Integer.parseInt(distanceYear);
                }
            }
            currentIndex++;
        }

        return stationClimatesRe;
    }

    private ClimateRecord getDiff(ClimateRecord first, ClimateRecord second) {
        ClimateRecord returnClimateRecord = new ClimateRecord();
        returnClimateRecord.setHeaderAsDifference();
        ;
        returnClimateRecord.setJanuar(second.getJanuar().subtract(first.getJanuar()));


        return returnClimateRecord;
    }

    private List<ClimateRecord> getClimateRecordsDto(List<StationClimate> stationClimates) {
        List<ClimateRecord> climateRecords = new ArrayList<ClimateRecord>();

        String currentIndex = year;
        for (StationClimate stationClimate : stationClimates) {

            if (stationClimate.getStartPeriod().contains(currentIndex)) {

                //Get a new ClimateReords
                ClimateRecord climateRecord = new ClimateRecord();
                climateRecord.setHeaderYearToYear(stationClimate.getStartPeriod(), stationClimate.getEndPeriod());

                climateRecord.mapFrom(stationClimate.getTemperatureForMonths());
                climateRecords.add(climateRecord);
            }
        }
        return climateRecords;
    }

    private String proofInput(String bundesland, String gps1Lat, String gps1Long, String gps2Lat, String
            gps2Long, String yearFrom, String distanceYear) {
        String errorMsg;
        final String NOT_VALID_COORDINATE_FORMAT = " has not a valid coordinate format !";
        final String NOT_VALID_LAT_COORDINATE = " is not a valid latitude !(min : -90 / max : 90)";
        final String NOT_VALID_LONG_COORDINATE = " is not a valid longitude !(min : -180 / max : 180)";

        //proof Bundesland
        bundeslandProofer.setName(bundesland);
        errorMsg = bundeslandProofer.proof();

        if (!errorMsg.equals("")) {
            return errorMsg;
        }
        //proof GPS1
        try {
            this.Gps1 = new GpsPoint(Double.parseDouble(gps1Lat), Double.parseDouble(gps1Long));
        } catch (NumberFormatException e) {
            return gps1Lat + " or " + gps1Long + NOT_VALID_COORDINATE_FORMAT;
        }
        //Proof GPS2
        try {

            this.Gps2 = new GpsPoint(Double.parseDouble(gps2Lat), Double.parseDouble(gps2Long));
        } catch (NumberFormatException e) {
            return gps2Lat + " or " + gps2Long + NOT_VALID_COORDINATE_FORMAT;
        }
        //proof if GPS have valid coordinates
        if (!this.Gps1.isLatitudeValid()) {
            return this.Gps1.getLatitude() + NOT_VALID_LAT_COORDINATE;
        }
        if (!this.Gps1.isLongitudeValid()) {
            return this.Gps1.getLongitude() + NOT_VALID_LONG_COORDINATE;
        }
        if (!this.Gps2.isLatitudeValid()) {
            return this.Gps2.getLatitude() + NOT_VALID_LAT_COORDINATE;
        }
        if (!this.Gps2.isLongitudeValid()) {
            return this.Gps2.getLongitude() + NOT_VALID_LONG_COORDINATE;
        }


        //Proof YearTo
        if (yearFrom.strip().length() == 4) {
            this.year = yearFrom.strip();
            try {
                Integer.parseInt(yearFrom.strip());
            } catch (NumberFormatException e) {

                return yearFrom + " is not numerically!";
            }
        } else return yearFrom + " has not a valid year format !";


        //Proof distanceYear
        try {
            this.distanceYear = Integer.parseInt(distanceYear.strip());
        } catch (NumberFormatException e) {
            return distanceYear + " is not a valid number !";
        }

        return "";
    }

    // **********************  Getter and Setter   ***************************


}
