package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Rendables;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES31;

import com.atlas.atlasEarth.R;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.ExternalMeshData.ObjLoader;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Matrices.MatricesUtility;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.NamesGL3x.VertexArrayNameGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.ShaderGL3x.ShaderProgramGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Shader.PostShaderProgram;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Texture;


public class Post extends Renderable {

    private Context context;

    public Post(Vector3F position, Bitmap image, String name, String date, Context context) {
        super(position, 0, 0, 0, 0.03f);
        super.setTexture(new Texture(image));
        this.context = context;
    }


    @Override
    public void onCreate() {
        super.mesh = ObjLoader.loadOBJ(R.raw.text_model_source, context);
    }

    @Override
    public void render(ShaderProgramGL3x shaderProgram) {
        PostShaderProgram postShaderProgram = (PostShaderProgram) shaderProgram;

        mesh.getVertexArray().bindAndEnableVAO();
        postShaderProgram.loadTextureIdentifier();

        GLES31.glActiveTexture(GLES31.GL_TEXTURE0);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D, getTexture0().getTextureID());

        postShaderProgram.loadTransformationMatrix(MatricesUtility.createTransformationMatrix(
                getPosition(),
                getRotX(),
                getRotY(),
                getRotZ(),
                getScale()));

        mesh.getIndicesBuffer().bind();
        GLES31.glDrawElements(GLES31.GL_TRIANGLES, mesh.getVertexCount(), mesh.getIndicesBuffer().getDataType(), 0);
        mesh.getIndicesBuffer().unbind();
        VertexArrayNameGL3x.unbindAndDisableVAO();
    }


}
