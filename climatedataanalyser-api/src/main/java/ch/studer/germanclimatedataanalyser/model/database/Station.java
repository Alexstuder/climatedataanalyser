package ch.studer.germanclimatedataanalyser.model.database;


import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name="STATION")
public class Station {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID")
    private int Id;

    @Column(name="STATION_ID")
    private int stationId;

    @Column(name="DATE_BEGIN")
    private Date dateBegin;

    @Column(name="DATE_End")
    private Date dateEnd;

    @Column(name="STATION_HIGH")
    private BigDecimal stationHigh;

    @Column(name="GEO_LATITUDE")
    private BigDecimal geoLatitude;

    @Column(name="GEO_LENGTH")
    private BigDecimal geoLength;

    @Column(name="STATION_NAME")
    private String stationName;

    @Column(name="BUNDES_LAND")
    private String bundesLand;


    public Station(){

    }
    public Station(int stationId, Date dateBegin, Date dateEnd, BigDecimal stationHigh, BigDecimal geoLatitude, BigDecimal geoLength, String stationName, String bundesLand) {
        this.stationId = stationId;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.stationHigh = stationHigh;
        this.geoLatitude = geoLatitude;
        this.geoLength = geoLength;
        this.stationName = stationName;
        this.bundesLand = bundesLand;
    }

    public int getStationId() {
        return stationId;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public BigDecimal getStationHigh() {
        return stationHigh;
    }

    public BigDecimal getGeoLatitude() {
        return geoLatitude;
    }

    public BigDecimal getGeoLength() {
        return geoLength;
    }

    public String getStationName() {
        return stationName;
    }

    public String getBundesLand() {
        return bundesLand;
    }
}
