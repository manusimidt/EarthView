package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x;

import android.opengl.GLES31;
import android.support.annotation.Nullable;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.BufferGL3x.BufferGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.NamesGL3x.VertexArrayNameGL3x;

import java.nio.FloatBuffer;


/**
 * Class representing a VertexArray in Java
 */

public class VertexArrayGL3x extends VertexArrayNameGL3x {

    /**
     * @param positionBuffer The position Data as {@link java.nio.Buffer} Object which should be loaded in the VAO(0)
     * @param normalBuffer The normals Data as {@link java.nio.Buffer} Object which should be loaded in the VAO(1)
     * @param textureBuffer The texture Coordinates Data as {@link java.nio.Buffer} Object which should be loaded in the VAO(2)
     */

    public VertexArrayGL3x(FloatBuffer positionBuffer, @Nullable FloatBuffer normalBuffer, @Nullable FloatBuffer textureBuffer) {
        bindAndEnableVAO();

        //Store Positions
        storeFloatBufferInVAO(0, 3, positionBuffer);

        //Check for normals
        if (normalBuffer != null) {
            storeFloatBufferInVAO(1, 3, normalBuffer);
        }

        //Check for Texture Coordinates
        if (textureBuffer != null) {
            storeFloatBufferInVAO(2, 2, textureBuffer);
        }
        unbindAndDisableVAO();
    }

    private void storeFloatBufferInVAO(int index, int dimension, FloatBuffer data) {
        BufferGL3x buffer = new BufferGL3x(ByteFlags.GL_ARRAY_BUFFER, ByteFlags.GL_STATIC_DRAW, data.capacity() * 4);
        buffer.setData(data);
        GLES31.glEnableVertexAttribArray(index);
        GLES31.glVertexAttribPointer(index, dimension, GLES31.GL_FLOAT, false, 0, 0);
    }


}
