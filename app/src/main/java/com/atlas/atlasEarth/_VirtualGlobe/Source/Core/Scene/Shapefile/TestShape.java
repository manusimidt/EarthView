package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Shapefile;

import android.opengl.GLES20;
import android.opengl.GLES31;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3D;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.cartesianCS.Indices.TriangleIndicesShort;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Matrices.MatricesUtility;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Rendables.Renderable;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.NamesGL3x.VertexArrayNameGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.ShaderGL3x.ShaderProgramGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Mesh.VertexAttributeCollection;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Shader.ShapefileShaderProgram;

import java.util.ArrayList;
import java.util.List;


public class TestShape extends Renderable {

    public TestShape() {
        super(new Vector3F(0,0,0), 0, 90, 0, 1);
        double testchange = test(5);

    }

    private double test(int i) {
        return Math.pow(i,i);
    }


    public void setRectangle(Vector3D p1, Vector3D p2, Vector3D p3){
        List<Vector3F> positions = new ArrayList<>(3);
        List<TriangleIndicesShort> triangles = new ArrayList<>(1);
        positions.add(p3.toVector3F());
        positions.add(p2.toVector3F());
        positions.add(p1.toVector3F());
        triangles.add(new TriangleIndicesShort((short)0,(short)1,(short)2));

        mesh.addVertexAttributes(new VertexAttributeCollection(positions));
        mesh.addTriangleIndicesShort(triangles);

    }

    @Override
    public void render(ShaderProgramGL3x shaderProgram) {
       ShapefileShaderProgram shapefileShaderProgram = (ShapefileShaderProgram) shaderProgram;

       getMesh().getVertexArray().bindAndEnableVAO();

       if(getTexture0() != null) {
           GLES31.glActiveTexture(GLES31.GL_TEXTURE0);
           GLES31.glBindTexture(GLES31.GL_TEXTURE_2D, getTexture0().getTextureID());
       }
       if(getTexture1() != null) {
           GLES31.glActiveTexture(GLES20.GL_TEXTURE1);
           GLES31.glBindTexture(GLES31.GL_TEXTURE_2D, getTexture1().getTextureID());
       }

       shapefileShaderProgram.loadTransformationMatrix(MatricesUtility.createTransformationMatrix(getPosition(), getRotX(), getRotY(), getRotZ(), getScale()));
       GLES31.glDrawElements(GLES31.GL_TRIANGLES, getMesh().elementsCount(), GLES31.GL_UNSIGNED_SHORT, getMesh().getIndicesBufferShort());
       VertexArrayNameGL3x.unbindAndDisableVAO();
    }
}
