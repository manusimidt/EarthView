package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Mesh;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector2F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Tools.BufferUtils;

import java.nio.FloatBuffer;
import java.util.List;


/**
 * Container for all VAO related Data.
 * Used for com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Mesh
 */

public class VertexBufferCollection {

    private FloatBuffer positionBuffer;
    private FloatBuffer normalBuffer;
    private FloatBuffer textureBuffer;
    private byte mode;

    /* Tells which slot in the VAO have to be activated
     * (true at position 2 means that the second VAO slot have to be activated)
     */
    private boolean[] requiredSlots;
    private int vertexCount;


    /**
     * @param positions          3D Positions for VAO in Euclidean Space
     * @param normals            3D Normals, could be null
     * @param textureCoordinates 2D Texture Coordinates, could be null
     * @param mode               Draw mode for glDraw...() (ie: GL_TRIANGLES)
     */
    public VertexBufferCollection(@NonNull List<Vector3F> positions, @Nullable List<Vector3F> normals, @Nullable List<Vector2F> textureCoordinates, byte mode) {
        this.positionBuffer = BufferUtils.convertVector3FListToFloatBuffer(positions);
        vertexCount = positions.size() * 3;

        if (normals != null) {
            this.normalBuffer = BufferUtils.convertVector3FListToFloatBuffer(normals);
        }

        if (textureCoordinates != null) {
            this.textureBuffer = BufferUtils.convertVector2FListToFloatBuffer(textureCoordinates);
        }

        requiredSlots = new boolean[]{true, (normalBuffer != null), (textureCoordinates != null)};

        this.mode = mode;
    }

    public VertexBufferCollection(@NonNull float[] positions, @Nullable float[] normals, @Nullable float[] textureCoordinates, byte mode) {
        this.positionBuffer = BufferUtils.convertFloatArrayToFloatBuffer(positions);
        vertexCount = positions.length;

        if (normals != null) {
            this.normalBuffer = BufferUtils.convertFloatArrayToFloatBuffer(normals);
        }
        if (textureCoordinates != null) {
            this.textureBuffer = BufferUtils.convertFloatArrayToFloatBuffer(textureCoordinates);
        }

        requiredSlots = new boolean[]{true, (normalBuffer != null), (textureCoordinates != null)};

        this.mode = mode;
    }

    public FloatBuffer getPositionBuffer() {
        return positionBuffer;
    }

    public FloatBuffer getNormalBuffer() {
        return normalBuffer;
    }

    public FloatBuffer getTextureBuffer() {
        return textureBuffer;
    }

    public boolean[] getRequiredSlots() {
        return requiredSlots;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public byte getMode() {
        return mode;
    }

    public void clear() {
        positionBuffer.clear();
        if (requiredSlots[1]) {
            normalBuffer.clear();
        }
        if (requiredSlots[2]) {
            textureBuffer.clear();
        }
    }
}
