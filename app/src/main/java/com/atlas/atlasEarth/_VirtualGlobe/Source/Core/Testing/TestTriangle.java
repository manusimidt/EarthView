package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Testing;

import android.opengl.GLES31;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Matrices.MatricesUtility;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables.Renderable;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.NamesGL3x.VertexArrayNameGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.ShaderGL3x.ShaderProgramGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Mesh.Mesh;




public class TestTriangle extends Renderable {

    public TestTriangle() {
        super(new Vector3F(0, 0, 0), 0, 0, 0, 1);
    }


    public void setTriangle(Vector3F p1, Vector3F p2, Vector3F p3) {
        super.mesh = new Mesh(
                new int[]{
                        0, 1, 2
                }, new float[]{
                p3.x, p3.y, p3.z,
                p2.x, p2.y, p2.z,
                p1.x, p1.y, p1.z,
        },
                null,
                null,
                ByteFlags.GL_TRIANGLES);
    }

    @Override
    public void onCreate() {
        //Mesh is created in setTriangle
    }

    @Override
    public void render(ShaderProgramGL3x shaderProgram) {


        TestTriangleShaderProgram testTriangleShaderProgram = (TestTriangleShaderProgram) shaderProgram;

        testTriangleShaderProgram.loadTransformationMatrix(MatricesUtility.createModelMatrix(
                getPosition(),
                getRotX(),
                getRotY(),
                getRotZ(),
                getScale()));

        mesh.getVertexArray().bindAndEnableVAO();
        mesh.getIndicesBuffer().bind();

        GLES31.glDrawElements(mesh.getDrawModeGL3x(), mesh.getVertexCount(), mesh.getIndicesBuffer().getDataTypeGL3x(), 0);

        mesh.getIndicesBuffer().unbind();
        VertexArrayNameGL3x.unbindAndDisableVAO();
    }

}
