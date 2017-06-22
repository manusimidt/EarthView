package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x;

import android.opengl.GLES31;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.BufferGL3x.BufferGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.NamesGL3x.VertexArrayNameGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Mesh.VertexBufferCollection;

import java.nio.FloatBuffer;


/**
 * Class representing a VertexArray in Java
 */

public class VertexArrayGL3x extends VertexArrayNameGL3x {

    private int vertexCount;

    public VertexArrayGL3x(VertexBufferCollection vbos) {
        super(vbos.getRequiredSlots());
        bindAndEnableVAO();

        //Store Positions
        storeFloatBufferInVAO(0, 3, vbos.getPositionBuffer());

        //Check for normals
        if (vbos.getRequiredSlots()[1]) {
            storeFloatBufferInVAO(1, 3, vbos.getNormalBuffer());
        }

        //Check for Texture Coordinates
        if (vbos.getRequiredSlots()[2]) {
            storeFloatBufferInVAO(2, 2, vbos.getTextureBuffer());
        }
        unbindAndDisableVAO();

        vertexCount = vbos.getVertexCount();
    }

    private void storeFloatBufferInVAO(int index, int dimension, FloatBuffer data) {
        BufferGL3x buffer = new BufferGL3x(ByteFlags.GL_ARRAY_BUFFER, ByteFlags.GL_STATIC_DRAW);
        buffer.setData(data);
        GLES31.glEnableVertexAttribArray(index);
        GLES31.glVertexAttribPointer(index, dimension, GLES31.GL_FLOAT, false, 0, 0);
    }

    public int getVertexCount() {
        return vertexCount;
    }
}
