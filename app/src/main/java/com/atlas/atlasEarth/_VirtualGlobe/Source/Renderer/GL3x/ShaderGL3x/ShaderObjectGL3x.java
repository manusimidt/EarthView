package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.ShaderGL3x;


import android.content.Context;
import android.opengl.GLES31;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Jonas on 2/12/2017.
 */

public class ShaderObjectGL3x {

    private int shaderID;

    public ShaderObjectGL3x(Context context, int shaderType, int rawID) {


        try {
            if (shaderType != GLES31.GL_VERTEX_SHADER && shaderType != GLES31.GL_FRAGMENT_SHADER) {
                throw new Exception("No Valid Shader Type !!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        shaderID = GLES31.glCreateShader(shaderType);
        if(shaderType == GLES31.GL_FRAGMENT_SHADER){
            GLES31.glShaderSource(shaderID, loadFsConstants() + loadShader(context, rawID));
        }else {
            GLES31.glShaderSource(shaderID, loadShader(context, rawID));
        }
        GLES31.glCompileShader(shaderID);
        Log.d("Program", "Shader Type: " + shaderType + ", CompileLog: \n" + GLES31.glGetShaderInfoLog(shaderID));


    }

    private String loadFsConstants(){
        return "#extension GL_OES_standard_derivatives : enable\n" +
                "const float og_oneOverPi = " + (1/Math.PI) + "; \n" +
                "const float og_oneOverTwoPi = " + (1/(Math.PI*2)) + "; \n";
    }

        private String loadShader(Context context, int rawID) {

            InputStream inputStream = context.getResources().openRawResource(rawID);
            InputStreamReader inputReader = new InputStreamReader(inputStream);
            BufferedReader buffReader = new BufferedReader(inputReader);
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

    public int getShaderID() {
        return shaderID;
    }
}

