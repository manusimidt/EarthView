package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Shader;

import android.content.Context;
import android.renderscript.Matrix4f;

import com.atlas.atlasEarth.R;
import com.atlas.atlasEarth._VirtualGlobe.AdvancedEarthViewOptions;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Matrices.MatricesUtility;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Camera.Camera;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.ShaderGL3x.ShaderProgramGL3x;


public class SkyBoxShaderProgram extends ShaderProgramGL3x {

    //Matrices
    private int location_modelMatrix;
    private int location_viewMatrix;
    private int location_projectionMatrix;

    private int location_texture0;
    private int location_universeIntensity;


    public SkyBoxShaderProgram(Context context) {
        super(context, R.raw.skybox_vertex_shader, R.raw.skybox_fragment_shader, "SkyBoxShaderProgram");
    }

    @Override
    protected String globalConstantsVS() {
        return "";
    }

    @Override
    protected String globalConstantsFS() {
        return "";
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(2, "texCoord");
    }

    @Override
    protected void getAllUniformLocations() {
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_modelMatrix = super.getUniformLocation("modelMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");

        location_texture0 = super.getUniformLocation("texture0");
        location_universeIntensity = super.getUniformLocation("universeIntensity");
    }


    public void loadModelMatrix(Matrix4f transformation) {
        super.loadMatrix(location_modelMatrix, transformation);
    }

    public void loadViewMatrix(Camera camera) {
        super.loadMatrix(location_viewMatrix, MatricesUtility.createViewMatrix(camera));
    }

    public void loadProjectionMatrix(Matrix4f projection) {
        super.loadMatrix(location_projectionMatrix, projection);
    }


    public void loadTextureIdentifier() {
        super.loadInt(location_texture0, 0);
    }

    public void loadUniverseIntensity(){
        super.loadVector3F(location_universeIntensity, AdvancedEarthViewOptions.universeIntensity);
    }


}
