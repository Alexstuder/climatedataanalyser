package ch.studer.germanclimatedataanalyser.model;

import java.math.BigDecimal;

public class Point {

    private BigDecimal geo_altitude;
    private  BigDecimal geo_length ;

    public Point(BigDecimal geo_altitude, BigDecimal geo_length) {

        this.geo_altitude = geo_altitude;
        this.geo_length = geo_length;
    }

    public  BigDecimal getgeo_altitude() {
        return geo_altitude;
    }

    public  BigDecimal getgeo_length() {
        return geo_length;
    }

    @Override
    public String toString() {
        return "Point{" +
                "geo_altitude=" + geo_altitude +
                ", geo_length=" + geo_length +
                '}';
    }
}
