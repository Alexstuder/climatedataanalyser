package ch.studer.germanclimatedataanalyser.model.dto.helper;

public class GpsPoint {

    // längengrad  -180 , 0 , 180
    private double longitude ;

    // breitengrad -90, 0 , 90
    private double latitude ;

    private final static double NULL = 1000;
    public GpsPoint(){

        latitude = NULL;
        longitude = NULL;

    }
    public  GpsPoint(double latitude , double longitude){
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public boolean isLongitudeValid(){
        boolean status = false ;

        if(this.longitude >= -180 && this.longitude <= 180){
            status = true;
        }

        return status;
    }

    public boolean isLatitudeValid(){
        boolean status = false ;

        if((this.latitude >= -90) && (this.latitude <= 90)){
            status = true;
        }

        return status;
    }

    // Both have to be <>1000 to bie valid or not NULL
    public boolean isGpsNull(){
        boolean status = false;

        if (longitude == NULL || latitude == NULL){
            status = true;
        }

        return status;
    }


    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
