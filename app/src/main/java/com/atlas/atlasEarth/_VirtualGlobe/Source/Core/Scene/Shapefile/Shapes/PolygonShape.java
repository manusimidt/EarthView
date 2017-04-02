package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Shapefile.Shapes;


import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.RectangleD;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector2D;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Shapefile.ShapeType;

public class PolygonShape extends Shape {

    private RectangleD extent;
    private ShapePart[] parts;


    public PolygonShape(int recordNumber, RectangleD extent, int[] parts, Vector2D[] positions) {
        super(recordNumber, ShapeType.Polygon);
        this.extent = extent;
        this.parts = new ShapePart[parts.length];


        for (int i = 0; i < parts.length; ++i) {
            int count = ((i == parts.length - 1) ? positions.length : parts[i + 1]) - parts[i];

            this.parts[i] = new ShapePart(positions, parts[i], count);
        }

    }

    public RectangleD getExtent() {
        return extent;
    }

    public ShapePart getPart(int index) {
        return parts[index];
    }

    public int size(){
        return parts.length;
    }


}
