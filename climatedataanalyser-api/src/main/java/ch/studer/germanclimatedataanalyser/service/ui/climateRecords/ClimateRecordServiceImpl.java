package ch.studer.germanclimatedataanalyser.service.ui.climateRecords;

import ch.studer.germanclimatedataanalyser.dao.StationClimateDAO;
import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.database.TemperatureForMonths;
import ch.studer.germanclimatedataanalyser.model.dto.climaterecords.ClimateRecord;
import ch.studer.germanclimatedataanalyser.model.dto.climaterecords.ClimateRecordsDto;
import ch.studer.germanclimatedataanalyser.model.dto.helper.Bundesland;
import ch.studer.germanclimatedataanalyser.model.dto.helper.GpsPoint;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
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
            , String year
            , String distanceYear) {


        // Proof input :cause all @GetMapping parameters are String
        climateRecordsDto.setErrorMsg(proofInput(bundesland, gps1Lat, gps1Long, gps2Lat, gps2Long, year, distanceYear));

        //proceed only if errorMsg is empty
        if (climateRecordsDto.getErrorMsg().isEmpty()) {

            //Persist the InputParameters
            this.bundesland.setName(bundesland);
            this.Gps1 = new GpsPoint(Double.parseDouble(gps1Lat), Double.parseDouble(gps1Long));
            this.Gps2 = new GpsPoint(Double.parseDouble(gps2Lat), Double.parseDouble(gps2Long));
            this.year = year.trim();
            this.distanceYear = Integer.parseInt(distanceYear.trim());

            // ****************************************************************
            // get all Bundesland ClimateRecords from year
            // ****************************************************************
            List<StationClimate> allStationClimates;

            if (this.bundesland.getName().isEmpty()) {
                allStationClimates = stationClimateDAO.getClimateForGpsCoordinatesFromYearOrderByYearAndStationId(Gps1, Gps2, year);
            } else {
                allStationClimates = stationClimateDAO.getClimateForBundeslandFromYearOrderByYearAndStationId(bundesland, year);
            }

            // ***************************************************************************
            // Remove all years between year + distanceYear
            // ***************************************************************************
            List<StationClimate> relevantYearsStationClimates;

            if (allStationClimates.isEmpty()) {
                climateRecordsDto.setErrorMsg("There are no climateRecords for your search criteria !");
            } else {

                relevantYearsStationClimates = getStationClimatesFromYearWithDistance(this.getDistanceYear(), allStationClimates);

                // ***************************************************************************
                // Agregate alle Stations in StationClimates to 1 ClimateRecord / year
                // ***************************************************************************
                List<ClimateRecord> averagedClimateRecords = getAverageClimatePerYear(relevantYearsStationClimates);

                // ***************************************************
                // Calculate the difference between each ClimateRecord
                // ***************************************************
                if (averagedClimateRecords.size() != 1) {
                    climateRecordsDto.setClimateRecordList(getDifferences(averagedClimateRecords));
                } else {
                    climateRecordsDto.setClimateRecordList(averagedClimateRecords);
                }
            }

        }

        return climateRecordsDto;
    }

    private List<ClimateRecord> getAverageClimatePerYear(List<StationClimate> relevantYearsStationClimates) {
        List<ClimateRecord> climateRecords = new ArrayList<>();
        List<TemperatureForMonths> collectTemperatureForMonth = new ArrayList<>();
        ClimateRecord climateRecord;

        String startPeriod;
        String endPeriod;


        //Group by
        Map<String, List<StationClimate>> stationClimatesGroupeByStartPeriod = relevantYearsStationClimates.stream().collect(Collectors.groupingBy(StationClimate::getStartPeriod));
        //get in right order
        Map<String, List<StationClimate>> stationClimatesGroupedAndOrdered = new LinkedHashMap<>();
        stationClimatesGroupeByStartPeriod.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEachOrdered(x -> stationClimatesGroupedAndOrdered.put(x.getKey(), x.getValue()));

        for (String key : stationClimatesGroupedAndOrdered.keySet()) {
            for (StationClimate stationClimates : stationClimatesGroupedAndOrdered.get(key)) {
                collectTemperatureForMonth.add(stationClimates.getTemperatureForMonths());
            }


            //Build the climateRecord
            climateRecord = new ClimateRecord();
            // Get start and end Period to build the header of the ClimatRecord
            startPeriod = stationClimatesGroupeByStartPeriod.get(key).get(0).getStartPeriod();
            endPeriod = stationClimatesGroupeByStartPeriod.get(key).get(0).getEndPeriod();
            climateRecord.setHeaderYearToYear(startPeriod, endPeriod);

            // Get the average temperature
            climateRecord.mapAndSetFrom(TemperatureForMonths.getAverage(collectTemperatureForMonth));

            climateRecords.add(climateRecord);

            //get a fresh TemperatreForMonth List
            collectTemperatureForMonth = new ArrayList<>();

        }

        return climateRecords;
    }


    private String proofInput(String bundesland, String gps1Lat, String gps1Long, String gps2Lat, String
            gps2Long, String yearFrom, String distanceYear) {
        String errorMsg;
        final String NOT_VALID_COORDINATE_FORMAT = " has not a valid coordinate format !";
        final String NOT_VALID_LAT_COORDINATE = " is not a valid latitude !(min : -90 / max : 90)";
        final String NOT_VALID_LONG_COORDINATE = " is not a valid longitude !(min : -180 / max : 180)";

        if (!bundesland.isBlank()) {

            //proof Bundesland
            bundeslandProofer.setName(bundesland);
            errorMsg = bundeslandProofer.proof();

            if (!errorMsg.equals("")) {
                return errorMsg;
            }
        } else {


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
     * Get the climateRecords remove all records between startPeriod and EndPeriod so you get the min. distance of @Input distanceYear
     * <p>
     * it is expected that the @Input stationClimates starts from the index(0) and is sorted in ascending order
     */
    private List<StationClimate> getStationClimatesFromYearWithDistance(int distanceYear, List<StationClimate> stationClimatesIn) {

        List<StationClimate> stationClimatesRe;

        int currentYear = Integer.parseInt(stationClimatesIn.get(0).getStartPeriod());
        int finishYear = Integer.parseInt(stationClimatesIn.get(stationClimatesIn.size() - 1).getStartPeriod());
        List<String> relevantYears = getRelevantYears(currentYear, distanceYear, finishYear);

        stationClimatesRe = stationClimatesIn.stream()
                .filter(stationClimate -> String.valueOf(relevantYears).contains(stationClimate.getStartPeriod()))
                .collect(Collectors.toList());


        return stationClimatesRe;
    }


    private List<String> getRelevantYears(int startYear, int distanceYear, int finishYear) {
        List<String> relevantYears = new ArrayList<>();

        int currentYear = startYear;

        // Add first relevant Year
        relevantYears.add(String.valueOf(currentYear));
        while (currentYear + distanceYear < finishYear) {

            currentYear = currentYear + distanceYear;
            relevantYears.add(String.valueOf(currentYear));
        }

        if (currentYear != finishYear) {
            relevantYears.add(String.valueOf(finishYear));
        }

        return relevantYears;
    }


    private List<ClimateRecord> getDifferences(List<ClimateRecord> climateRecords) {

        List<ClimateRecord> calculatedClimateRecords = new ArrayList<>();
        int currentIndex = 0;
        for (int i = 0; i < climateRecords.size() - 1; i++) {
            //persist the record
            calculatedClimateRecords.add(climateRecords.get(i));
            calculatedClimateRecords.add(getDiff(climateRecords.get(i), climateRecords.get(i + 1)));

        }

        // Add Last Record without Diff Calculation
        calculatedClimateRecords.add(climateRecords.get(climateRecords.size() - 1));

        return calculatedClimateRecords;
    }


    private ClimateRecord getDiff(ClimateRecord first, ClimateRecord second) {
        ClimateRecord returnClimateRecord = new ClimateRecord();
        returnClimateRecord.setHeaderAsDifference();

        returnClimateRecord.setJanuar(second.getJanuar().subtract(first.getJanuar()));
        returnClimateRecord.setFebruar(second.getFebruar().subtract(first.getFebruar()));
        returnClimateRecord.setMaerz(second.getMaerz().subtract(first.getMaerz()));
        returnClimateRecord.setApril(second.getApril().subtract(first.getApril()));
        returnClimateRecord.setMai(second.getMai().subtract(first.getMai()));
        returnClimateRecord.setJuni(second.getJuni().subtract(first.getJuni()));
        returnClimateRecord.setJuli(second.getJuli().subtract(first.getJuli()));
        returnClimateRecord.setAugust(second.getAugust().subtract(first.getAugust()));
        returnClimateRecord.setSeptember(second.getSeptember().subtract(first.getSeptember()));
        returnClimateRecord.setOktober(second.getOktober().subtract(first.getOktober()));
        returnClimateRecord.setNovember(second.getNovember().subtract(first.getNovember()));
        returnClimateRecord.setDezember(second.getDezember().subtract(first.getDezember()));


        return returnClimateRecord;
    }


    /*
    Hier werden die einzelnen KlimaRecords /station aggregiert im Bezug auf das Jahr
    Es wird erwartet , dass die eingegebenen StationClimate aufsteigend nach der StartPeriode sortiert sind .

     */

    private List<ClimateRecord> getClimateRecordsDto(List<StationClimate> stationClimates) {
        List<ClimateRecord> climateRecords = new ArrayList<>();

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
