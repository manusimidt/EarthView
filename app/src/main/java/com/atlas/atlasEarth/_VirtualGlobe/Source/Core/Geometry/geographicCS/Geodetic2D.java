package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.geographicCS;


public class Geodetic2D {

    private double λ;
    private double φ;


    /**
     * Class for representing a two dimensional geodetic point IN RADIANT'S!
     * @param λ corresponds to longitude
     * @param φ corresponds to latitude
     */
    public Geodetic2D(double φ, double λ) {
        this.φ = φ;
        this.λ = λ;
    }
    public Geodetic2D(Geodetic3D geodetic3D) {
        λ = geodetic3D.getλ();
        φ = geodetic3D.getφ();
    }


    public boolean equalsEpsilon(Geodetic2D other, double epsilon) {
        return (Math.abs(λ - other.λ) <= epsilon) &&
                (Math.abs(φ - other.φ) <= epsilon);
    }
    public boolean equals(Geodetic2D other) {
        return λ == other.λ && φ == other.getφ();
    }


    /**
     * Getter & Setter
     */
    public double getλ() {
        return λ;
    }
    public double getφ() {
        return φ;
    }
}

