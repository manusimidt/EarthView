package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Shader;

import android.content.Context;
import android.renderscript.Matrix4f;

import com.atlas.atlasEarth.R;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Matrices.Matrix4x2f;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector4F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Matrices.MatricesUtility;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Camera.Camera;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.ShaderGL3x.ShaderProgramGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Sun;


public class RayCastedEarthShaderProgram extends ShaderProgramGL3x {

    //Matrices
    private int location_viewMatrix;
    private int location_projectionMatrix;

    //Lightning
    private int location_sunPosition;
    private int location_sunlightColor;
    private int location_fullLightOption;

    private int location_eyePosition;
    private int location_texture0;
    private int location_texture1;
    private int location_globeOverRadiiSquared;
    private int location_eyePositionSquared;
    private int location_diffuseSpecularAmbientLighting;
    private int location_useAverageDepth;
    private int location_modelZToClipCoordinates;


    public RayCastedEarthShaderProgram(Context context) {
        super(context, R.raw.raycasted_globe_vertex_shader, R.raw.raycasted_globe_fragment_shader, "RayCastedEarthShaderProgram");
    }

    @Override
    protected String globalConstantsVS() {
        return "";
    }

    @Override
    protected String globalConstantsFS() {
        return "const float og_oneOverPi = 0.3183098861837907; \n" +
                "const float og_oneOverTwoPi = 0.15915494309189535; \n";
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }

    @Override
    protected void getAllUniformLocations() {
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");

        location_sunPosition = super.getUniformLocation("sunPosition");
        location_sunlightColor = super.getUniformLocation("sunlightColor");
        location_fullLightOption = super.getUniformLocation("enableFullLightning");

        location_eyePosition = super.getUniformLocation("eyePosition");
        location_texture0 = super.getUniformLocation("texture0");
        location_texture1 = super.getUniformLocation("texture1");
        location_globeOverRadiiSquared = super.getUniformLocation("globeOneOverRadiiSquared");
        location_eyePositionSquared = super.getUniformLocation("eyePositionSquared");
        location_diffuseSpecularAmbientLighting = super.getUniformLocation("diffuseSpecularAmbientShininess");
        location_useAverageDepth = super.getUniformLocation("useAverageDepth");
        location_modelZToClipCoordinates = super.getUniformLocation("modelZToClipCoordinates");
    }


    public void loadViewMatrix(Camera camera) {
        super.loadMatrix(location_viewMatrix, MatricesUtility.createViewMatrix(camera));
        super.loadVector3F(location_eyePosition, camera.getPosition());
        super.loadVector3F(location_eyePositionSquared, camera.getPosition().multiplyComponents(camera.getPosition()));
    }

    public void loadProjectionMatrix(Matrix4f projection) {
        super.loadMatrix(location_projectionMatrix, projection);
    }

    public void loadLight(Sun sun) {
        super.loadSun(location_sunPosition, location_sunlightColor,-1, sun);
    }

    public void loadDiffuseSpecularAmbientLighting(Vector4F value) {
        super.loadVector4F(location_diffuseSpecularAmbientLighting, value);
    }

    public void loadFullLightningOption(boolean enabled) {
        super.loadBoolean(location_fullLightOption, enabled);
    }

    public void loadUseAverageDepth(boolean value) {
        super.loadBoolean(location_useAverageDepth, value);
    }

    public void loadModelZtoClipCoordinates(Matrix4x2f value) {
        super.loadMatrix4x2(location_modelZToClipCoordinates, value);
    }

    public void loadTextureIdentifier() {
        super.loadInt(location_texture0, 0);
        super.loadInt(location_texture1, 1);
    }

    public void loadGlobeOverRadiiSquaredValue(Vector3F value) {
        super.loadVector3F(location_globeOverRadiiSquared, value);
    }
}
