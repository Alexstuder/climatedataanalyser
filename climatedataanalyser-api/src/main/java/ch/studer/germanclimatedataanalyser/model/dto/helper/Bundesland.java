package ch.studer.germanclimatedataanalyser.model.dto.helper;

import ch.studer.germanclimatedataanalyser.service.db.StationService;
import org.springframework.beans.factory.annotation.Autowired;

public class Bundesland {

    @Autowired
    StationService stationService;

    String name;

    public Bundesland() {
        this.name = "";
    }

    public boolean exists() {
        return stationService.bundeslandExists(this.name);

    }

    public String proof() {
        if (!exists()) {
            return this.name + " Bundesland doesn't exist!";
        }
        return "";
    }

    public void setName(String name) {
        this.name = name.stripLeading().stripTrailing();
    }

    public String getName() {
        return name;
    }
}
