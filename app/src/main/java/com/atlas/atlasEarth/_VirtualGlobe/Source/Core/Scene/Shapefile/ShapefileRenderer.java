package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Shapefile;

import android.content.Context;
import android.content.res.Resources;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.geographicCS.Ellipsoid;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Shapefile.Shapefiles.PolygonShapefile;

import java.io.InvalidObjectException;


public class ShapefileRenderer {
    PolygonShapefile polygonShapefile;
    public ShapefileRenderer(int rawFileID, Context context, Ellipsoid globeShape, ShapefileAppearance appearance) throws InvalidObjectException, Resources.NotFoundException {

        Shapefile shapefile = new Shapefile(rawFileID, context);
        switch (shapefile.getShapeType().getShapeType()) {
            case ShapeType.Point:
                //PointShapefile pointShapefile = new PointShapefile(shapefile, context, globeShape, appearance);
                //pointShapefile.DepthWrite = false;
                //_shapefileGraphics = pointShapefile;
                break;
            /*case ShapeType.Polyline:
                PolylineShapefile polylineShapefile = new PolylineShapefile(shapefile, context, globeShape, appearance);
               // polylineShapefile.DepthWrite = false;
               // _shapefileGraphics = polylineShapefile;
                break;*/
            case ShapeType.Polygon:
                 polygonShapefile = new PolygonShapefile(shapefile, context, globeShape, appearance);
                break;
            default:
                throw new InvalidObjectException("Rendering is not supported for " + shapefile.getShapeType().toString() + " shapefiles.");
        }


    }

    public PolygonShapefile getPolygonShapefile() {
        return polygonShapefile;
    }
}

