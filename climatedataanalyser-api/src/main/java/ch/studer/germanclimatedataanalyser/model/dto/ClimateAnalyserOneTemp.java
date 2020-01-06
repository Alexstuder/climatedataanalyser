package ch.studer.germanclimatedataanalyser.model.dto;

import ch.studer.germanclimatedataanalyser.model.database.TemperatureForMonths;

import java.math.BigDecimal;

/*
This is just a wrapper class to get an instance from a abstract class
 */
public class ClimateAnalyserOneTemp extends  TemperatureForMonths{

    public void setJanuar(BigDecimal jan){super.setJanuar(jan);}
    public void setFebruar(BigDecimal Februar ){super.setFebruar(Februar);}
    public void setMaerz(BigDecimal Maerz ){super.setMaerz(Maerz);}
    public void setApril(BigDecimal April ){super.setApril(April);}
    public void setMai(BigDecimal Mai ){super.setMai(Mai);}
    public void setJuni(BigDecimal Juni ){super.setJuni(Juni);}
    public void setJuli(BigDecimal Juli ){super.setJuli(Juli);}
    public void setAugust(BigDecimal August ){super.setAugust(August);}
    public void setSeptember(BigDecimal September ){super.setSeptember(September);}
    public void setOktober(BigDecimal Oktober ){super.setOktober(Oktober);}
    public void setNovember(BigDecimal November ){super.setNovember(November);}
    public void setDezember(BigDecimal Dezember ){super.setDezember(Dezember);}

    public ClimateAnalyserOneTemp(){
        super.setJanuar(new BigDecimal("0"));
        super.setFebruar(new BigDecimal("0"));
        super.setMaerz(new BigDecimal("0"));
        super.setApril(new BigDecimal("0"));
        super.setMai(new BigDecimal("0"));
        super.setJuni(new BigDecimal("0"));
        super.setJuli(new BigDecimal("0"));
        super.setAugust(new BigDecimal("0"));
        super.setSeptember(new BigDecimal("0"));
        super.setOktober(new BigDecimal("0"));
        super.setNovember(new BigDecimal("0"));
        super.setDezember(new BigDecimal("0"));
    }


}
