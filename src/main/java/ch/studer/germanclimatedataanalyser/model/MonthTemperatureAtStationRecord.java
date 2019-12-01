package ch.studer.germanclimatedataanalyser.model;

import java.math.BigDecimal;

public class MonthTemperatureAtStationRecord {

    private String stationId;
    private String messDatumBeginn;
    private String messDatumEnd;
    private BigDecimal temperatur ;

    public MonthTemperatureAtStationRecord(String stationId, String messDatumBeginn, String messDatumEnd, BigDecimal temperatur) {
        this.stationId = stationId;
        this.messDatumBeginn = messDatumBeginn;
        this.messDatumEnd = messDatumEnd;
        this.temperatur = temperatur;
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

    public BigDecimal getTemperatur() {
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

    public void setTemperatur(BigDecimal temperatur) {
        this.temperatur = temperatur;
    }


}
