package ch.studer.germanclimatedataanalyser.model;

import java.io.Serializable;

public class MonthFile implements Serializable {

    //private static final long serialVersionUID = -6402068923614583448L ;
  private String stationsId;
  private String messDatumBeginn;
  private String messDatumEnde;
  private String qn4;
  private String moN;
  private String moTt;
  private String moTx;
  private String moTn;
  private String moFk;
  private String mxTx;
  private String mxFx;
  private String mxTn;
  private String moSdS;
  private String qn6;
  private String moRr;
  private String mxRs;
  private String eor;

  public MonthFile(){

  }

    public MonthFile(String stationsId
            , String messDatumBeginn
            , String messDatumEnde
            , String qn4
            , String moN
            , String moTt
            , String moTx
            , String moTn
            , String moFk
            , String mxTx
            , String mxFx
            , String mxTn
            , String moSdS
            , String qn6
            , String moRr
            , String mxRs
            , String eor ) {
        this.stationsId = stationsId;
        this.messDatumBeginn = messDatumBeginn;
        this.messDatumEnde = messDatumEnde;
        this.qn4 = qn4;
        this.moN = moN;
        this.moTt = moTt;
        this.moTx = moTx;
        this.moTn = moTn;
        this.moFk = moFk;
        this.mxTx = mxTx;
        this.mxFx = mxFx;
        this.mxTn = mxTn;
        this.moSdS = moSdS;
        this.qn6 = qn6;
        this.moRr = moRr;
        this.mxRs = mxRs;
        this.eor = eor;
    }

    public String getStationsId() {
        return stationsId;
    }

    public void setStationsId(String stationsId) {
        this.stationsId = stationsId;
    }

    public String getMessDatumBeginn() {
        return messDatumBeginn;
    }

    public void setMessDatumBeginn(String messDatumBeginn) {
        this.messDatumBeginn = messDatumBeginn;
    }

    public String getMessDatumEnde() {
        return messDatumEnde;
    }

    public void setMessDatumEnde(String messDatumEnde) {
        this.messDatumEnde = messDatumEnde;
    }

    public String getQn4() {
        return qn4;
    }

    public void setQn4(String qn4) {
        this.qn4 = qn4;
    }

    public String getMoN() {
        return moN;
    }

    public void setMoN(String moN) {
        this.moN = moN;
    }

    public String getMoTt() {
        return moTt;
    }

    public void setMoTt(String moTt) {
        this.moTt = moTt;
    }

    public String getMoTx() {
        return moTx;
    }

    public void setMoTx(String moTx) {
        this.moTx = moTx;
    }

    public String getMoTn() {
        return moTn;
    }

    public void setMoTn(String moTn) {
        this.moTn = moTn;
    }

    public String getMoFk() {
        return moFk;
    }

    public void setMoFk(String moFk) {
        this.moFk = moFk;
    }

    public String getMxTx() {
        return mxTx;
    }

    public void setMxTx(String mxTx) {
        this.mxTx = mxTx;
    }

    public String getMxFx() {
        return mxFx;
    }

    public void setMxFx(String mxFx) {
        this.mxFx = mxFx;
    }

    public String getMxTn() {
        return mxTn;
    }

    public void setMxTn(String mxTn) {
        this.mxTn = mxTn;
    }

    public String getMoSdS() {
        return moSdS;
    }

    public void setMoSdS(String moSdS) {
        this.moSdS = moSdS;
    }

    public String getQn6() {
        return qn6;
    }

    public void setQn6(String qn6) {
        this.qn6 = qn6;
    }

    public String getMoRr() {
        return moRr;
    }

    public void setMoRr(String moRr) {
        this.moRr = moRr;
    }

    public String getMxRs() {
        return mxRs;
    }

    public void setMxRs(String mxRs) {
        this.mxRs = mxRs;
    }

    public String getEor() {
        return eor;
    }

    public void setEor(String eor) {
        this.eor = eor;
    }

      @Override
    public String toString() {
        return "MonthFile{" +
                "stationsId='" + stationsId + '\'' +
                ", messDatumBeginn='" + messDatumBeginn + '\'' +
                ", messDatumEnde='" + messDatumEnde + '\'' +
                ", qn4='" + qn4 + '\'' +
                ", moN='" + moN + '\'' +
                ", moTt='" + moTt + '\'' +
                ", moTx='" + moTx + '\'' +
                ", moTn='" + moTn + '\'' +
                ", moFk='" + moFk + '\'' +
                ", mxTx='" + mxTx + '\'' +
                ", mxFx='" + mxFx + '\'' +
                ", mxTn='" + mxTn + '\'' +
                ", moSdS='" + moSdS + '\'' +
                ", qn6='" + qn6 + '\'' +
                ", moRr='" + moRr + '\'' +
                ", mxRs='" + mxRs + '\'' +
                ", eor='" + eor + '\'' +
                '}';
    }
}
