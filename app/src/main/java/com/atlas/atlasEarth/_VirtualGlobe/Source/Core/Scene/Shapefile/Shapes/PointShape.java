package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Shapefile.Shapes;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector2D;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Shapefile.ShapeType;


public class PointShape extends Shape {

    private Vector2D position;

    public PointShape(int recordNumber, Vector2D position) {
        super(recordNumber, ShapeType.Point);
        this.position = position;
    }

    public Vector2D getPosition() {
        return position;
    }
}
