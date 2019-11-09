package ch.studer.germanclimatedataanalyser.model;

public class StatisticRecord {

    private int StationsID ;
    private String Filename ;
    private String FirstDatum;
    private String LastDatum;
    private int AnzahlProcess;
    private int AnzahlOnDb;
    private boolean WithoutError;
    private String WhatIsMissing;

    public StatisticRecord(){
        StationsID = 0;
        Filename = "";
        FirstDatum = "";
        LastDatum = "";
        AnzahlProcess = 0;
        AnzahlOnDb = 0;
        WithoutError = true;
        WhatIsMissing = "";

    }


 /*   public StatisticRecord(int stationsID, String filename, String firstDatum, String lastDatum, int anzahlRead, int anzahlProcess, int anzahlWrite, int anzahlOnDb, boolean withoutError, String whatIsMissing) {
        StationsID = stationsID;
        Filename = filename;
        FirstDatum = firstDatum;
        LastDatum = lastDatum;
        AnzahlProcess = anzahlProcess;
        AnzahlOnDb = anzahlOnDb;
        WithoutError = withoutError;
        WhatIsMissing = whatIsMissing;
    }*/


    public int getStationsID() {
        return StationsID;
    }

    public void setStationsID(int stationsID) {
        StationsID = stationsID;
    }

    public String getFilename() {
        return Filename;
    }

    public void setFilename(String filename) {
        Filename = filename;
    }

    public String getFirstDatum() {
        return FirstDatum;
    }

    public void setFirstDatum(String firstDatum) {
        FirstDatum = firstDatum;
    }

    public String getLastDatum() {
        return LastDatum;
    }

    public void setLastDatum(String lastDatum) {
        LastDatum = lastDatum;
    }

    public int getAnzahlProcess() {
        return AnzahlProcess;
    }

    public void setAnzahlProcess(int anzahlProcess) {
        AnzahlProcess = anzahlProcess;
    }

    public int getAnzahlOnDb() {
        return AnzahlOnDb;
    }

    public void setAnzahlOnDb(int anzahlOnDb) {
        AnzahlOnDb = anzahlOnDb;
    }

    public boolean isWithoutError() {
        return WithoutError;
    }

    public void setWithoutError(boolean withoutError) {
        WithoutError = withoutError;
    }

    public String getWhatIsMissing() {
        return WhatIsMissing;
    }

    public void setWhatIsMissing(String whatIsMissing) {
        WhatIsMissing = whatIsMissing;
    }

    @Override
    public String toString() {
        return "StatisticRecord{" +
                "StationsID=" + StationsID +
                ", Filename='" + Filename + '\'' +
                ", FirstDatum='" + FirstDatum + '\'' +
                ", LastDatum='" + LastDatum + '\'' +
                ", AnzahlProcess=" + AnzahlProcess +
                ", AnzahlOnDb=" + AnzahlOnDb +
                ", WithoutError=" + WithoutError +
                ", WhatIsMissing='" + WhatIsMissing + '\'' +
                '}';
    }

}
