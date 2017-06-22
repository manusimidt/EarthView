package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables.EarthModel;

import android.opengl.GLES20;
import android.opengl.GLES31;

import com.atlas.atlasEarth._VirtualGlobe.EarthViewOptions;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.cartesianCS.Tessellation.SubdivisionSphereTessellator;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables.Renderable;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.ShaderGL3x.ShaderProgramGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Shader.EarthShaderProgram;


class GeographicGlobe extends Renderable {



    GeographicGlobe(Vector3F position, float rotX, float rotY, float rotZ, float scale) {
        super(position, rotX, rotY, rotZ, scale);

    }

    @Override
    public void onCreate() {
        super.mesh = SubdivisionSphereTessellator.compute(5);
    }

    @Override
    public void render(ShaderProgramGL3x shaderProgram) {

        EarthShaderProgram earthShaderProgram = (EarthShaderProgram) shaderProgram;

        earthShaderProgram.loadTextureIdentifier();
        earthShaderProgram.loadFullLightningOption(EarthViewOptions.isFullLightning());

        GLES31.glActiveTexture(GLES31.GL_TEXTURE0);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D, getTexture0().getTextureIDGl3x());

        GLES31.glActiveTexture(GLES20.GL_TEXTURE1);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D, getTexture1().getTextureIDGl3x());

        earthShaderProgram.loadModelMatrix(getModelMatrix());

        super.mesh.draw();
    }





}
