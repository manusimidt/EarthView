package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Tools;


import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.TriangleIndices.TriangleIndicesInt;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.TriangleIndices.TriangleIndicesShort;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector2F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.List;

public class BufferUtils {

    public static FloatBuffer convertVector3ArrayListToFloatBuffer(List<Vector3F> data) {
        FloatBuffer buffer = ByteBuffer.allocateDirect(data.size() * 3 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        buffer.put(Vector3F.toArray(data));
        buffer.flip();
        return buffer;
    }

    public static FloatBuffer convertVector2ArrayListToFloatBuffer(List<Vector2F> data) {
        FloatBuffer buffer = ByteBuffer.allocateDirect(data.size() * 2 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        buffer.put(Vector2F.convertToFloatArray(data));
        buffer.flip();
        return buffer;
    }

    public static FloatBuffer convertFloatArrayToFloatBuffer(float[] data) {
        FloatBuffer buffer = ByteBuffer.allocateDirect(data.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
    public static IntBuffer convertIntArrayToIntBuffer(int[] data){
        IntBuffer buffer = ByteBuffer.allocateDirect(data.length*4).order(ByteOrder.nativeOrder()).asIntBuffer();
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public static ShortBuffer convertTriangleIndicesShortToShortBuffer(List<TriangleIndicesShort> data){
        ShortBuffer buffer = ByteBuffer.allocateDirect(data.size()*3*2).order(ByteOrder.nativeOrder()).asShortBuffer();
        buffer.put(TriangleIndicesShort.convertToShortArray(data));
        buffer.flip();
        return buffer;
    }

    public static IntBuffer convertTriangleIndicesIntToShortBuffer(List<TriangleIndicesInt> data){
        IntBuffer buffer = ByteBuffer.allocateDirect(data.size()*3*4).order(ByteOrder.nativeOrder()).asIntBuffer();
        buffer.put(TriangleIndicesInt.convertToIntArray(data));
        buffer.flip();
        return buffer;
    }
}
