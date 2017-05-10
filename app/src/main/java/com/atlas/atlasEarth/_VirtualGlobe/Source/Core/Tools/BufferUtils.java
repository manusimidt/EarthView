package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Tools;


import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;


public class BufferUtils {


    public static FloatBuffer convertVector3FArrayToFloatBuffer(Vector3F... value) {
        float[] result = new float[value.length * 3];
        int arrayIncrement = 0;
        for (Vector3F vector : value) {
            result[arrayIncrement] = vector.x;
            arrayIncrement++;
            result[arrayIncrement] = vector.y;
            arrayIncrement++;
            result[arrayIncrement] = vector.z;
            arrayIncrement++;
        }
        return convertFloatArrayToFloatBuffer(result);
    }

    public static FloatBuffer convertFloatArrayToFloatBuffer(float... data) {
        if (data.length < 3) {
            throw new IllegalArgumentException("For VertexBuffer are at least three Vertices required!");
        }
        FloatBuffer buffer = ByteBuffer.allocateDirect(data.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
    public static IntBuffer convertIntArrayToIntBuffer(int... data) {
        IntBuffer buffer = ByteBuffer.allocateDirect(data.length * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
    public static ShortBuffer convertShortArrayToShortBuffer(short... data) {
        ShortBuffer buffer = ByteBuffer.allocateDirect(data.length * 2).order(ByteOrder.nativeOrder()).asShortBuffer();
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

}
