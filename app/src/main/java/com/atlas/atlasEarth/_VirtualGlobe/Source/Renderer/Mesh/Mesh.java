package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Mesh;


import android.opengl.GLES31;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Tools.BufferUtils;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.BufferGL3x.BufferGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.TypeConverterGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.VertexArrayGL3x;

import java.nio.Buffer;
import java.util.List;


public class Mesh {


    //Created by Constructor
    private VertexBufferCollection vertexBufferCollection = null;
    private Buffer indicesBufferRAW;

    //Created by activateVAO(), in the EGL Context
    private VertexArrayGL3x vertexArray;
    private BufferGL3x indicesBuffer;

    //Winding order of the triangles
    private byte frontFaceWindingOrder;
    //Render Mode (i.e GL_TRIANGLES)
    private byte renderMode;


    public <T> Mesh(@NonNull VertexBufferCollection vbos, @Nullable List<T> indices, byte frontFaceWindingOrder) {
        this(vbos, frontFaceWindingOrder);

        if (indices != null) {
           indicesBufferRAW = BufferUtils.convertTrianglesToBuffer(indices);
        }
    }

    public Mesh(@NonNull VertexBufferCollection vbos, @Nullable int[] indices, byte frontFaceWindingOrder) {
        this(vbos, frontFaceWindingOrder);

        if (indices != null) {
            this.indicesBufferRAW = BufferUtils.convertIntArrayToIntBuffer(indices);
        }

    }

    public Mesh(@NonNull VertexBufferCollection vbos, byte frontFaceWindingOrder) {

        this.vertexBufferCollection = vbos;
        this.frontFaceWindingOrder = frontFaceWindingOrder;

    }


    public void activateVAO() {
        vertexArray = new VertexArrayGL3x(vertexBufferCollection);

        if (indicesBufferRAW != null) {
            indicesBuffer = new BufferGL3x(ByteFlags.GL_ELEMENTARRAY_BUFFER, ByteFlags.GL_STATIC_DRAW);
            indicesBuffer.setData(indicesBufferRAW);
        }
        renderMode = vertexBufferCollection.getMode();
        clearMesh();
    }

    public void draw() {
        if (vertexArray == null) {
            activateVAO();
        }
        if (indicesBuffer == null) {
            vertexArray.bindAndEnableVAO();
            GLES31.glDrawArrays(getDrawModeGL3x(), 0, vertexArray.getVertexCount());
            vertexArray.unbindAndDisableVAO();
        } else {
            vertexArray.bindAndEnableVAO();
            indicesBuffer.bind();

            GLES31.glDrawElements(getDrawModeGL3x(), vertexArray.getVertexCount(), indicesBuffer.getDataTypeGL3x(), 0);

            indicesBuffer.unbind();
            vertexArray.unbindAndDisableVAO();
        }

    }

    public byte getFrontFaceWindingOrder() {
        return frontFaceWindingOrder;
    }

    private int getDrawModeGL3x() {
        return TypeConverterGL3x.convert(TypeConverterGL3x.Category_RENDER_MODE, renderMode);
    }

    private void clearMesh() {
        indicesBufferRAW = null;
        vertexBufferCollection = null;
    }

    public void dispose() {
        clearMesh();
        vertexArray.dispose();
        if(indicesBufferRAW !=null) {
            indicesBuffer.dispose();
        }
    }


}
