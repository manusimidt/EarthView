package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Rendables;


import android.content.Context;
import android.opengl.GLES31;

import com.atlas.atlasEarth.R;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.TriangleIndices.TriangleIndicesShort;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector2F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.ShaderGL3x.ShaderProgramGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Mesh.VertexAttributeCollection;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Shader.BackgroundShaderProgram;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Texture;

import java.util.ArrayList;
import java.util.List;

public class SpaceBackground extends Renderable {


    public SpaceBackground(Context context) {
        super(new Vector3F(0, 0, -20), 0, 0, 0, 1);
        super.setTexture(new Texture(R.drawable.space2));
    }


    @Override
    public void onCreate() {
        List<Vector3F> positions = new ArrayList<>(4);
        List<Vector2F> textureCoords = new ArrayList<>(4);

        positions.add(new Vector3F(-1, 1, 0));
        textureCoords.add(new Vector2F(0, 0));

        positions.add(new Vector3F(-1, -1, 0));
        textureCoords.add(new Vector2F(0, 1));

        positions.add(new Vector3F(1, -1, 0));
        textureCoords.add(new Vector2F(1, 1));

        positions.add(new Vector3F(1, 1, 0));
        textureCoords.add(new Vector2F(1, 0));

        mesh.addVertexAttributes(new VertexAttributeCollection(positions, null, textureCoords));

        List<TriangleIndicesShort> triangles = new ArrayList<>(2);
        triangles.add(new TriangleIndicesShort((short) 0, (short) 1, (short) 2));
        triangles.add(new TriangleIndicesShort((short) 0, (short) 2, (short) 3));
        mesh.addTriangles(triangles);
    }

    @Override
    public void render(ShaderProgramGL3x shaderProgram) {
        BackgroundShaderProgram backgroundShaderProgram = (BackgroundShaderProgram) shaderProgram;
        mesh.getVertexArray().bindAndEnableVAO();
        backgroundShaderProgram.loadTextureIdentifier();

        GLES31.glActiveTexture(GLES31.GL_TEXTURE0);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D, getTexture0().getTextureID());

        mesh.getIndicesBuffer().bind();
        GLES31.glDrawElements(GLES31.GL_TRIANGLES, mesh.getVertexCount(), mesh.getIndicesBuffer().getDataType(), 0);
        mesh.getIndicesBuffer().unbind();
    }


}
