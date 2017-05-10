package com.atlas.atlasEarth._VirtualGlobe.Source.Core;

/**
 * Created by Jonas on 5/5/2017.
 */

public class MathTools {

    public static float radToDeg(float rad) {
        return (float) (360 * rad / (Math.PI * 2));
    }
    public static float degToRad(float deg) {
        return (float) (Math.PI * 2 * deg) / 360;
    }
}



