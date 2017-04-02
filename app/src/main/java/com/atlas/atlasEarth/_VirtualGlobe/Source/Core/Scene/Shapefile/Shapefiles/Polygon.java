package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Shapefile.Shapefiles;

import android.content.Context;
import android.opengl.GLES31;
import android.util.Log;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector2D;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3D;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.CSConverter;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.EllipsoidTangentPlane;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.cartesianCS.Indices.TriangleIndicesInt;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.geographicCS.Ellipsoid;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Matrices.MatricesUtility;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Polygons.EarClippingOnEllipsoid;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Polygons.TriangleMeshSubdivision;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Polygons.TriangleMeshSubdivisionResult;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Rendables.Renderable;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.DrawState;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.NamesGL3x.VertexArrayNameGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.ShaderGL3x.ShaderProgramGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Mesh.Mesh;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Mesh.VertexAttributeCollection;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Shader.PolygonShaderProgram;

import java.util.Collections;
import java.util.List;


public class Polygon extends Renderable{

    private int color;
    private DrawState drawState;
    private byte primitiveType;




    public Polygon(Context context, Ellipsoid globeShape, List<Vector3D> positions)
    {
        super(new Vector3F(0,0,0),90, 0, 180, 1);

        //
        // Pipeline Stage 1a:  Clean up - Remove duplicate positions
        //
// TODO: 3/30/2017 cleanPositions are ACCURATE!!
        List<Vector3D> cleanPositions = SimplePolygonAlgorithms.cleanUp(positions);

        //
        // Pipeline Stage 1b:  Clean up - Swap winding order
        //
        EllipsoidTangentPlane plane = new EllipsoidTangentPlane(globeShape, cleanPositions);
        List<Vector2D> positionsOnPlane = plane.ComputePositionsOnPlane(cleanPositions);



        if (SimplePolygonAlgorithms.ComputeWindingOrder(positionsOnPlane) == ByteFlags.CLOCKWISE) {
            Collections.reverse(cleanPositions);
        }

        //
        // Pipeline Stage 2:  Triangulate
        //

        List<TriangleIndicesInt> indices = EarClippingOnEllipsoid.Triangulate(cleanPositions);


      //
      // Pipeline Stage 3:  Subdivide
      //
      TriangleMeshSubdivisionResult result = TriangleMeshSubdivision.Compute(cleanPositions, indices, CSConverter.toRadians(1));

      //
      // Pipeline Stage 4:  Set height
      //

      for(int i = 0; i<result.getPositions().size(); i++){
          result.getPositions().set(i, globeShape.scaleToGeodeticSurface(result.getPositions().get(i)));
      }

        mesh.addVertexAttributes(new VertexAttributeCollection(Vector3D.toVector3FArray(result.getPositions()), null, null));
        mesh.addTriangleIndicesInt(result.getIndices());


    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public void render(ShaderProgramGL3x shaderProgram) {
        PolygonShaderProgram polygonShaderProgram = (PolygonShaderProgram) shaderProgram;
        mesh.getVertexArray().bindAndEnableVAO();
        polygonShaderProgram.loadTransformationMatrix(MatricesUtility.createTransformationMatrix(
                getPosition(),
                getRotX(),
                getRotY(),
                getRotZ(),
                getScale()));

        GLES31.glDrawElements(GLES31.GL_TRIANGLES, getMesh().elementsCount(), GLES31.GL_UNSIGNED_SHORT, getMesh().getIndicesBufferInt());

        VertexArrayNameGL3x.unbindAndDisableVAO();
    }
}
