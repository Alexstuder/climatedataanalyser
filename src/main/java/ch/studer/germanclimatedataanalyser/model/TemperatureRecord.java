package ch.studer.germanclimatedataanalyser.model;

import java.math.BigDecimal;

public class TemperatureRecord {


    String year ;
    BigDecimal jan;
    BigDecimal feb;
    BigDecimal mar;
    BigDecimal apr;
    BigDecimal mai;
    BigDecimal jun;
    BigDecimal jul;
    BigDecimal aug;
    BigDecimal sep;
    BigDecimal oct;
    BigDecimal nov;
    BigDecimal dec;

    // ####################################################
    // #   Constructor
    // ####################################################
    public TemperatureRecord(String year) {
        this.year = year;
        this.jan = null ;
        this.feb = null ;
        this.mar = null ;
        this.apr = null ;
        this.mai = null ;
        this.jun = null ;
        this.jul = null ;
        this.aug = null ;
        this.sep = null ;
        this.oct = null ;
        this.nov = null ;
        this.dec = null ;
    }

    // ####################################################
    // #   Getter and Setter
    // ####################################################


    public String getYear() {
        return year;
    }

    public BigDecimal getJan() {
        return jan;
    }

    public BigDecimal getFeb() {
        return feb;
    }

    public BigDecimal getMar() {
        return mar;
    }

    public BigDecimal getApr() {
        return apr;
    }

    public BigDecimal getMai() {
        return mai;
    }

    public BigDecimal getJun() {
        return jun;
    }

    public BigDecimal getJul() {
        return jul;
    }

    public BigDecimal getAug() {
        return aug;
    }

    public BigDecimal getSep() {
        return sep;
    }

    public BigDecimal getOct() {
        return oct;
    }

    public BigDecimal getNov() {
        return nov;
    }

    public BigDecimal getDec() {
        return dec;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setJan(BigDecimal jan) {
        this.jan = jan;
    }

    public void setFeb(BigDecimal feb) {
        this.feb = feb;
    }

    public void setMar(BigDecimal mar) {
        this.mar = mar;
    }

    public void setApr(BigDecimal apr) {
        this.apr = apr;
    }

    public void setMai(BigDecimal mai) {
        this.mai = mai;
    }

    public void setJun(BigDecimal jun) {
        this.jun = jun;
    }

    public void setJul(BigDecimal jul) {
        this.jul = jul;
    }

    public void setAug(BigDecimal aug) {
        this.aug = aug;
    }

    public void setSep(BigDecimal sep) {
        this.sep = sep;
    }

    public void setOct(BigDecimal oct) {
        this.oct = oct;
    }

    public void setNov(BigDecimal nov) {
        this.nov = nov;
    }

    public void setDec(BigDecimal dec) {
        this.dec = dec;
    }
}
