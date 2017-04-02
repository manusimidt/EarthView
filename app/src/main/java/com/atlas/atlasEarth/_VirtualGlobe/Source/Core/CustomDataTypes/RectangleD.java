package com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector2D;



public class RectangleD {

    private Vector2D lowerLeft;
    private Vector2D upperRight;

    public RectangleD(Vector2D lowerLeft, Vector2D upperRight) {
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
    }

    public Vector2D getLowerLeft() {
        return lowerLeft;
    }
    public Vector2D getUpperRight() {
        return upperRight;
    }

    @Override
    public String toString() {
        return "Lower Left: " +lowerLeft.toString() + "Upper Right: "+ upperRight.toString();
    }





}
