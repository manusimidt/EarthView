package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.geographicCS.Geodetic2D;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.geographicCS.Geodetic3D;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.geographicCS.Geographic2D;


/**
 * Created by Jonas on 3/16/2017.
 */

public class CSConverter {
    public static final double OneOverPi = 1.0 / Math.PI;
    public static final double PiOverTwo = Math.PI * 0.5;
    public static final double PiOverThree = Math.PI / 3.0;
    public static final double PiOverFour = Math.PI / 4.0;
    public static final double PiOverSix = Math.PI / 6.0;
    public static final double ThreePiOver2 = (3.0 * Math.PI) * 0.5;
    public static final double TwoPi = 2.0 * Math.PI;
    public static final double OneOverTwoPi = 1.0 / (2.0 * Math.PI);
    public static final double RadiansPerDegree = Math.PI / 180.0;

    public static double toRadians(double degrees)
    {
        return degrees * RadiansPerDegree;
    }

    public static Geodetic3D toRadians(Geodetic3D geodetic)
    {
        return new Geodetic3D(toRadians(geodetic.getλ()), toRadians(geodetic.getφ()), geodetic.getHeight());
    }

    public static Geodetic2D toRadians(Geodetic2D geodetic)
    {
        return new Geodetic2D(toRadians(geodetic.getλ()), toRadians(geodetic.getφ()));
    }
    public static Geodetic2D toRadiants(Geographic2D geographic){
        return new Geodetic2D(toRadians(geographic.getLongitude()), toRadians(geographic.getLatitude()));
    }

    public static double toDegrees(double radians)
    {
        return radians / RadiansPerDegree;
    }

    public static Geodetic3D toDegrees(Geodetic3D geodetic)
    {
        return new Geodetic3D(toDegrees(geodetic.getλ()), toDegrees(geodetic.getφ()), geodetic.getHeight());
    }

    public static Geodetic2D toDegrees(Geodetic2D geodetic)
    {
        return new Geodetic2D(toDegrees(geodetic.getλ()), toDegrees(geodetic.getφ()));
    }

}
