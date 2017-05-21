package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Shader;

import android.content.Context;
import android.renderscript.Matrix4f;

import com.atlas.atlasEarth.R;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Matrices.MatricesUtility;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Camera.Camera;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.ShaderGL3x.ShaderProgramGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Light;


public class PostShaderProgram extends ShaderProgramGL3x {

    //Matrices
    private int location_modelMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;

    //Lightning
    private int location_lightPosition;
    private int location_lightColor;

    private int location_texture0;


    public PostShaderProgram(Context context) {
        super(context, R.raw.post_vertex_shader, R.raw.post_fragment_shader, "PostShaderProgram");
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
        super.bindAttribute(1, "normal");
        super.bindAttribute(2, "texCoord");
    }

    @Override
    protected void getAllUniformLocations() {
        location_modelMatrix = super.getUniformLocation("modelMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");

        location_lightPosition = super.getUniformLocation("lightPosition");
        location_lightColor = super.getUniformLocation("lightColor");

        location_texture0 = super.getUniformLocation("texture0");
    }

    public void loadModelMatrix(Matrix4f transformation) {
        super.loadMatrix(location_modelMatrix, transformation);
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

    public void loadTextureIdentifier() {
        super.loadInt(location_texture0, 0);
    }
}
