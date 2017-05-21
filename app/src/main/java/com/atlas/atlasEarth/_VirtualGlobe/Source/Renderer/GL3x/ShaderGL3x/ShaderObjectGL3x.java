package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.ShaderGL3x;


import android.content.Context;
import android.opengl.GLES31;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Class representing a single Shader in java
 */
class ShaderObjectGL3x {

    private int shaderID;

    /**
     * @param context Context of the calling class (needed for reading from .raw resources)
     * @param shaderTypeGL3x type of shader as {@link GLES31} flag
     * @param rawID resource id where the shader code is stored
     * @param globalConstants pre (GPU)Processor Constants
     * @param name Name of the {@link ShaderProgramGL3x} for debugging
     */
    ShaderObjectGL3x(Context context, int shaderTypeGL3x, int rawID, String globalConstants, String name) {
        try {
            if (shaderTypeGL3x != GLES31.GL_VERTEX_SHADER && shaderTypeGL3x != GLES31.GL_FRAGMENT_SHADER) {
                throw new Exception("No Valid Shader Type !!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        shaderID = GLES31.glCreateShader(shaderTypeGL3x);
        if (shaderTypeGL3x == GLES31.GL_FRAGMENT_SHADER) {
            GLES31.glShaderSource(shaderID, getGLSLVersionHeader() + loadFsConstants() + globalConstants + loadShader(context, rawID));
        } else {
            GLES31.glShaderSource(shaderID, getGLSLVersionHeader() +globalConstants+  loadShader(context, rawID));
        }
        GLES31.glCompileShader(shaderID);

        // for debugging reasons
        int[] compileStatus = new int[1];
        GLES31.glGetShaderiv(shaderID, GLES31.GL_COMPILE_STATUS,compileStatus,0);

        if(compileStatus[0] == GLES31.GL_FALSE){
            Log.e("ShaderProgram", "COMPILE ERROR! Name: " + name +
                    ", Shader Type: " + ((shaderTypeGL3x == GLES31.GL_VERTEX_SHADER)? "Vertex Shader" : "Fragment Shader") +
                    ", CompileLog: \n" + GLES31.glGetShaderInfoLog(shaderID));
            GLES31.glDeleteShader(shaderID);
            shaderID = -1;
        }else{
            Log.i("ShaderProgram", "Compile of " + ((shaderTypeGL3x == GLES31.GL_VERTEX_SHADER)? "Vertex Shader" : "Fragment Shader") + " successful. Name: " + name);
        }

    }

    private String getGLSLVersionHeader() {
        return "#version 300 es \n";
    }

    private String loadFsConstants() {
        return
                "#extension GL_OES_standard_derivatives : enable\n" +
                        "precision mediump float;\n";
    }

    private String loadShader(Context context, int rawID) {

        BufferedReader buffReader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(rawID)));
        String line;
        StringBuilder text = new StringBuilder();

        try {
            while ((line = buffReader.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return text.toString();

    }

    int getShaderID() {
        return shaderID;
    }

}

