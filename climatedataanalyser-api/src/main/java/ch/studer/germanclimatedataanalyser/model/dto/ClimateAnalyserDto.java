package ch.studer.germanclimatedataanalyser.model.dto;

import ch.studer.germanclimatedataanalyser.model.database.TemperatureForMonths;

public class ClimateAnalyserDto {

    String bundesland;

    // Das Jahr, welches es zu untersuchen gilt
    String Year;

    // Klima Daten für das Jahr mit den meisten Daten
    TemperatureForMonths climateDataWithAllRecievedRecords ;

    // Klima Daten für die nur noch heute übrig gebliebenen Stationen
    TemperatureForMonths climateDataOnlyWithTheStationsExistingToday ;


}
