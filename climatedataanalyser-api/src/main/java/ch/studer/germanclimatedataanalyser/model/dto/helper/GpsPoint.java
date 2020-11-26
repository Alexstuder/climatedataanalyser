package ch.studer.germanclimatedataanalyser.model.dto.helper;

public class GpsPoint {


    // längengrad  -180 , 0 , 180
    private double longitude;


    // breitengrad -90, 0 , 90
    private double latitude;

    private final static double MAX_LONGITUDE = 180.00;
    private final static double MIN_LONGITUDE = -180.00;
    private final static double MAX_LATITUDE = 90.00;
    private final static double MIN_LATITUDE = -90.00;
    private final static double NULL = 1000;

    public GpsPoint() {

        latitude = NULL;
        longitude = NULL;

    }

    public GpsPoint(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public boolean isLongitudeValid() {
        return this.longitude >= MIN_LONGITUDE && this.longitude <= MAX_LONGITUDE;
    }

    public boolean isLatitudeValid() {
        return (this.latitude >= MIN_LATITUDE) && (this.latitude <= MAX_LATITUDE);
    }

    // Both have to be <>1000 to be valid or not NULL
    public boolean isGpsNull() {
        return (longitude == NULL || latitude == NULL);
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
