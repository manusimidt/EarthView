package com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors;

/**
 * Class Representing a four dimensional vector with float'S (especially for homogeneous coordinates)
 */

public class Vector4F {
    public float x, y, z, w;


    public Vector4F(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
}
