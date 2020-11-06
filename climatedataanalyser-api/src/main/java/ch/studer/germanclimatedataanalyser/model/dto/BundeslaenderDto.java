package ch.studer.germanclimatedataanalyser.model.dto;

import java.util.ArrayList;
import java.util.List;

public class BundeslaenderDto implements Bundeslaender {

    List<String> bundeslaenderDto;

    @Override
    public List<String> mapToDto(List<String> bundeslaenderDbo) {
        bundeslaenderDto = new ArrayList<String>();

        bundeslaenderDto.addAll(bundeslaenderDbo);


        return bundeslaenderDto;
    }


}
