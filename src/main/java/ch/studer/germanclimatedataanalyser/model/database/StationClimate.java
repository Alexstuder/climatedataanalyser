package ch.studer.germanclimatedataanalyser.model.database;

import javax.persistence.*;

@Entity
@Table(name="CLIMATE")
public class StationClimate extends  StationTemperature{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "CLIMATE_ID")
    private int climateId;

    // End date 'yyyy' -> 2018
    @Column(name="END_PERIOD")
    private String endPeriod;

    // Start date 'yyyy' -> 1988
    @Column(name="START_PERIOD")
    private String startPeriod;



//    public StationClimate() {
//    }

    public StationClimate(int stationId){

        super(stationId);
        this.endPeriod      = null;
        this.startPeriod      = null;

    };

    public int getClimateId() {
        return climateId;
    }

    public String getEndPeriod() {
        return endPeriod;
    }

    public String getStartPeriod() {
        return startPeriod;
    }

    public void setEndPeriod(String endPeriod) {
        this.endPeriod = endPeriod;
    }

    public void setStartPeriod(String startPeriod) {
        this.startPeriod = startPeriod;
    }
}
