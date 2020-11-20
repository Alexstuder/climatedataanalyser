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
            this.year = yearFrom.trim();
            this.distanceYear = Integer.parseInt(distanceYear.trim());

            // ****************************************************************
            // get all Bundesland ClimateRecords from yearFrom
            // ****************************************************************
            List<StationClimate> allStationClimates = new ArrayList<StationClimate>();

            if (this.bundesland.getName().isEmpty()) {
                allStationClimates = stationClimateDAO.getClimateForGpsCoordinatesFromYearOrderByYearAndStationId(Gps1, Gps2, yearFrom);
            } else {
                allStationClimates = stationClimateDAO.getClimateForBundeslandFromYearOrderByYearAndStationId(bundesland, yearFrom);
            }

            // ***************************************************************************
            // Remove all years between yearFrom + distanceYear
            // ***************************************************************************
            List<StationClimate> relevantYearsStationClimates = new ArrayList<StationClimate>();

            if (allStationClimates.isEmpty()) {
                climateRecordsDto.setErrorMsg("There are no climateRecords for your search criteria !");
            } else {
                relevantYearsStationClimates = getStationClimatesFromYearWithDistance(this.getDistanceYear(), allStationClimates);
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
            this.distanceYear = Integer.parseInt(distanceYear.trim());
        } catch (NumberFormatException e) {
            return distanceYear + " is not a valid number !";
        }

        return "";
    }


    /**
     * Get the climateRecords remove alle records between so you get the min. distance of @Input distanceYear
     * <p>
     * it is expected that the @Input stationClimates starts from the index(0) and is sorted in ascending order
     */
    private List<StationClimate> getStationClimatesFromYearWithDistance(int distanceYear, List<StationClimate> stationClimatesIn) {

        List<StationClimate> stationClimatesRe = new ArrayList<StationClimate>();

        int currentYear = Integer.valueOf(stationClimatesIn.get(0).getStartPeriod());
        int finishYear = Integer.valueOf(stationClimatesIn.get(stationClimatesIn.size()).getStartPeriod());
        List<Integer> relevantYears = getRelevantYears(currentYear, distanceYear, finishYear);


        for (StationClimate stationClimate : stationClimatesIn) {


        }


        return stationClimatesRe;
    }

    private List<Integer> getRelevantYears(int startYear, int distanceYear, int finishYear) {
        List<Integer> relevantYears = new ArrayList<Integer>();

        int currentYear = startYear;

        // Add first relevant Year
        relevantYears.add(currentYear);
        while (currentYear + distanceYear < finishYear) {

            currentYear = currentYear + distanceYear;
            relevantYears.add(currentYear);
        }

        if (currentYear != finishYear) {
            relevantYears.add(finishYear);
        }

        return relevantYears;
    }


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


    private ClimateRecord getDiff(ClimateRecord first, ClimateRecord second) {
        ClimateRecord returnClimateRecord = new ClimateRecord();
        returnClimateRecord.setHeaderAsDifference();
        ;
        returnClimateRecord.setJanuar(second.getJanuar().subtract(first.getJanuar()));


        return returnClimateRecord;
    }


    /*
    Hier werden die einzelnen KlimaRecords /station aggregiert im Bezug auf das Jahr
    Es wird erwartet , dass die eingegebenen StationClimate aufsteigend nach der StartPeriode sortiert sind .

     */

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

    // **********************  Getter and Setter   ***************************


    public Bundesland getBundesland() {
        return bundesland;
    }

    public void setBundesland(Bundesland bundesland) {
        this.bundesland = bundesland;
    }

    public GpsPoint getGps1() {
        return Gps1;
    }

    public void setGps1(GpsPoint gps1) {
        Gps1 = gps1;
    }

    public GpsPoint getGps2() {
        return Gps2;
    }

    public void setGps2(GpsPoint gps2) {
        Gps2 = gps2;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getDistanceYear() {
        return distanceYear;
    }

    public void setDistanceYear(int distanceYear) {
        this.distanceYear = distanceYear;
    }

    public ClimateRecordsDto getClimateRecordsDto() {
        return climateRecordsDto;
    }

    public void setClimateRecordsDto(ClimateRecordsDto climateRecordsDto) {
        this.climateRecordsDto = climateRecordsDto;
    }
}
