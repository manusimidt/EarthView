package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.geographicCS;


public class Geodetic3D {

    private double λ;
    private double φ;
    private double h;


    /**
     * Class for representing a three dimensional geodetic point IN RADIANT'S!
     *
     * @param λ      corresponds to longitude
     * @param φ      corresponds to latitude
     * @param height height of the Geodetic
     */
    public Geodetic3D(double φ, double λ, double height) {
        this.φ = φ;
        this.λ = λ;
        this.h = height;
    }

    public Geodetic3D(double φ, double λ) {
        this.φ = φ;
        this.λ = λ;
        h = 0;
    }

    public Geodetic3D(Geodetic2D geodetic2D, double height) {
        φ = geodetic2D.getφ();
        λ = geodetic2D.getλ();
        this.h = height;
    }


    public double getλ() {
        return λ;
    }

    public double getφ() {
        return φ;
    }

    public double getHeight() {
        return h;
    }
}
