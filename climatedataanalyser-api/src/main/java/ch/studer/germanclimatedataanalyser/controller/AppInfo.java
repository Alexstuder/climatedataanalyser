package ch.studer.germanclimatedataanalyser.controller;

import ch.studer.germanclimatedataanalyser.model.dto.AppInfo.AppInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/appInfo")
@CrossOrigin
public class AppInfo {

    @Autowired
    BuildProperties buildProperties;

    @GetMapping("/")
    AppInfoDto appinfo() {
        AppInfoDto appInfoDto = new AppInfoDto();
        appInfoDto.setVersion(buildProperties.getVersion());
        appInfoDto.setBuildTime(getDateFormatted(buildProperties.getTime().atZone(ZoneId.of("Europe/Paris"))));

        return appInfoDto;
    }

    private String getDateFormatted(ZonedDateTime buildtime) {
        String formattedBuildTime;
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH.mm");

        return buildtime.format(format);
    }
}
