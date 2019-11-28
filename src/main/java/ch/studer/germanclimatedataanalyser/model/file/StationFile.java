package ch.studer.germanclimatedataanalyser.model.file;

import java.io.Serializable;

public class StationFile implements Serializable {

    //private static final long serialVersionUID = -6402068923614583448L ;
  private String stationsId;
  private String dateBegin;
  private String dateEnd;
  private String stationHigh;
  private String geoLatitude;
  private String geoLength;
  private String stationName;
  private String bundesLand;

    public StationFile(String stationsId, String dateBegin, String dateEnd, String stationHigh, String geoLatitude, String geoLength, String stationName, String bundesLand) {
        this.stationsId = stationsId;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.stationHigh = stationHigh;
        this.geoLatitude = geoLatitude;
        this.geoLength = geoLength;
        this.stationName = stationName;
        this.bundesLand = bundesLand;
    }
    public StationFile(){
    }

    public String getStationsId() {
        return stationsId;
    }

    public void setStationsId(String stationsId) {
        this.stationsId = stationsId;
    }

    public String getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(String dateBegin) {
        this.dateBegin = dateBegin;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getStationHigh() {
        return stationHigh;
    }

    public void setStationHigh(String stationHigh) {
        this.stationHigh = stationHigh;
    }

    public String getGeoLatitude() {
        return geoLatitude;
    }

    public void setGeoLatitude(String geoLatitude) {
        this.geoLatitude = geoLatitude;
    }

    public String getGeoLength() {
        return geoLength;
    }

    public void setGeoLength(String geoLength) {
        this.geoLength = geoLength;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getBundesLand() {
        return bundesLand;
    }

    public void setBundesLand(String bundesLand) {
        this.bundesLand = bundesLand;
    }

    @Override
    public String toString() {
        return "StationFile{" +
                "stationsId='" + stationsId + '\'' +
                ", dateBeginn='" + dateBegin + '\'' +
                ", dateEnde='" + dateEnd + '\'' +
                ", stationHigh='" + stationHigh + '\'' +
                ", geoLatitude='" + geoLatitude + '\'' +
                ", geoLength='" + geoLength + '\'' +
                ", stationName='" + stationName + '\'' +
                ", bundesland='" + bundesLand + '\'' +
                '}';
    }
}
