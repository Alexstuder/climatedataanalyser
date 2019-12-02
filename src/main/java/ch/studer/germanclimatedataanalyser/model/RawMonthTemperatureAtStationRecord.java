package ch.studer.germanclimatedataanalyser.model;

import java.math.BigDecimal;

public class RawMonthTemperatureAtStationRecord {

    private String stationId;
    private String messDatumBeginn;
    private String messDatumEnd;
    private String temperatur ;

    public RawMonthTemperatureAtStationRecord(){
    }

    public String getStationId() {
        return stationId;
    }

    public String getMessDatumBeginn() {
        return messDatumBeginn;
    }

    public String getMessDatumEnd() {
        return messDatumEnd;
    }

    public String getTemperatur() {
        return temperatur;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public void setMessDatumBeginn(String messDatumBeginn) {
        this.messDatumBeginn = messDatumBeginn;
    }

    public void setMessDatumEnd(String messDatumEnd) {
        this.messDatumEnd = messDatumEnd;
    }

    public void setTemperatur(String temperatur) {
        this.temperatur = temperatur;
    }


}
