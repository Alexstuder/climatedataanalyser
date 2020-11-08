package ch.studer.germanclimatedataanalyser.model.dto.db;

public class DbLoadStep {

    private String stepName;
    private String startTime;
    private String stepEndTime;
    private String readCount;
    private String writeCount;
    private String stepStatus;

    public DbLoadStep(String stepName, String startTime, String stepEndTime, String readCount, String writeCount, String stepStatus) {
        this.stepName = stepName;
        this.startTime = startTime;
        this.stepEndTime = stepEndTime;
        this.readCount = readCount;
        this.writeCount = writeCount;
        this.stepStatus = stepStatus;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStepEndTime() {
        return stepEndTime;
    }

    public void setStepEndTime(String stepEndTime) {
        this.stepEndTime = stepEndTime;
    }

    public String getReadCount() {
        return readCount;
    }

    public void setReadCount(String readCount) {
        this.readCount = readCount;
    }

    public String getWriteCount() {
        return writeCount;
    }

    public void setWriteCount(String writeCount) {
        this.writeCount = writeCount;
    }

    public String getStepStatus() {
        return stepStatus;
    }

    public void setStepStatus(String stepStatus) {
        this.stepStatus = stepStatus;
    }
}
