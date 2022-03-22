package ch.studer.germanclimatedataanalyser.model.dto.AppInfo;

public class AppInfoDto {

    private String version;
    private String buildTime;


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(String buildTime) {
        this.buildTime = buildTime;
    }
}
