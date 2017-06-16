package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.geographicCS;

/**
 * Created by Jonas on 5/8/2017.
 */

public class Geographic2D {


    private double latitude;
    private double longitude;

    /**
     * Class for representing a normal geographic coordinate
     * @param latitude latitude of the geographic coordinate
     * @param longitude longitude of the geographic coordinate
     */

    public Geographic2D(double latitude, double longitude) {

        if(latitude <-90 || latitude >90 || longitude <-180 || longitude >180){
            throw new IllegalArgumentException("Wrong geographic coordinates! Lat: " + latitude +", Lng: " + longitude+".");
        }

        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
