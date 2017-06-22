package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.NamesGL3x;


import android.opengl.GLES31;
import android.util.Log;


public class VertexArrayNameGL3x {

    private int[] id = new int[1];
    private boolean[] enabledSlots;

    protected VertexArrayNameGL3x(boolean... enabledSlots) {
        GLES31.glGenVertexArrays(1, id, 0);
        if (id[0] == -1) {
            Log.e("Program", "Error occurred while creating OpenGL Vertex Array Object! Returned ID: " + id[0]);
            throw new IllegalArgumentException("Error occurred while creating VAO");
        }
        this.enabledSlots = enabledSlots;
    }

    public int getId() {
        return id[0];
    }

    public void bindAndEnableVAO() {
        GLES31.glBindVertexArray(id[0]);
        GLES31.glEnableVertexAttribArray(0);
        if (enabledSlots[1]) {
            GLES31.glEnableVertexAttribArray(1);
        }
        if (enabledSlots[2]) {
            GLES31.glEnableVertexAttribArray(2);
        }
    }

    public void unbindAndDisableVAO() {
        GLES31.glDisableVertexAttribArray(0);
        if (enabledSlots[1]) {
            GLES31.glDisableVertexAttribArray(1);
        }
        if (enabledSlots[2]) {
            GLES31.glDisableVertexAttribArray(2);
        }
        GLES31.glBindVertexArray(0);
    }

    public void dispose() {
        unbindAndDisableVAO();
        GLES31.glDeleteVertexArrays(1, id, 0);
    }
}
