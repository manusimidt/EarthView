package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.geographicCS;


import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3D;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.CSConverter;


public class Ellipsoid {

    //Semimajoraxis Semimajoraxis Semiminoraxis
    public static Ellipsoid Wgs84 = new Ellipsoid(new Vector3D(6378137.0, 6378137.0, 6356752.314245));
    public static Ellipsoid ScaledWgs84 = new Ellipsoid(new Vector3D(1.0, 1.0, 6356752.314245 / 6378137.0));
    public static Ellipsoid UnitSphere = new Ellipsoid(new Vector3D(1.0, 1.0, 1.0));

    private Vector3D radii;
    private Vector3D radiiSquared;
    private Vector3D radiiToTheFourth;
    private Vector3D oneOverRadiiSquared;

    private Ellipsoid(Vector3D radii) {
        if ((radii.x <= 0.0) || (radii.y <= 0.0) || (radii.z <= 0.0)) {
            throw new IllegalArgumentException("Incorrect value for radii");
        }

        this.radii = radii;
        radiiSquared = new Vector3D(
                radii.x * radii.x,
                radii.y * radii.y,
                radii.z * radii.z);
        radiiToTheFourth = new Vector3D(
                radiiSquared.x * radiiSquared.x,
                radiiSquared.y * radiiSquared.y,
                radiiSquared.z * radiiSquared.z);
        oneOverRadiiSquared = new Vector3D(
                1.0 / (radiiSquared.x),
                1.0 / (radiiSquared.y),
                1.0 / (radiiSquared.z));

    }

    public Vector3D convertGeographicToCartesian(Geographic2D geographic) {
        return convertGeodeticToCartesian(CSConverter.toRadians(geographic));
    }


    /**
     * @param geodeticInRadians geographic coordinates
     * @return The associated cartesian coordinates, on the surface of the ellipsoid.
     * Dependent on the geographical reference system
     */
    public Vector3D convertGeodeticToCartesian(Geodetic2D geodeticInRadians) {
        return convertGeodeticToCartesian(new Geodetic3D(geodeticInRadians.getλ(), geodeticInRadians.getφ(), 0.0));
    }


    /**
     * @param geodetic geographic coordinates + height over ellipsoid
     * @return The associated cartesian coordinates. Dependent on the geographical reference system
     */
    public Vector3D convertGeodeticToCartesian(Geodetic3D geodetic) {

        // TODO: 6/15/2017 Is there a better method ?
        geodetic = new Geodetic3D(-geodetic.getφ(), geodetic.getλ(), geodetic.getHeight());

        Vector3D surfaceNormal = getGeodeticSurfaceNormal(geodetic);
        //Ellipsoid radii squared * surfaceNormal
        Vector3D k = radiiSquared.multiplyComponents(surfaceNormal);
        //Compute gamma
        double γ = Math.sqrt(
                (k.x * surfaceNormal.x) +
                        (k.y * surfaceNormal.y) +
                        (k.z * surfaceNormal.z));

        //Point on the Surface of the Ellipsoid
        Vector3D pointOnEllipsoid = k.divide(γ);


        //go from pointOnEllipsoid the surfaceNormal up, until the height is reached
        pointOnEllipsoid.add(surfaceNormal.multiply(geodetic.getHeight()));
        return pointOnEllipsoid;
    }

    public Vector3D getGeodeticSurfaceNormal(Vector3D positionOnEllipsoid) {
        return (positionOnEllipsoid.multiplyComponents(oneOverRadiiSquared)).normalize();
    }

    public Vector3D getGeodeticSurfaceNormal(Geodetic3D geodetic) {

        /**
         * See at @Link https://vvvv.org/blog/polar-spherical-and-geographic-coordinates
         */

        double cosLatitude = Math.cos(geodetic.getφ());

        return new Vector3D(
                cosLatitude * Math.cos(geodetic.getλ()),
                cosLatitude * Math.sin(geodetic.getλ()),
                Math.sin(geodetic.getφ()));
    }


    public Vector3D scaleToGeodeticSurface(Vector3D position) {
        double beta = 1.0 / Math.sqrt(
                (position.x * position.x) * oneOverRadiiSquared.x +
                        (position.y * position.y) * oneOverRadiiSquared.y +
                        (position.z * position.z) * oneOverRadiiSquared.z);
        double n = new Vector3D(
                beta * position.x * oneOverRadiiSquared.x,
                beta * position.y * oneOverRadiiSquared.y,
                beta * position.z * oneOverRadiiSquared.z).getMagnitude();
        double alpha = (1.0 - beta) * (position.getMagnitude() / n);

        double x2 = position.x * position.x;
        double y2 = position.y * position.y;
        double z2 = position.z * position.z;

        double da = 0.0;
        double db = 0.0;
        double dc = 0.0;

        double s = 0.0;
        double dSdA = 1.0;

        do {
            alpha -= (s / dSdA);

            da = 1.0 + (alpha * oneOverRadiiSquared.x);
            db = 1.0 + (alpha * oneOverRadiiSquared.y);
            dc = 1.0 + (alpha * oneOverRadiiSquared.z);

            double da2 = da * da;
            double db2 = db * db;
            double dc2 = dc * dc;

            double da3 = da * da2;
            double db3 = db * db2;
            double dc3 = dc * dc2;

            s = x2 / (radiiSquared.x * da2) +
                    y2 / (radiiSquared.y * db2) +
                    z2 / (radiiSquared.z * dc2) - 1.0;

            dSdA = -2.0 *
                    (x2 / (radiiToTheFourth.x * da3) +
                            y2 / (radiiToTheFourth.y * db3) +
                            z2 / (radiiToTheFourth.z * dc3));
        }
        while (Math.abs(s) > 1e-10);

        return new Vector3D(
                position.x / da,
                position.y / db,
                position.z / dc);
    }

    public Vector3D getRadii() {
        return radii;
    }

    public Vector3D getOneOverRadiiSquared() {
        return oneOverRadiiSquared;
    }
}
