package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.BufferGL3x;

import android.opengl.GLES31;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.NamesGL3x.BufferNameGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.TypeconverterGL3x;

import java.nio.Buffer;
import java.nio.FloatBuffer;


public class BufferGL3x extends BufferNameGL3x {


    private int sizeIinBytes;
    private int bufferType;
    private int usageHint;


    public BufferGL3x(byte bufferType, byte usageHint, int sizeInBytes) {
        super();
        this.bufferType = TypeconverterGL3x.convert(bufferType);
        this.usageHint = TypeconverterGL3x.convert(usageHint);
        this.sizeIinBytes = sizeInBytes;
        TypeconverterGL3x.testForValidity(bufferType, ByteFlags.ARRAY_BUFFER, ByteFlags.UNIFORM_BUFFER);
        TypeconverterGL3x.testForValidity(usageHint, ByteFlags.STREAM_DRAW, ByteFlags.DYNAMIC_COPY);
        TypeconverterGL3x.testForBiggerZero(sizeInBytes);
    }

    public void setData(Buffer data) {
        bind();
        GLES31.glBufferData(bufferType, sizeIinBytes, data, usageHint);
    }


    public void bind() {
        GLES31.glBindBuffer(bufferType, super.getId());
    }

    public void unbind() {
        GLES31.glBindBuffer(0, super.getId());
    }
}
