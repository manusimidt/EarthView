package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.ShaderGL3x;

import android.content.Context;
import android.opengl.GLES31;
import android.renderscript.Matrix4f;
import android.util.Log;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Matrices.Matrix4x2f;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector4F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.NamesGL3x.ShaderProgramNameGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Sun;


/**
 * Class representing a OpenGL Shader Program
 */
public abstract class ShaderProgramGL3x extends ShaderProgramNameGL3x {

    private int vsID, fsID;
    private String name;

    /**
     * @param context Context of the calling class (needed for reading from .raw resources)
     * @param vsRawID resource id of the Vertex shader
     * @param fsRawID resource id of the Fragment shader
     * @param name    name of the shader program for debugging
     */

    public ShaderProgramGL3x(Context context, int vsRawID, int fsRawID, String name) {
        super();
        this.name = name;

        vsID = new ShaderObjectGL3x(context, GLES31.GL_VERTEX_SHADER, vsRawID, globalConstantsVS(), name).getShaderID();
        fsID = new ShaderObjectGL3x(context, GLES31.GL_FRAGMENT_SHADER, fsRawID, globalConstantsFS(), name).getShaderID();

        if (vsID != -1 && fsID != -1) {
            GLES31.glAttachShader(super.getProgramID(), vsID);
            GLES31.glAttachShader(super.getProgramID(), fsID);
            bindAttributes();

            GLES31.glLinkProgram(super.getProgramID());

            int[] linkStatus = new int[1];
            GLES31.glGetProgramiv(super.getProgramID(), GLES31.GL_LINK_STATUS, linkStatus, 0);

            if (linkStatus[0] == GLES31.GL_FALSE) {
                Log.e("ShaderProgram", "Linking of " + name + " has FAILED!! " + "CompileLog: \n" + GLES31.glGetProgramInfoLog(super.getProgramID()));
            } else {
                Log.i("ShaderProgram", "Linking of " + name + " successful");
                getAllUniformLocations();
            }
        }
    }

    protected abstract String globalConstantsVS();
    protected abstract String globalConstantsFS();
    protected abstract void bindAttributes();
    protected abstract void getAllUniformLocations();


    public void start() {
        GLES31.glUseProgram(super.getProgramID());
    }

    public void stop() {
        GLES31.glUseProgram(0);
    }

    public void disposeShader() {
        GLES31.glDetachShader(super.getProgramID(), vsID);
        GLES31.glDetachShader(super.getProgramID(), fsID);
        GLES31.glDeleteShader(vsID);
        GLES31.glDeleteShader(fsID);
    }


    protected void bindAttribute(int vaoIndex, String variableName) {
        GLES31.glBindAttribLocation(super.getProgramID(), vaoIndex, variableName);
    }

    protected int getUniformLocation(String uniformName) {
        int id = GLES31.glGetUniformLocation(super.getProgramID(), uniformName);
        if (id == -1) {
            Log.w("ShaderProgram", "Uniform variable: " + uniformName + " was not found in the shader program: " + name);
        }
        return id;
    }

    //Loading Methods
    protected void loadFloat(int location, float value) {
        GLES31.glUniform1f(location, value);
    }

    protected void loadInt(int location, int value) {
        GLES31.glUniform1i(location, value);
    }

    protected void loadVector3F(int location, Vector3F vector) {
        GLES31.glUniform3f(location, vector.x, vector.y, vector.z);
    }

    protected void loadVector4F(int location, Vector4F vector) {
        GLES31.glUniform4f(location, vector.x, vector.y, vector.z, vector.w);
    }

    protected void loadBoolean(int location, boolean value) {
        GLES31.glUniform1i(location, value ? 1 : 0);
    }

    protected void loadMatrix(int location, Matrix4f matrix) {
        GLES31.glUniformMatrix4fv(location, 1, false, matrix.getArray(), 0);
    }

    protected void loadMatrix4x2(int location, Matrix4x2f matrix) {
        GLES31.glUniformMatrix4x2fv(location, 1, false, matrix.getArray(), 0);
    }

    protected void loadSun(int positionLocation, int sunlightColorLocation, int nightShininessColorLocation, Sun sun) {
        loadVector3F(positionLocation, sun.getPosition());
        loadVector3F(sunlightColorLocation, sun.getSunlightColor());
        if (nightShininessColorLocation != -1) {
            loadVector3F(nightShininessColorLocation, sun.getSunNightShininessColor());
        }
    }

}
