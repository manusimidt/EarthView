package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Shader;

import android.content.Context;
import android.renderscript.Matrix4f;

import com.atlas.atlasEarth.R;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.ShaderGL3x.ShaderProgramGL3x;


public class BackgroundShaderProgram extends ShaderProgramGL3x {

    private int location_projectionMatrix;
private int location_texture0;

    private static final int vsRawID = R.raw.background_vertex_shader;
    private static final int fsRawID = R.raw.background_fragment_shader;

    public BackgroundShaderProgram(Context context) {
        super(context, vsRawID, fsRawID);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(2, "texture");
    }

    @Override
    protected void getAllUniformLocations() {
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_texture0 = super.getUniformLocation("texture0");
    }

    public void loadProjectionMatrix(Matrix4f projection){
        super.loadMatrix(location_projectionMatrix, projection);
    }
    public void loadTextureIdentifier(){
        super.loadInt(location_texture0, 0);
    }


}
