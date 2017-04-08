package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x;

import android.opengl.GLES31;
import android.support.annotation.Nullable;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector2F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.BufferGL3x.BufferGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.NamesGL3x.VertexArrayNameGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Mesh.VertexAttributeCollection;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.List;


public class VertexArrayGL3x extends VertexArrayNameGL3x {

    public VertexArrayGL3x(VertexAttributeCollection vertexAttributes) {
        bindAndEnableVAO();

        //Store Positions
        storeFloatBufferInVAO(0, 3, vertexAttributes.getPositions().size() * 3, convertVector3ArrayListToFloatBuffer(vertexAttributes.getPositions()));

        //Check for normals
        if (vertexAttributes.getNormals() != null) {
            storeFloatBufferInVAO(1, 3, vertexAttributes.getNormals().size() * 3, convertVector3ArrayListToFloatBuffer(vertexAttributes.getNormals()));
        }

        //Check for Texture Coordinates
        if (vertexAttributes.getTextureCoordinates() != null) {
            storeFloatBufferInVAO(2, 2, vertexAttributes.getTextureCoordinates().size() * 2, convertVector2ArrayListToFloatBuffer(vertexAttributes.getTextureCoordinates()));
        }

        unbindAndDisableVAO();
    }

    public VertexArrayGL3x(float[] positions, @Nullable float[] normals, @Nullable float[] textureCoords) {
        bindAndEnableVAO();
        storeFloatBufferInVAO(0, 3, positions.length, convertFloatArrayToFloatBuffer(positions));
        if(normals != null) {
            storeFloatBufferInVAO(1, 3, normals.length, convertFloatArrayToFloatBuffer(normals));
        }
        if(textureCoords != null) {
            storeFloatBufferInVAO(2, 2, textureCoords.length, convertFloatArrayToFloatBuffer(textureCoords));
        }
    }


    private void storeFloatBufferInVAO(int index, int dimension, int amountOfFloats, FloatBuffer data) {
        BufferGL3x buffer = new BufferGL3x(ByteFlags.ARRAY_BUFFER, ByteFlags.STATIC_DRAW, amountOfFloats * 4);
        buffer.setData(data);
        GLES31.glEnableVertexAttribArray(index);
        GLES31.glVertexAttribPointer(index, dimension, GLES31.GL_FLOAT, false, 0, 0);
    }

    private FloatBuffer convertVector3ArrayListToFloatBuffer(List<Vector3F> data) {
        FloatBuffer buffer = ByteBuffer.allocateDirect(data.size() * 3 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        buffer.put(Vector3F.toArray(data));
        buffer.flip();
        return buffer;
    }

    private FloatBuffer convertVector2ArrayListToFloatBuffer(List<Vector2F> data) {
        FloatBuffer buffer = ByteBuffer.allocateDirect(data.size() * 2 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        buffer.put(Vector2F.convertToFloatArray(data));
        buffer.flip();
        return buffer;
    }

    private FloatBuffer convertFloatArrayToFloatBuffer(float[] data) {
        FloatBuffer buffer = ByteBuffer.allocateDirect(data.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        buffer.put(data);
        buffer.flip();
        return buffer;
    }


}
