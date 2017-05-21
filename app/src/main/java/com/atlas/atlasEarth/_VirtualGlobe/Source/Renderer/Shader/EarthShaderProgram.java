package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Shader;

import android.content.Context;
import android.renderscript.Matrix4f;

import com.atlas.atlasEarth.R;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Matrices.MatricesUtility;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Camera.Camera;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.ShaderGL3x.ShaderProgramGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Light;


public class EarthShaderProgram extends ShaderProgramGL3x {

    //Matrices
    private int location_modelMatrix;
    private int location_viewMatrix;
    private int location_projectionMatrix;

    //Lightning
    private int location_lightPosition;
    private int location_lightColor;
    private int location_fullLightOption;
    private int location_shineDamper;
    private int location_reflectivity;

    private int location_eyePosition;
    private int location_gridResolution;
    private int location_texture0;
    private int location_texture1;


    public EarthShaderProgram(Context context) {
        super(context, R.raw.earthmodel_vertex_shader, R.raw.earthmodel_fragment_shader, "EarthShaderProgram");
    }

    @Override
    protected String globalConstantsVS() {
        return "";
    }

    @Override
    protected String globalConstantsFS() {
        return "const float og_oneOverPi = " + (1 / Math.PI) + "; \n" +
                "const float og_oneOverTwoPi = " + (1 / (Math.PI * 2)) + "; \n";
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }

    @Override
    protected void getAllUniformLocations() {
        location_modelMatrix = super.getUniformLocation("modelMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");

        location_lightPosition = super.getUniformLocation("lightPosition");
        location_lightColor = super.getUniformLocation("lightColor");
        location_fullLightOption = super.getUniformLocation("enableFullLightning");
        location_reflectivity = super.getUniformLocation("reflectivity");
        location_shineDamper = super.getUniformLocation("shineDamper");

        location_eyePosition = super.getUniformLocation("eyePosition");
        location_gridResolution = super.getUniformLocation("gridResolution");
        location_texture0 = super.getUniformLocation("texture0");
        location_texture1 = super.getUniformLocation("texture1");


    }


    public void loadModelMatrix(Matrix4f transformation) {
        super.loadMatrix(location_modelMatrix, transformation);
    }

    public void loadViewMatrix(Camera camera) {
        super.loadMatrix(location_viewMatrix, MatricesUtility.createViewMatrix(camera));
        super.loadVector3F(location_eyePosition, camera.getPosition());
    }

    public void loadProjectionMatrix(Matrix4f projection) {
        super.loadMatrix(location_projectionMatrix, projection);
    }


    public void loadLight(Light light) {
        super.loadLight(location_lightPosition, location_lightColor, light);
    }

    public void loadFullLightningOption(boolean enabled) {
        super.loadBoolean(location_fullLightOption, enabled);
    }

    @Deprecated
    public void loadShineVariables(float damper, float reflectivity) {
        super.loadFloat(location_shineDamper, damper);
        super.loadFloat(location_reflectivity, reflectivity);
    }

    public void loadGridResolution(float resolution) {
        super.loadFloat(location_gridResolution, resolution);
    }

    public void loadTextureIdentifier() {
        super.loadInt(location_texture0, 0);
        super.loadInt(location_texture1, 1);
    }


}
