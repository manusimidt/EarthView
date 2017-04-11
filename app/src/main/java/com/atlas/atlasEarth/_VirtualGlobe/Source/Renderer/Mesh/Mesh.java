package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Mesh;


import com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.TriangleIndices.TriangleIndicesInt;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.TriangleIndices.TriangleIndicesShort;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.BufferGL3x.BufferGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.VertexArrayGL3x;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.List;

public class Mesh {


    private List<TriangleIndicesInt> trianglesInt = null;
    private List<TriangleIndicesShort> trianglesShort = null;
    private VertexAttributeCollection vertexAttributes = null;

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
        vertexArray = new VertexArrayGL3x(positions, normals, textureCoords);


        indicesDataType = ByteFlags.INT;
        vertexCount = indices.length;

        IntBuffer indicesBufferInt = ByteBuffer.allocateDirect(indices.length * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
        indicesBufferInt.put(indices);
        indicesBufferInt.rewind();
        indicesBuffer = new BufferGL3x(ByteFlags.ELEMENTARRAY_BUFFER, ByteFlags.STATIC_DRAW, indices.length * 4);
        indicesBuffer.setData(indicesBufferInt);

    }

    public void addVertexAttributes(VertexAttributeCollection vertexAttributeCollection) {
        this.vertexAttributes = vertexAttributeCollection;
        if (indicesDataType != ByteFlags.NULL) {
            progressData();
        }
    }

    public <T> void addTriangles(List<T> triangles) {

        if (triangles.get(triangles.size() - 1) instanceof TriangleIndicesShort) {
            this.trianglesShort = (List<TriangleIndicesShort>) triangles;
            indicesDataType = ByteFlags.SHORT;
        } else if (triangles.get(triangles.size() - 1) instanceof TriangleIndicesInt) {
            this.trianglesInt = (List<TriangleIndicesInt>) triangles;
            indicesDataType = ByteFlags.INT;
        }

        vertexCount = triangles.size() * 3;
        if (vertexAttributes != null) {
            progressData();
        }
    }


    private void progressData() {
        vertexArray = new VertexArrayGL3x(vertexAttributes);
        vertexAttributes.clear();


        ShortBuffer indicesBufferShort;
        IntBuffer indicesBufferInt;

        if (indicesDataType == ByteFlags.SHORT) {
            indicesBufferShort = ByteBuffer.allocateDirect(trianglesShort.size() * 3 * 2).order(ByteOrder.nativeOrder()).asShortBuffer();
            indicesBufferShort.put(TriangleIndicesShort.convertToShortArray(trianglesShort));
            indicesBufferShort.rewind();
            indicesBuffer = new BufferGL3x(ByteFlags.ELEMENTARRAY_BUFFER, ByteFlags.STATIC_DRAW, trianglesShort.size() * 3 * 2);
            indicesBuffer.setData(indicesBufferShort);

        } else if (indicesDataType == ByteFlags.INT) {
            indicesBufferInt = ByteBuffer.allocateDirect(trianglesInt.size() * 3 * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
            indicesBufferInt.put(TriangleIndicesInt.convertToIntArray(trianglesInt));
            indicesBufferInt.rewind();
            indicesBuffer = new BufferGL3x(ByteFlags.ELEMENTARRAY_BUFFER, ByteFlags.STATIC_DRAW, trianglesInt.size() * 3 * 4);
            indicesBuffer.setData(indicesBufferInt);

        } else {
            throw new IllegalArgumentException("Mesh doesn't contain Indices!");
        }
        trianglesInt = null;
        trianglesShort = null;
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
        vertexAttributes = null;
        indicesBuffer = null;
    }

    public VertexArrayGL3x getVertexArray() {

        return vertexArray;
    }

    public BufferGL3x getIndicesBuffer() {
        return indicesBuffer;
    }


}
