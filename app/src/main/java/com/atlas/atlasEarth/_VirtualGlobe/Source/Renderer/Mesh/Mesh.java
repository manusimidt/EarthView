package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Mesh;


import com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.TriangleIndices.TriangleIndicesInt;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.TriangleIndices.TriangleIndicesShort;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector2F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Tools.BufferUtils;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.BufferGL3x.BufferGL3x;
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
    private int vertexCount;

    public Mesh() {

    }

    /**
     * Special Constructor for higher performance
     */
    public Mesh(int indices[], float[] positions, float[] normals, float[] textureCoords) {
      indicesDataType = ByteFlags.INT;
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
        if (indicesDataType != ByteFlags.NULL) {
            progressData();
        }
    }

    @SuppressWarnings("unchecked")
    public <T> void addTriangles(List<T> triangles) {

        if (triangles.get(triangles.size() - 1) instanceof TriangleIndicesShort) {
            this.trianglesShort = (List<TriangleIndicesShort>) triangles;
            indicesDataType = ByteFlags.SHORT;
        } else if (triangles.get(triangles.size() - 1) instanceof TriangleIndicesInt) {
            this.trianglesInt = (List<TriangleIndicesInt>) triangles;
            indicesDataType = ByteFlags.INT;
        }

        vertexCount = triangles.size() * 3;

        progressData();

    }


    private void progressData() {
        positionBufferRaw = BufferUtils.convertFloatArrayToFloatBuffer(Vector3F.toArray(vertexAttributes.getPositions()));
        if (vertexAttributes.getNormals() != null) {
            normalBufferRaw = BufferUtils.convertFloatArrayToFloatBuffer(Vector3F.toArray(vertexAttributes.getNormals()));
        }
        if (vertexAttributes.getTextureCoordinates() != null) {
            textureBufferRaw = BufferUtils.convertFloatArrayToFloatBuffer(Vector2F.toArray(vertexAttributes.getTextureCoordinates()));
        }

        if (indicesDataType == ByteFlags.INT) {
            indicesBufferRAW = BufferUtils.convertIntArrayToIntBuffer(TriangleIndicesInt.convertToIntArray(trianglesInt));
        } else if (indicesDataType == ByteFlags.SHORT) {
            indicesBufferRAW = BufferUtils.convertShortArrayToShortBuffer(TriangleIndicesShort.convertToShortArray(trianglesShort));
        } else {
            throw new IllegalArgumentException("No indices are assigned!!");
        }
        vertexAttributes.clear();
    }


    public void activateVAO() {
        vertexArray = new VertexArrayGL3x(positionBufferRaw, normalBufferRaw, textureBufferRaw);


        if (indicesDataType == ByteFlags.SHORT) {
            indicesBuffer = new BufferGL3x(ByteFlags.ELEMENTARRAY_BUFFER, ByteFlags.STATIC_DRAW, indicesBufferRAW.limit()*2);
            indicesBuffer.setData(indicesBufferRAW);
        } else if (indicesDataType == ByteFlags.INT) {
            indicesBuffer = new BufferGL3x(ByteFlags.ELEMENTARRAY_BUFFER, ByteFlags.STATIC_DRAW, indicesBufferRAW.limit()*4);
            indicesBuffer.setData(indicesBufferRAW);
        }

     clear();
    }

    public int getVertexCount() {
        if (vertexCount < 1) {
            throw new IllegalArgumentException("Empty Mesh");
        }
        return vertexCount;
    }


    public void clear() {
        trianglesInt = null;
        trianglesShort = null;
        positionBufferRaw = null;
        normalBufferRaw = null;
        textureBufferRaw = null;
        indicesBufferRAW = null;
        vertexAttributes = null;
    }

    public void dispose(){
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
