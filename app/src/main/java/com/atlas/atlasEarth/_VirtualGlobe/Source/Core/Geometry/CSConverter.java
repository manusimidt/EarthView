package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.geographicCS.Geodetic2D;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.geographicCS.Geodetic3D;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.geographicCS.Geographic2D;


/**
 * Created by Jonas on 3/16/2017.
 */

public class CSConverter {

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
    public static Geodetic2D toRadians(Geographic2D geographic){
        return new Geodetic2D(toRadians(geographic.getLatitude()),toRadians(geographic.getLongitude()));
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
