package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.NamesGL3x;

import android.opengl.GLES31;
import android.util.Log;


public class ShaderProgramNameGL3x {
    private final int programID;

    public ShaderProgramNameGL3x() {
        programID = GLES31.glCreateProgram();
        if(programID ==-1){
            Log.e("Program", "Error occurred while creating shader program! Returned ID: " + programID);
            throw new IllegalArgumentException("Error occurred while creating Shader Program");
        }
    }

    public void dispose() {
        GLES31.glDeleteProgram(programID);
    }

    public int getProgramID() {
        return programID;
    }
}
