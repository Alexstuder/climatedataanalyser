package ch.studer.germanclimatedataanalyser.model;

import java.io.Serializable;

public class Month implements Serializable {

    //private static final long serialVersionUID = -6402068923614583448L ;
  private int stationsId;
  private int messDatumBeginn;
  private int messDatumEnde;
  private int qn4;
  private double moN;
  private double moTt;
  private double moTx;
  private double moTn;
  private double moFk;
  private double mxTx;
  private double mxFx;
  private double mxTn;
  private double moSdS;
  private int qn6;
  private double moRr;
  private double mxRs;

  public Month(){

  }

    public Month(int stationsId, int messDatumBeginn, int messDatumEnde, int qn4, double moN, double moTt, double moTx, double moTn, double moFk, double mxTx, double mxFx, double mxTn, double moSdS, int qn6, double moRr, double mxRs) {
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
    }

    public int getStationsId() {
        return stationsId;
    }

    public void setStationsId(int stationsId) {
        this.stationsId = stationsId;
    }

    public int getMessDatumBeginn() {
        return messDatumBeginn;
    }

    public void setMessDatumBeginn(int messDatumBeginn) {
        this.messDatumBeginn = messDatumBeginn;
    }

    public int getMessDatumEnde() {
        return messDatumEnde;
    }

    public void setMessDatumEnde(int messDatumEnde) {
        this.messDatumEnde = messDatumEnde;
    }

    public int getQn4() {
        return qn4;
    }

    public void setQn4(int qn4) {
        this.qn4 = qn4;
    }

    public double getMoN() {
        return moN;
    }

    public void setMoN(double moN) {
        this.moN = moN;
    }

    public double getMoTt() {
        return moTt;
    }

    public void setMoTt(double moTt) {
        this.moTt = moTt;
    }

    public double getMoTx() {
        return moTx;
    }

    public void setMoTx(double moTx) {
        this.moTx = moTx;
    }

    public double getMoTn() {
        return moTn;
    }

    public void setMoTn(double moTn) {
        this.moTn = moTn;
    }

    public double getMoFk() {
        return moFk;
    }

    public void setMoFk(double moFk) {
        this.moFk = moFk;
    }

    public double getMxTx() {
        return mxTx;
    }

    public void setMxTx(double mxTx) {
        this.mxTx = mxTx;
    }

    public double getMxFx() {
        return mxFx;
    }

    public void setMxFx(double mxFx) {
        this.mxFx = mxFx;
    }

    public double getMxTn() {
        return mxTn;
    }

    public void setMxTn(double mxTn) {
        this.mxTn = mxTn;
    }

    public double getMoSdS() {
        return moSdS;
    }

    public void setMoSdS(double moSdS) {
        this.moSdS = moSdS;
    }

    public int getQn6() {
        return qn6;
    }

    public void setQn6(int qn6) {
        this.qn6 = qn6;
    }

    public double getMoRr() {
        return moRr;
    }

    public void setMoRr(double moRr) {
        this.moRr = moRr;
    }

    public double getMxRs() {
        return mxRs;
    }

    public void setMxRs(double mxRs) {
        this.mxRs = mxRs;
    }

    @Override
    public String toString() {
        return "Month{" +
                "stationsId=" + stationsId +
                ", messDatumBeginn=" + messDatumBeginn +
                ", messDatumEnde=" + messDatumEnde +
                ", qn4=" + qn4 +
                ", moN=" + moN +
                ", moTt=" + moTt +
                ", moTx=" + moTx +
                ", moTn=" + moTn +
                ", moFk=" + moFk +
                ", mxTx=" + mxTx +
                ", mxFx=" + mxFx +
                ", mxTn=" + mxTn +
                ", moSdS=" + moSdS +
                ", qn6=" + qn6 +
                ", moRr=" + moRr +
                ", mxRs=" + mxRs +
                '}';
    }
}
