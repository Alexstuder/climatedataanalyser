package ch.studer.germanclimatedataanalyser.model;

public class ClimateRecord {

    // End date 'yyyy' -> 2018
    private String endPeriod;

    // Start date 'yyyy' -> 1988
    private String startDate;


    public ClimateRecord(String endPeriod, String startDate) {
        this.endPeriod = endPeriod;
        this.startDate = startDate;
    }

    // average Temperature for Dez;
    private double tempDez;

    // average Temperature for Nov;
    private double tempNov;

    // average Temperature for Okt;
    private double tempOkt;

    // average Temperature for Sep;
    private double tempSep;

    // average Temperature for Aug;
    private double tempAug;

    // average Temperature for Jul;
    private double tempJul;

    // average Temperature for Jun;
    private double tempJun;

    // average Temperature for Mai;
    private double tempMai;

    // average Temperature for Apr;
    private double tempApr;

    // average Temperature for Mar;
    private double tempMar;

    // average Temperature for Feb;
    private double tempFeb;

    // average Temperature for Jan;
    private double tempJan;

    public String getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(String endPeriod) {
        this.endPeriod = endPeriod;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public double getTempDez() {
        return tempDez;
    }

    public void setTempDez(double tempDez) {
        this.tempDez = tempDez;
    }

    public double getTempNov() {
        return tempNov;
    }

    public void setTempNov(double tempNov) {
        this.tempNov = tempNov;
    }

    public double getTempOkt() {
        return tempOkt;
    }

    public void setTempOkt(double tempOkt) {
        this.tempOkt = tempOkt;
    }

    public double getTempSep() {
        return tempSep;
    }

    public void setTempSep(double tempSep) {
        this.tempSep = tempSep;
    }

    public double getTempAug() {
        return tempAug;
    }

    public void setTempAug(double tempAug) {
        this.tempAug = tempAug;
    }

    public double getTempJul() {
        return tempJul;
    }

    public void setTempJul(double tempJul) {
        this.tempJul = tempJul;
    }

    public double getTempJun() {
        return tempJun;
    }

    public void setTempJun(double tempJun) {
        this.tempJun = tempJun;
    }

    public double getTempMai() {
        return tempMai;
    }

    public void setTempMai(double tempMai) {
        this.tempMai = tempMai;
    }

    public double getTempApr() {
        return tempApr;
    }

    public void setTempApr(double tempApr) {
        this.tempApr = tempApr;
    }

    public double getTempMar() {
        return tempMar;
    }

    public void setTempMar(double tempMar) {
        this.tempMar = tempMar;
    }

    public double getTempFeb() {
        return tempFeb;
    }

    public void setTempFeb(double tempFeb) {
        this.tempFeb = tempFeb;
    }

    public double getTempJan() {
        return tempJan;
    }

    public void setTempJan(double tempJan) {
        this.tempJan = tempJan;
    }
}
