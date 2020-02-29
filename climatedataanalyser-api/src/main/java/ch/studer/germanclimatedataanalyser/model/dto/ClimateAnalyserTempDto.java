package ch.studer.germanclimatedataanalyser.model.dto;

import ch.studer.germanclimatedataanalyser.model.database.StationClimate;

import java.math.BigDecimal;

/*
This is just a wrapper class to get an instance from a abstract class
 */
public class ClimateAnalyserTempDto {

    private BigDecimal januar;
    private BigDecimal februar;
    private BigDecimal maerz;
    private BigDecimal april;
    private BigDecimal mai;
    private BigDecimal juni;
    private BigDecimal juli;
    private BigDecimal august;
    private BigDecimal september;
    private BigDecimal oktober;
    private BigDecimal november;
    private  BigDecimal dezember;



    public ClimateAnalyserTempDto(){
        setJanuar(new BigDecimal("0"));
        setFebruar(new BigDecimal("0"));
        setMaerz(new BigDecimal("0"));
        setApril(new BigDecimal("0"));
        setMai(new BigDecimal("0"));
        setJuni(new BigDecimal("0"));
        setJuli(new BigDecimal("0"));
        setAugust(new BigDecimal("0"));
        setSeptember(new BigDecimal("0"));
        setOktober(new BigDecimal("0"));
        setNovember(new BigDecimal("0"));
        setDezember(new BigDecimal("0"));
    }

    public BigDecimal getJanuar() {
        return januar;
    }

    public void setJanuar(BigDecimal januar) {
        this.januar = januar;
    }

    public BigDecimal getFebruar() {
        return februar;
    }

    public void setFebruar(BigDecimal februar) {
        this.februar = februar;
    }

    public BigDecimal getMaerz() {
        return maerz;
    }

    public void setMaerz(BigDecimal maerz) {
        this.maerz = maerz;
    }

    public BigDecimal getApril() {
        return april;
    }

    public void setApril(BigDecimal april) {
        this.april = april;
    }

    public BigDecimal getMai() {
        return mai;
    }

    public void setMai(BigDecimal mai) {
        this.mai = mai;
    }

    public BigDecimal getJuni() {
        return juni;
    }

    public void setJuni(BigDecimal juni) {
        this.juni = juni;
    }

    public BigDecimal getJuli() {
        return juli;
    }

    public void setJuli(BigDecimal juli) {
        this.juli = juli;
    }

    public BigDecimal getAugust() {
        return august;
    }

    public void setAugust(BigDecimal august) {
        this.august = august;
    }

    public BigDecimal getSeptember() {
        return september;
    }

    public void setSeptember(BigDecimal september) {
        this.september = september;
    }

    public BigDecimal getOktober() {
        return oktober;
    }

    public void setOktober(BigDecimal oktober) {
        this.oktober = oktober;
    }

    public BigDecimal getNovember() {
        return november;
    }

    public void setNovember(BigDecimal november) {
        this.november = november;
    }

    public BigDecimal getDezember() {
        return dezember;
    }

    public void setDezember(BigDecimal dezember) {
        this.dezember = dezember;
    }

    public ClimateAnalyserTempDto mapFrom(StationClimate stationClimate) {
        ClimateAnalyserTempDto climateAnalyserTempDto = new ClimateAnalyserTempDto();

        climateAnalyserTempDto.setJanuar(stationClimate.getJanuar());
        climateAnalyserTempDto.setFebruar(stationClimate.getFebruar());
        climateAnalyserTempDto.setMaerz(stationClimate.getMaerz());
        climateAnalyserTempDto.setApril(stationClimate.getApril());
        climateAnalyserTempDto.setMai(stationClimate.getMai());
        climateAnalyserTempDto.setJuni(stationClimate.getJuni());
        climateAnalyserTempDto.setJuli(stationClimate.getJuli());
        climateAnalyserTempDto.setAugust(stationClimate.getAugust());
        climateAnalyserTempDto.setSeptember(stationClimate.getSeptember());
        climateAnalyserTempDto.setOktober(stationClimate.getOktober());
        climateAnalyserTempDto.setNovember(stationClimate.getNovember());
        climateAnalyserTempDto.setDezember(stationClimate.getDezember());




        return climateAnalyserTempDto;
    }
}
