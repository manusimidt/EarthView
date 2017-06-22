package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.NamesGL3x;

import android.opengl.GLES31;
import android.util.Log;


public class TextureNameGL3x {
    private final int[] id = new int[1];

    public TextureNameGL3x() {
        GLES31.glGenTextures(1, id,0);
        if(id[0] ==-1){
            Log.e("Program", "Error occurred while creating OpenGL Texture! Returned ID: " + id[0]);
            throw new IllegalArgumentException("Error occurred while creating Texture");
        }
    }

    public void dispose() {
        GLES31.glDeleteTextures(1, id,0);
    }

    public int getTextureIDGl3x() {
        return id[0];
    }
}
