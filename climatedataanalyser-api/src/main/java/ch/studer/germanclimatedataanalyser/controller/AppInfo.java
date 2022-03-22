package ch.studer.germanclimatedataanalyser.controller;

import ch.studer.germanclimatedataanalyser.model.dto.AppInfo.AppInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        appInfoDto.setBuildTime(String.valueOf(buildProperties.getTime()));

        return appInfoDto;
    }
}
