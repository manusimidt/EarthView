package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.BufferGL3x;

import android.opengl.GLES31;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.NamesGL3x.BufferNameGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.TypeConverterGL3x;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;


/**
 * Class representing a OpenGL Buffer object (VBO)
 */
public class BufferGL3x extends BufferNameGL3x {


    private int sizeIinBytes;
    private byte bufferType;
    private byte usageHint;
    private byte dataType;

    /**
     * @param bufferType  type of the buffer (i.e ARRAY_BUFFER)
     * @param usageHint   usage hint of the buffer (i.e STATIC_DRAW)
     */
    public BufferGL3x(byte bufferType, byte usageHint) {
        super();
        this.bufferType = bufferType;
        this.usageHint = usageHint;
    }

    /**
     * load the Data in OpenGL
     *
     * @param data Object of {@link Buffer} which should be loaded in OpenGL
     */
    public void setData(Buffer data) {
        bind();
        if (data instanceof IntBuffer) {
            dataType = ByteFlags.GL_UNSIGNED_INT;
            sizeIinBytes = data.capacity() * 4;
        } else if (data instanceof ShortBuffer) {
            dataType = ByteFlags.GL_UNSIGNED_SHORT;
            sizeIinBytes = data.capacity() * 2;
        } else if (data instanceof FloatBuffer) {
            // TODO: 4/11/2017 test if Medium float works!
            dataType = ByteFlags.GL_MEDIUM_FLOAT;
            sizeIinBytes = data.capacity() * 4;
        }
        GLES31.glBufferData(getBufferTypeGL3x(),
                sizeIinBytes,
                data,
                getUsageHintGL3x());
    }


    public void bind() {
        GLES31.glBindBuffer(getBufferTypeGL3x(), super.getId());
    }

    public void unbind() {
        GLES31.glBindBuffer(getBufferTypeGL3x(), 0);
    }


    public int getDataTypeGL3x() {
        return TypeConverterGL3x.convert(TypeConverterGL3x.Category_DATA_TYPES, dataType);
    }

    private int getBufferTypeGL3x() {
        return TypeConverterGL3x.convert(TypeConverterGL3x.Category_BUFFER_TYPES, bufferType);
    }

    private int getUsageHintGL3x() {
        return TypeConverterGL3x.convert(TypeConverterGL3x.Category_BUFFER_USAGE_HINT, usageHint);
    }
}
