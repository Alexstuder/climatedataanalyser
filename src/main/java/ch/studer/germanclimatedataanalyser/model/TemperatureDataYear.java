package ch.studer.germanclimatedataanalyser.model;

import java.util.ArrayList;
import java.util.List;

public class TemperatureDataYear {

    // All Temperature Data for Month Jan collected !
    private List<TemperatureDataMonth> jan ;

    // All Temperature Data for Month Feb collected !
    private List<TemperatureDataMonth> feb ;

    // All Temperature Data for Month Mar collected !
    private List<TemperatureDataMonth> mar ;

    // All Temperature Data for Month Apr collected !
    private List<TemperatureDataMonth> apr ;

    // All Temperature Data for Month Mai collected !
    private List<TemperatureDataMonth> mai ;

    // All Temperature Data for Month Jun collected !
    private List<TemperatureDataMonth> jun ;

    // All Temperature Data for Month Jul collected !
    private List<TemperatureDataMonth> jul ;

    // All Temperature Data for Month Aug collected !
    private List<TemperatureDataMonth> aug ;

    // All Temperature Data for Month Sep collected !
    private List<TemperatureDataMonth> sep ;

    // All Temperature Data for Month Oct collected !
    private List<TemperatureDataMonth> oct ;

    // All Temperature Data for Month Nov collected !
    private List<TemperatureDataMonth> nov ;

    // All Temperature Data for Month Dec collected !
    private List<TemperatureDataMonth> dec ;



    // ###########################################
    // #   Constructor
    // ###########################################

    public TemperatureDataYear() {
        this.jan = new ArrayList<TemperatureDataMonth>();
        this.feb = new ArrayList<TemperatureDataMonth>();
        this.mar = new ArrayList<TemperatureDataMonth>();
        this.apr = new ArrayList<TemperatureDataMonth>();
        this.mai = new ArrayList<TemperatureDataMonth>();
        this.jun = new ArrayList<TemperatureDataMonth>();
        this.jul = new ArrayList<TemperatureDataMonth>();
        this.aug = new ArrayList<TemperatureDataMonth>();
        this.sep = new ArrayList<TemperatureDataMonth>();
        this.oct = new ArrayList<TemperatureDataMonth>();
        this.nov = new ArrayList<TemperatureDataMonth>();
        this.dec = new ArrayList<TemperatureDataMonth>();
    }
    // ###########################################
    // #   Getter
    // ###########################################


    public List<TemperatureDataMonth> getJan() {
        return jan;
    }

    public List<TemperatureDataMonth> getFeb() {
        return feb;
    }

    public List<TemperatureDataMonth> getMar() {
        return mar;
    }

    public List<TemperatureDataMonth> getApr() {
        return apr;
    }

    public List<TemperatureDataMonth> getMai() {
        return mai;
    }

    public List<TemperatureDataMonth> getJun() {
        return jun;
    }

    public List<TemperatureDataMonth> getJul() {
        return jul;
    }

    public List<TemperatureDataMonth> getAug() {
        return aug;
    }

    public List<TemperatureDataMonth> getSep() {
        return sep;
    }

    public List<TemperatureDataMonth> getOct() {
        return oct;
    }

    public List<TemperatureDataMonth> getNov() {
        return nov;
    }

    public List<TemperatureDataMonth> getDec() {
        return dec;
    }
}
