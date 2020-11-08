package ch.studer.germanclimatedataanalyser.service.ui.climateRecords;

import ch.studer.germanclimatedataanalyser.model.dto.climaterecords.ClimateRecordsDto;
import ch.studer.germanclimatedataanalyser.model.dto.helper.Bundesland;
import ch.studer.germanclimatedataanalyser.model.dto.helper.GpsPoint;
import org.springframework.beans.factory.annotation.Autowired;

public class ClimateRecordServiceImpl implements ClimateRecordService {


    @Autowired
    Bundesland bundeslandProofer;

    //InputParameter
    private Bundesland bundesland;
    private GpsPoint Gps1;
    private GpsPoint Gps2;
    private String year;
    private int distanceYear;


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
        // get all ClimateRecords from yearFrom with distance to
        if(climateRecordsDto.getErrorMsg()!=""){

        }

        // Calculate the difference between each ClimateRecord
        if(climateRecordsDto.getErrorMsg()!=""){

        }

        return climateRecordsDto;
    }

    private String proofInput(String bundesland, String gps1Lat, String gps1Long, String gps2Lat, String gps2Long, String yearFrom, String distanceYear) {
        String errorMsg = "";
        final String NOT_VALID_COORDINATE_FORMAT = " has not a valid coordinate format !";
        final String NOT_VALID_LAT_COORDINATE = " is not a valid latitude !(min : -90 / max : 90)";
        final String NOT_VALID_LONG_COORDINATE = " is not a valid longitude !(min : -180 / max : 180)";

        //proof Bundesland
        bundeslandProofer.setName(bundesland);
        errorMsg = bundeslandProofer.proof();

        if (errorMsg != "") {
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
        if(yearFrom.strip().length() == 4){
            this.year = yearFrom.strip();
            try{
                Integer.parseInt(yearFrom.strip());
            } catch (NumberFormatException e){

                return yearFrom + " is not numerically!";
            }
        } else return yearFrom + " has not a valid year format !";



        //Proof distanceYear
        try{
            this.distanceYear = Integer.parseInt(distanceYear.strip());
        } catch(NumberFormatException e){
           return distanceYear + " is not a valid number !";
        }

        return "";
    }

    // **********************  Getter and Setter


}
