package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Shapefile.Shapefiles;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector2D;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3D;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.CSConverter;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.geographicCS.Ellipsoid;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.geographicCS.Geodetic3D;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Rendables.OutlinedPolylineTexture;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Shapefile.ShapeType;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Shapefile.Shapefile;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Shapefile.ShapefileAppearance;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Shapefile.Shapes.PolygonShape;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Shapefile.Shapes.Shape;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Shapefile.Shapes.ShapePart;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class PolygonShapefile {


    private List<Polygon> polygons;
    private OutlinedPolylineTexture polyline;


    public PolygonShapefile(Shapefile shapefile, Context context, Ellipsoid globeShape, ShapefileAppearance appearance) throws InvalidObjectException {
        if (shapefile == null || globeShape == null || appearance == null) {
            throw new IllegalArgumentException("Value can't be null!");
        }

        polyline = new OutlinedPolylineTexture();
        polygons = new ArrayList<>();

        List<Integer> indices = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();

        Random r = new Random(3);


        for (Shape shape : shapefile.getShapes()) {

            boolean test = shape.getShapeType() != ShapeType.Polygon;
            if (test) {
                throw new InvalidObjectException("The type of an individual shape does not match the Shapefile type.");
            }
            PolygonShape polygonShape = (PolygonShape) shape;

            List<Vector3D> positions = new ArrayList<>();

            for (int j = 0; j < polygonShape.size(); ++j) {
                int color = Color.argb(127, r.nextInt(256), r.nextInt(256), r.nextInt(256));

                positions.clear();

                //Completely Accurate
                ShapePart part = polygonShape.getPart(j);

                for (int i = 0; i < part.size(); ++i) {
                    Vector2D point = part.getPosition(i);

                    //Completely Accurate
                    Geodetic3D pointInRadiant = CSConverter.toRadians(new Geodetic3D(point.x, point.y));
                    Vector3D pointInWGS84 = globeShape.ToVector3D(pointInRadiant);

                    positions.add(pointInWGS84);

                    //
                    // For polyline
                    //
                    //positions.add(globeShape.ToVector3D(CSConverter.toRadians(new Geodetic3D(point.x, point.y))));
                    colors.add(color);

                    if (i != 0) {
                        indices.add(positions.size() - 2);
                        indices.add(positions.size() - 1);
                    }
                }

                try {
                    Polygon p = new Polygon(context, globeShape, positions);
                    p.setColor(color);
                    polygons.add(p);
                } catch (ArrayIndexOutOfBoundsException e) // Not enough positions after cleaning
                {
                    e.printStackTrace();
                }

            }
         if(polygons.size()>=5){
             break;
         }

        }


        Log.d("debug", "Ready");

    }

    public List<Polygon> getPolygons() {
        return polygons;
    }
}


