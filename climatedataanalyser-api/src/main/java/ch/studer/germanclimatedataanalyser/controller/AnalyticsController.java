package ch.studer.germanclimatedataanalyser.controller;

import ch.studer.germanclimatedataanalyser.model.dto.BundeslaenderDto;
import ch.studer.germanclimatedataanalyser.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin
public class AnalyticsController {

    @Autowired
    StationService stationService;

    @GetMapping("/")
    public List<String> handle() throws Exception{
//        List<String> bundeslaender = new ArrayList<String>();
//        bundeslaender.add("Zürich");
        BundeslaenderDto bundeslaenderDto = new BundeslaenderDto();

//        return bundeslaenderDto.mapToDto(bundeslaender);
        return bundeslaenderDto.mapToDto(stationService.getAllBundeslaender());

    }

}



