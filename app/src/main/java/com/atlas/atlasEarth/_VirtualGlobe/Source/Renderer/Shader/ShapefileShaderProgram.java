package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Shader;

import android.content.Context;
import android.renderscript.Matrix4f;

import com.atlas.atlasEarth.R;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector4F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Matrices.MatricesUtility;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Camera.Camera;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.ShaderGL3x.ShaderProgramGL3x;


public class ShapefileShaderProgram extends ShaderProgramGL3x {

    //Matrices
    private int location_modelMatrix;
    private int location_viewMatrix;
    private int location_projectionMatrix;

    private int location_color;


    public ShapefileShaderProgram(Context context) {
        super(context, R.raw.shapefile_vertex_shader, R.raw.shapefile_fragment_shader, "ShapefileShaderProgram");
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
    }

    @Override
    protected void getAllUniformLocations() {
        location_modelMatrix = super.getUniformLocation("modelMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");

        location_color = super.getUniformLocation("color");
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


    public void loadColor(Vector4F color) {
        super.loadVector4F(location_color, color);
    }

}
