package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.NamesGL3x;

import android.opengl.GLES31;


public class BufferNameGL3x {
    private final int[] id = new int[1];

    public BufferNameGL3x() {
        GLES31.glGenBuffers(1, id, 0);
    }

    public int getId() {
        return id[0];
    }
    public void dispose(){
     if(id[1] !=0){
         GLES31.glDeleteBuffers(1, id,0);
         id[0] = 0;
     }
    }
}
