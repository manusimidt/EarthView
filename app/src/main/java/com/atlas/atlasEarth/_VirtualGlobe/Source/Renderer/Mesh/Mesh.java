package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Mesh;


import com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.TriangleIndices.TriangleIndicesInt;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.TriangleIndices.TriangleIndicesShort;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector2F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Tools.BufferUtils;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.BufferGL3x.BufferGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.TypeConverterGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.VertexArrayGL3x;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.List;


public class Mesh {


    private List<TriangleIndicesInt> trianglesInt = null;
    private List<TriangleIndicesShort> trianglesShort = null;
    private VertexAttributeCollection vertexAttributes = null;

    private Buffer indicesBufferRAW;
    private FloatBuffer positionBufferRaw;
    private FloatBuffer normalBufferRaw;
    private FloatBuffer textureBufferRaw;

    private VertexArrayGL3x vertexArray;
    private BufferGL3x indicesBuffer;

    private byte indicesDataType = ByteFlags.NULL;
    private byte frontFaceWindingOrder = ByteFlags.NULL;
    private byte mode = ByteFlags.NULL;
    private int vertexCount;


    /**
     * Required Empty Constructor for modular Mesh
     */
    public Mesh() {

    }


    /**
     * Special Constructor for higher performance
     *
     * @param indices       Indices for GLES31.glDrawElements()
     * @param positions     3D Positions for VAO in Euclidean Space
     * @param normals       3D Normals, could be null
     * @param textureCoords 3D Texture Coordinates, could be null
     * @param mode          Draw mode for glDraw...() (ie: GL_TRIANGLES)
     */
    public Mesh(int indices[], float[] positions, float[] normals, float[] textureCoords, byte mode) {
        this.mode = mode;
        if (positions.length < 3 || indices.length < 3) {
            this.dispose();
            throw new IllegalArgumentException("Mesh must contain at least one position with three coordinates!");
        }

        indicesDataType = ByteFlags.GL_INT;
        vertexCount = indices.length;
        positionBufferRaw = BufferUtils.convertFloatArrayToFloatBuffer(positions);

        if (normals != null) {
            normalBufferRaw = BufferUtils.convertFloatArrayToFloatBuffer(normals);
        }

        if (textureCoords != null) {
            textureBufferRaw = BufferUtils.convertFloatArrayToFloatBuffer(textureCoords);
        }
        indicesBufferRAW = BufferUtils.convertIntArrayToIntBuffer(indices);
    }


    public void addVertexAttributes(VertexAttributeCollection vertexAttributeCollection) {
        this.vertexAttributes = vertexAttributeCollection;
        this.mode = vertexAttributeCollection.getMode();
        if (indicesDataType != ByteFlags.NULL) {
            progressData();
        }
    }

    @SuppressWarnings("unchecked")
    public <T> void addTriangles(List<T> triangles) {

        if (triangles.get(triangles.size() - 1) instanceof TriangleIndicesShort) {
            this.trianglesShort = (List<TriangleIndicesShort>) triangles;
            indicesDataType = ByteFlags.GL_SHORT;
        }
        else if (triangles.get(triangles.size() - 1) instanceof TriangleIndicesInt) {
            this.trianglesInt = (List<TriangleIndicesInt>) triangles;
            indicesDataType = ByteFlags.GL_INT;
        }

        vertexCount = triangles.size() * 3;

        progressData();

    }


    public byte getFrontFaceWindingOrder() {
        return frontFaceWindingOrder;
    }
    public void setFrontFaceWindingOrder(byte frontFaceWindingOrder) {
        this.frontFaceWindingOrder = frontFaceWindingOrder;
    }

    private void progressData() {
        positionBufferRaw = BufferUtils.convertFloatArrayToFloatBuffer(Vector3F.toArray(vertexAttributes.getPositions()));
        if (vertexAttributes.getNormals() != null) {
            normalBufferRaw = BufferUtils.convertFloatArrayToFloatBuffer(Vector3F.toArray(vertexAttributes.getNormals()));
        }
        if (vertexAttributes.getTextureCoordinates() != null) {
            textureBufferRaw = BufferUtils.convertFloatArrayToFloatBuffer(Vector2F.toArray(vertexAttributes.getTextureCoordinates()));
        }

        if (indicesDataType == ByteFlags.GL_INT) {
            indicesBufferRAW = BufferUtils.convertIntArrayToIntBuffer(TriangleIndicesInt.convertToIntArray(trianglesInt));
        } else if (indicesDataType == ByteFlags.GL_SHORT) {
            indicesBufferRAW = BufferUtils.convertShortArrayToShortBuffer(TriangleIndicesShort.convertToShortArray(trianglesShort));
        } else {
            throw new IllegalArgumentException("No indices are assigned!!");
        }
        vertexAttributes.clear();
    }

    public void activateVAO() {
        vertexArray = new VertexArrayGL3x(positionBufferRaw, normalBufferRaw, textureBufferRaw);

        if (indicesDataType == ByteFlags.GL_SHORT) {
            indicesBuffer = new BufferGL3x(ByteFlags.GL_ELEMENTARRAY_BUFFER, ByteFlags.GL_STATIC_DRAW, indicesBufferRAW.limit() * 2);
            indicesBuffer.setData(indicesBufferRAW);
        } else if (indicesDataType == ByteFlags.GL_INT) {
            indicesBuffer = new BufferGL3x(ByteFlags.GL_ELEMENTARRAY_BUFFER, ByteFlags.GL_STATIC_DRAW, indicesBufferRAW.limit() * 4);
            indicesBuffer.setData(indicesBufferRAW);
        }

        clearMesh();
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public int getDrawModeGL3x() {
        return TypeConverterGL3x.convert(TypeConverterGL3x.Category_RENDER_MODE, mode);
    }


    private void clearMesh() {
        trianglesInt = null;
        trianglesShort = null;
        positionBufferRaw = null;
        normalBufferRaw = null;
        textureBufferRaw = null;
        indicesBufferRAW = null;
        vertexAttributes = null;
    }

    public void dispose() {
        clearMesh();
        vertexArray.dispose();
        indicesBuffer.dispose();
    }

    public VertexArrayGL3x getVertexArray() {
        return vertexArray;
    }

    public BufferGL3x getIndicesBuffer() {
        return indicesBuffer;
    }


}
