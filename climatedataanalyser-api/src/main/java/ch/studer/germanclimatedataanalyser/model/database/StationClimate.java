package ch.studer.germanclimatedataanalyser.model.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.List;

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


    private static final Logger log = LoggerFactory.getLogger(StationClimate.class);


    public StationClimate() {
        super();
    }

    public StationClimate(int stationId){

        super(stationId);
        this.endPeriod      = null;
        this.startPeriod      = null;

    };

    public StationClimate getAverage(List<StationClimate> stationClimateList){
        StationClimate stationClimate = new StationClimate();

         //TODO Implement average calculation !



        return stationClimate;
    }

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
