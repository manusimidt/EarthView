package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Shader;

import android.content.Context;
import android.renderscript.Matrix4f;

import com.atlas.atlasEarth.R;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Camera;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Matrices.MatricesUtility;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.ShaderGL3x.ShaderProgramGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Light;


public class EarthShaderProgram extends ShaderProgramGL3x {
    private int location_transMat;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightPosition;
    private int location_lightColor;
    private int location_gridResolution;
    private int location_texture0;
    private int location_texture1;
    private int location_fullLightOption;



    public EarthShaderProgram(Context context) {
        super(context, R.raw.earthmodel_vertex_shader, R.raw.earthmodel_fragment_shader);


    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "normal");
        super.bindAttribute(2, "texture");
    }

    @Override
    protected void getAllUniformLocations() {
        location_transMat = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_lightPosition = super.getUniformLocation("lightPosition");
        location_lightColor = super.getUniformLocation("lightColor");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_gridResolution = super.getUniformLocation("gridResolution");
        location_texture0 = super.getUniformLocation("texture0");
        location_texture1 = super.getUniformLocation("texture1");
        location_fullLightOption = super.getUniformLocation("enableFullLightning");

    }

    public void loadTextureIdentifier(){
        super.loadInt(location_texture0, 0);
        super.loadInt(location_texture1, 1);
    }

    public void loadTransformationMatrix(Matrix4f transformation) {
        super.loadMatrix(location_transMat, transformation);
    }

    public void loadProjectionMatrix(Matrix4f projection) {
        super.loadMatrix(location_projectionMatrix, projection);
    }


    public void loadViewMatrix(Camera camera) {
        super.loadMatrix(location_viewMatrix, MatricesUtility.createViewMatrix(camera));
    }

    public void loadLight(Light light) {
        super.loadLight(location_lightPosition, location_lightColor, light);
    }

    public void loadGridResolution(float resolution) {
        super.loadFloat(location_gridResolution, resolution);
    }

    public void loadFullLightningOption(boolean enabled){
        super.loadBoolean(location_fullLightOption, enabled);
    }

    public void loadShineVariables(float damper, float reflectivity) {
        //super.loadFloat(location_shineDamper, damper);
        //super.loadFloat(location_reflectivity, reflectivity);
    }

}
