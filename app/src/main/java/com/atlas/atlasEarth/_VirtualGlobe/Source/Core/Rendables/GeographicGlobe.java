package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Rendables;

import android.opengl.GLES20;
import android.opengl.GLES31;

import com.atlas.atlasEarth._VirtualGlobe.EarthViewOptions;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.geographicCS.Ellipsoid;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Matrices.MatricesUtility;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.NamesGL3x.VertexArrayNameGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.ShaderGL3x.ShaderProgramGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Shader.EarthShaderProgram;


class GeographicGlobe extends Renderable {

    private Ellipsoid ellipsoid;
    GeographicGlobe(Vector3F position, float rotX, float rotY, float rotZ, float scale) {
        super(position, rotX, rotY, rotZ, scale);
    }

    @Override
    public void render(ShaderProgramGL3x shaderProgram) {

        EarthShaderProgram earthShaderProgram = (EarthShaderProgram) shaderProgram;
        mesh.getVertexArray().bindAndEnableVAO();
        earthShaderProgram.loadShineVariables(getTexture0().getShineDamper(), getTexture0().getReflectivity());
        earthShaderProgram.loadTextureIdentifier();
        earthShaderProgram.loadFullLightningOption(EarthViewOptions.isFullLightning());

        GLES31.glActiveTexture(GLES31.GL_TEXTURE0);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D, getTexture0().getTextureID());

        GLES31.glActiveTexture(GLES20.GL_TEXTURE1);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D, getTexture1().getTextureID());

        earthShaderProgram.loadTransformationMatrix(MatricesUtility.createTransformationMatrix(
                getPosition(),
                getRotX(),
                getRotY(),
                getRotZ(),
                getScale()));

        GLES31.glDrawElements(GLES31.GL_TRIANGLES, getMesh().elementsCount(), GLES31.GL_UNSIGNED_SHORT, getMesh().getIndicesBufferShort());

        VertexArrayNameGL3x.unbindAndDisableVAO();
    }


    public void setShape(Ellipsoid ellipsoid){
        this.ellipsoid = ellipsoid;
    }

}
