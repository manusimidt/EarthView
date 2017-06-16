package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables.Shapefiles;

import android.content.Context;
import android.content.res.Resources;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.geographicCS.Ellipsoid;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables.Renderable;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Shapefile.ShapeType;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Shapefile.Shapefile;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Shapefile.ShapefileAppearance;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.ShaderGL3x.ShaderProgramGL3x;

import java.io.InvalidObjectException;


public class ShapefileRenderable extends Renderable {

    private Object shapefileGraphic;
    private byte currentShapefile;


    public ShapefileRenderable(int rawFileID, Context context, Ellipsoid globeShape, ShapefileAppearance appearance) throws InvalidObjectException, Resources.NotFoundException {
        super(new Vector3F(0, 0, 0), 0, 90, 0, 1);
    //z 45, z90
        Shapefile shapefile = new Shapefile(rawFileID, context);
        currentShapefile = shapefile.getShapeType().getShapeType();
        switch (currentShapefile) {
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
                shapefileGraphic = new PolygonShapefile(shapefile, globeShape, appearance);
                super.mesh.setFrontFaceWindingOrder(ByteFlags.COUNTERCLOCKWISE);
                break;
            default:
                throw new InvalidObjectException("Rendering is not supported for " + shapefile.getShapeType().toString() + " shapefiles.");
        }


    }


    @Override
    public void render(ShaderProgramGL3x shaderProgram) {
        switch (currentShapefile) {
            case ShapeType.Point:
                break;
            case ShapeType.Polyline:
                break;
            case ShapeType.Polygon:
                ((PolygonShapefile) shapefileGraphic).render(
                        shaderProgram,
                        super.getPosition(),
                        super.getRotX(),
                        super.getRotY(),
                        super.getRotZ(),
                        super.getScale());
                break;
            default:
                throw new IllegalArgumentException("Rendering is not supported for " + currentShapefile + " shapefiles.");


        }
    }

    @Override
    public void onCreate() {

    }
}

