package ch.studer.germanclimatedataanalyser.model.database;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "MONTH")
public class Month implements Serializable {

    //private static final long serialVersionUID = -6402068923614583448L ;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MONTH_ID")
    private int monthId;

    @Column(name = "STATIONS_ID")
    private int stationsId;

    @Column(name = "MESS_DATUM_BEGINN")
    private Date messDatumBeginn;

    @Column(name = "MESS_DATUM_ENDE")
    private Date messDatumEnde;

    @Column(name = "QN_4")
    private int qn4;

    @Column(name = "MO_N")
    private BigDecimal moN;

    @Column(name = "MO_TT")
    private BigDecimal moTt;

    @Column(name = "MO_TX")
    private BigDecimal moTx;

    @Column(name = "MO_TN")
    private BigDecimal moTn;

    @Column(name = "MO_FK")
    private BigDecimal moFk;

    @Column(name = "MX_TX")
    private BigDecimal mxTx;

    @Column(name = "MX_FX")
    private BigDecimal mxFx;

    @Column(name = "MX_TN")
    private BigDecimal mxTn;

    @Column(name = "MO_SD_S")
    private BigDecimal moSdS;

    @Column(name = "QN_6")
    private int qn6;

    @Column(name = "MO_RR")
    private BigDecimal moRr;

    @Column(name = "MX_RS")
    private BigDecimal mxRs;

    public Month() {

    }

    public Month(int stationsId, Date messDatumBeginn, Date messDatumEnde, int qn4, BigDecimal moN, BigDecimal moTt, BigDecimal moTx, BigDecimal moTn, BigDecimal moFk, BigDecimal mxTx, BigDecimal mxFx, BigDecimal mxTn, BigDecimal moSdS, int qn6, BigDecimal moRr, BigDecimal mxRs) {
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

    public Date getMessDatumBeginn() {
        return messDatumBeginn;
    }

    public void setMessDatumBeginn(Date messDatumBeginn) {
        this.messDatumBeginn = messDatumBeginn;
    }

    public Date getMessDatumEnde() {
        return messDatumEnde;
    }

    public void setMessDatumEnde(Date messDatumEnde) {
        this.messDatumEnde = messDatumEnde;
    }

    public int getQn4() {
        return qn4;
    }

    public void setQn4(int qn4) {
        this.qn4 = qn4;
    }

    public BigDecimal getMoN() {
        return moN;
    }

    public void setMoN(BigDecimal moN) {
        this.moN = moN;
    }

    public BigDecimal getMoTt() {
        return moTt;
    }

    public void setMoTt(BigDecimal moTt) {
        this.moTt = moTt;
    }

    public BigDecimal getMoTx() {
        return moTx;
    }

    public void setMoTx(BigDecimal moTx) {
        this.moTx = moTx;
    }

    public BigDecimal getMoTn() {
        return moTn;
    }

    public void setMoTn(BigDecimal moTn) {
        this.moTn = moTn;
    }

    public BigDecimal getMoFk() {
        return moFk;
    }

    public void setMoFk(BigDecimal moFk) {
        this.moFk = moFk;
    }

    public BigDecimal getMxTx() {
        return mxTx;
    }

    public void setMxTx(BigDecimal mxTx) {
        this.mxTx = mxTx;
    }

    public BigDecimal getMxFx() {
        return mxFx;
    }

    public void setMxFx(BigDecimal mxFx) {
        this.mxFx = mxFx;
    }

    public BigDecimal getMxTn() {
        return mxTn;
    }

    public void setMxTn(BigDecimal mxTn) {
        this.mxTn = mxTn;
    }

    public BigDecimal getMoSdS() {
        return moSdS;
    }

    public void setMoSdS(BigDecimal moSdS) {
        this.moSdS = moSdS;
    }

    public int getQn6() {
        return qn6;
    }

    public void setQn6(int qn6) {
        this.qn6 = qn6;
    }

    public BigDecimal getMoRr() {
        return moRr;
    }

    public void setMoRr(BigDecimal moRr) {
        this.moRr = moRr;
    }

    public BigDecimal getMxRs() {
        return mxRs;
    }

    public void setMxRs(BigDecimal mxRs) {
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
