package ch.studer.germanclimatedataanalyser.model;

public class TemperatureRecord {


    String year ;
    double jan;
    double feb;
    double mar;
    double apr;
    double mai;
    double jun;
    double jul;
    double aug;
    double sep;
    double oct;
    double nov;
    double dec;

    // ####################################################
    // #   Constructor
    // ####################################################
    public TemperatureRecord(String year) {
        this.year = year;
        this.jan = Double.MAX_VALUE;
        this.feb = Double.MAX_VALUE;
        this.mar = Double.MAX_VALUE;
        this.apr = Double.MAX_VALUE;
        this.mai = Double.MAX_VALUE;
        this.jun = Double.MAX_VALUE;
        this.jul = Double.MAX_VALUE;
        this.aug = Double.MAX_VALUE;
        this.sep = Double.MAX_VALUE;
        this.oct = Double.MAX_VALUE;
        this.nov = Double.MAX_VALUE;
        this.dec = Double.MAX_VALUE;
    }

    // ####################################################
    // #   Getter and Setter
    // ####################################################


    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public double getJan() {
        return jan;
    }

    public void setJan(double jan) {
        this.jan = jan;
    }

    public double getFeb() {
        return feb;
    }

    public void setFeb(double feb) {
        this.feb = feb;
    }

    public double getMar() {
        return mar;
    }

    public void setMar(double mar) {
        this.mar = mar;
    }

    public double getApr() {
        return apr;
    }

    public void setApr(double apr) {
        this.apr = apr;
    }

    public double getMai() {
        return mai;
    }

    public void setMai(double mai) {
        this.mai = mai;
    }

    public double getJun() {
        return jun;
    }

    public void setJun(double jun) {
        this.jun = jun;
    }

    public double getJul() {
        return jul;
    }

    public void setJul(double jul) {
        this.jul = jul;
    }

    public double getAug() {
        return aug;
    }

    public void setAug(double aug) {
        this.aug = aug;
    }

    public double getSep() {
        return sep;
    }

    public void setSep(double sep) {
        this.sep = sep;
    }

    public double getOct() {
        return oct;
    }

    public void setOct(double oct) {
        this.oct = oct;
    }

    public double getNov() {
        return nov;
    }

    public void setNov(double nov) {
        this.nov = nov;
    }

    public double getDec() {
        return dec;
    }

    public void setDec(double dec) {
        this.dec = dec;
    }
}
