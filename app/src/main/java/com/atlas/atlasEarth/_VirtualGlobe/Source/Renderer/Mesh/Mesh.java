package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Mesh;


import com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.cartesianCS.Indices.TriangleIndicesInt;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.cartesianCS.Indices.TriangleIndicesShort;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.VertexArrayGL3x;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.List;

public class Mesh {


    private List<TriangleIndicesInt> trianglesInt;
    private List<TriangleIndicesShort> trianglesShort;
    private byte indicesDataType = ByteFlags.NULL;
    private VertexAttributeCollection vertexAttributes = null;

    private VertexArrayGL3x vertexArray;
    private ShortBuffer indicesBufferShort;
    private IntBuffer indicesBufferInt;


    public void addVertexAttributes(VertexAttributeCollection vertexAttributeCollection) {
        this.vertexAttributes = vertexAttributeCollection;
        if (indicesDataType != ByteFlags.NULL) {
            progressData();
        }
    }


    public void addTriangleIndicesShort(List<TriangleIndicesShort> triangles) {
        this.trianglesShort = triangles;
        indicesDataType = ByteFlags.SHORT;

        if (vertexAttributes != null) {
            progressData();
        }
    }

    public void addTriangleIndicesInt(List<TriangleIndicesInt> triangles) {
        this.trianglesInt = triangles;
        indicesDataType = ByteFlags.INT;

        if (vertexAttributes != null) {
            progressData();
        }
    }

    private void progressData() {
        vertexArray = new VertexArrayGL3x(vertexAttributes);
        vertexAttributes.clear();

        if (indicesDataType == ByteFlags.SHORT) {
            indicesBufferShort = ByteBuffer.allocateDirect(trianglesShort.size() * 3 * 2).order(ByteOrder.nativeOrder()).asShortBuffer();
            indicesBufferShort.put(TriangleIndicesShort.convertToShortArray(trianglesShort));
            indicesBufferShort.rewind();

        } else if (indicesDataType == ByteFlags.INT) {
            indicesBufferInt = ByteBuffer.allocateDirect(trianglesInt.size() * 3 * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
            indicesBufferInt.put(TriangleIndicesInt.convertToIntArray(trianglesInt));
            indicesBufferInt.rewind();

        } else {
            throw new IllegalArgumentException("Mesh doesn't contain Indices!");
        }

    }

    public int elementsCount() {
        switch (indicesDataType) {
            case ByteFlags.INT:
                return trianglesInt.size()*3;
            case ByteFlags.SHORT:
                return trianglesShort.size()*3;
            default:
                throw new IllegalArgumentException("Mesh doesn't contain Indices!");
        }
    }


    public void clear() {
        vertexAttributes = null;
        indicesBufferShort = null;
        indicesBufferInt = null;
    }

    public VertexArrayGL3x getVertexArray() {

        return vertexArray;
    }

    public byte getBufferType(){
        return indicesDataType;
    }

    public ShortBuffer getIndicesBufferShort() {
        return indicesBufferShort;
    }

    public IntBuffer getIndicesBufferInt() {
        return indicesBufferInt;
    }
}
