package ch.studer.germanclimatedataanalyser.model.dto.climaterecords;

import java.util.ArrayList;
import java.util.List;

public class ClimateRecordsDto {

    private List<ClimateRecord> climateRecordList;

    private String errorMsg;

    public ClimateRecordsDto(){
        climateRecordList= new ArrayList<ClimateRecord>();
        errorMsg="";
    }


    //************************* Getter and Setter ****************
    public List<ClimateRecord> getClimateRecordList() {
        return climateRecordList;
    }

    public void setClimateRecordList(List<ClimateRecord> climateRecordList) {
        this.climateRecordList = climateRecordList;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
