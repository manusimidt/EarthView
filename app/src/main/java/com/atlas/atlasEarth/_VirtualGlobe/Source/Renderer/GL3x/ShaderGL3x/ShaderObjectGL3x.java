package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.ShaderGL3x;


import android.content.Context;
import android.opengl.GLES31;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


class ShaderObjectGL3x {

    private int shaderID;

    ShaderObjectGL3x(Context context, int shaderType, int rawID) {

        try {
            if (shaderType != GLES31.GL_VERTEX_SHADER && shaderType != GLES31.GL_FRAGMENT_SHADER) {
                throw new Exception("No Valid Shader Type !!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        shaderID = GLES31.glCreateShader(shaderType);
        if (shaderType == GLES31.GL_FRAGMENT_SHADER) {
            GLES31.glShaderSource(shaderID, loadFsConstants() + loadShader(context, rawID));
        } else {
            GLES31.glShaderSource(shaderID, loadShader(context, rawID));
        }
        GLES31.glCompileShader(shaderID);
        Log.d("Program", "Shader Type: " + shaderType + ", CompileLog: \n" + GLES31.glGetShaderInfoLog(shaderID));


    }

    private String loadFsConstants() {
        return
        "#extension GL_OES_standard_derivatives : enable\n" +
                "precision mediump float;\n"+
                "const float og_oneOverPi = " + (1 / Math.PI) + "; \n" +
                "const float og_oneOverTwoPi = " + (1 / (Math.PI * 2)) + "; \n";
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

