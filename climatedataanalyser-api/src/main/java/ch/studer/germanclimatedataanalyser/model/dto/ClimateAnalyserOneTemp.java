package ch.studer.germanclimatedataanalyser.model.dto;

import ch.studer.germanclimatedataanalyser.model.database.TemperatureForMonths;

import java.math.BigDecimal;

/*
This is just a wrapper class to get an instance from a abstract class
 */
public class ClimateAnalyserOneTemp extends  TemperatureForMonths{

    // TODO : the NULL Value should be given trough the application.properties !
    //TODO Inject NULL_TEMPERATURE How ?? it's an abstaract class !?
    // TODO How to inject a BigDecimal !
    private static BigDecimal NULL_TEMPERATURE = new BigDecimal("-999.0000");


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
        super.setFebruar(NULL_TEMPERATURE);
        super.setMaerz(NULL_TEMPERATURE);
        super.setApril(NULL_TEMPERATURE);
        super.setMai(NULL_TEMPERATURE);
        super.setJuni(NULL_TEMPERATURE);
        super.setJuli(NULL_TEMPERATURE);
        super.setAugust(NULL_TEMPERATURE);
        super.setSeptember(NULL_TEMPERATURE);
        super.setOktober(NULL_TEMPERATURE);
        super.setNovember(NULL_TEMPERATURE);
        super.setDezember(NULL_TEMPERATURE);
    }


}
