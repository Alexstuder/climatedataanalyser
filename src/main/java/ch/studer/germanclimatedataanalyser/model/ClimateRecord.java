package ch.studer.germanclimatedataanalyser.model;

import java.math.BigDecimal;

public class ClimateRecord {

    // End date 'yyyy' -> 2018
    private String endPeriod;

    // Start date 'yyyy' -> 1988
    private String startDate;


    public ClimateRecord(String endPeriod, String startDate) {
        this.endPeriod = endPeriod;
        this.startDate = startDate;
        this.setTempJan(new BigDecimal(0));
        this.setTempFeb(new BigDecimal(0));
        this.setTempMar(new BigDecimal(0));
        this.setTempApr(new BigDecimal(0));
        this.setTempMai(new BigDecimal(0));
        this.setTempJun(new BigDecimal(0));
        this.setTempJul(new BigDecimal(0));
        this.setTempAug(new BigDecimal(0));
        this.setTempSep(new BigDecimal(0));
        this.setTempOkt(new BigDecimal(0));
        this.setTempNov(new BigDecimal(0));
        this.setTempDez(new BigDecimal(0));
    }

    // average Temperature for Dez;
    private BigDecimal tempDez;

    // average Temperature for Nov;
    private BigDecimal tempNov;

    // average Temperature for Okt;
    private BigDecimal tempOkt;

    // average Temperature for Sep;
    private BigDecimal tempSep;

    // average Temperature for Aug;
    private BigDecimal tempAug;

    // average Temperature for Jul;
    private BigDecimal tempJul;

    // average Temperature for Jun;
    private BigDecimal tempJun;

    // average Temperature for Mai;
    private BigDecimal tempMai;

    // average Temperature for Apr;
    private BigDecimal tempApr;

    // average Temperature for Mar;
    private BigDecimal tempMar;

    // average Temperature for Feb;
    private BigDecimal tempFeb;

    // average Temperature for Jan;
    private BigDecimal tempJan;

    public String getEndPeriod() {
        return endPeriod;
    }

    public String getStartDate() {
        return startDate;
    }

    public BigDecimal getTempDez() {
        return tempDez;
    }

    public BigDecimal getTempNov() {
        return tempNov;
    }

    public BigDecimal getTempOkt() {
        return tempOkt;
    }

    public BigDecimal getTempSep() {
        return tempSep;
    }

    public BigDecimal getTempAug() {
        return tempAug;
    }

    public BigDecimal getTempJul() {
        return tempJul;
    }

    public BigDecimal getTempJun() {
        return tempJun;
    }

    public BigDecimal getTempMai() {
        return tempMai;
    }

    public BigDecimal getTempApr() {
        return tempApr;
    }

    public BigDecimal getTempMar() {
        return tempMar;
    }

    public BigDecimal getTempFeb() {
        return tempFeb;
    }

    public BigDecimal getTempJan() {
        return tempJan;
    }

    public void setEndPeriod(String endPeriod) {
        this.endPeriod = endPeriod;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setTempDez(BigDecimal tempDez) {
        this.tempDez = tempDez;
    }

    public void setTempNov(BigDecimal tempNov) {
        this.tempNov = tempNov;
    }

    public void setTempOkt(BigDecimal tempOkt) {
        this.tempOkt = tempOkt;
    }

    public void setTempSep(BigDecimal tempSep) {
        this.tempSep = tempSep;
    }

    public void setTempAug(BigDecimal tempAug) {
        this.tempAug = tempAug;
    }

    public void setTempJul(BigDecimal tempJul) {
        this.tempJul = tempJul;
    }

    public void setTempJun(BigDecimal tempJun) {
        this.tempJun = tempJun;
    }

    public void setTempMai(BigDecimal tempMai) {
        this.tempMai = tempMai;
    }

    public void setTempApr(BigDecimal tempApr) {
        this.tempApr = tempApr;
    }

    public void setTempMar(BigDecimal tempMar) {
        this.tempMar = tempMar;
    }

    public void setTempFeb(BigDecimal tempFeb) {
        this.tempFeb = tempFeb;
    }

    public void setTempJan(BigDecimal tempJan) {
        this.tempJan = tempJan;
    }
}
