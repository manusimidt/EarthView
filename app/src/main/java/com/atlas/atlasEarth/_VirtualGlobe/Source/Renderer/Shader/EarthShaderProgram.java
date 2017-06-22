package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Shader;

import android.content.Context;
import android.renderscript.Matrix4f;

import com.atlas.atlasEarth.R;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector4F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Matrices.MatricesUtility;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Camera.Camera;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.ShaderGL3x.ShaderProgramGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Sun;


public class EarthShaderProgram extends ShaderProgramGL3x {

    //Matrices
    private int location_modelMatrix;
    private int location_viewMatrix;
    private int location_projectionMatrix;

    //Lightning
    private int location_sunPosition;
    private int location_sunlightColor;
    private int location_nightShiningColor;
    private int location_fullLightOption;
    private int location_diffuseSpecularAmbientShininess;

    private int location_eyePosition;
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

        location_sunPosition = super.getUniformLocation("sunPosition");
        location_sunlightColor = super.getUniformLocation("sunlightColor");
        location_nightShiningColor = super.getUniformLocation("sunNightShininessColor");
        location_fullLightOption = super.getUniformLocation("enableFullLightning");
        location_diffuseSpecularAmbientShininess = super.getUniformLocation("diffuseSpecularAmbientShininess");

        location_eyePosition = super.getUniformLocation("eyePosition");
        location_texture0 = super.getUniformLocation("texture0");
        location_texture1 = super.getUniformLocation("texture1");


    }


    public void loadModelMatrix(Matrix4f transformation) {
        super.loadMatrix(location_modelMatrix, transformation);
    }

    public void loadViewMatrix(Camera camera) {
        // TODO: 6/17/2017 Don't create the view Matrix every time new
        super.loadMatrix(location_viewMatrix, MatricesUtility.createViewMatrix(camera));
        super.loadVector3F(location_eyePosition, camera.getPosition());
    }

    public void loadProjectionMatrix(Matrix4f projection) {
        super.loadMatrix(location_projectionMatrix, projection);
    }


    public void loadSun(Sun sun) {
        super.loadSun(location_sunPosition, location_sunlightColor,location_nightShiningColor, sun);
    }



    public void loadFullLightningOption(boolean enabled) {
        super.loadBoolean(location_fullLightOption, enabled);
    }


    public void loadShineVariables(float diffuse, float specular, float ambient, float shininess) {
        super.loadVector4F(location_diffuseSpecularAmbientShininess, new Vector4F(diffuse,specular,ambient,shininess));
    }

    public void loadTextureIdentifier() {
        super.loadInt(location_texture0, 0);
        super.loadInt(location_texture1, 1);
    }


}
