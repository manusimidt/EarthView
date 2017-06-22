package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Tools;


import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.TriangleIndices.TriangleIndicesInt;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.TriangleIndices.TriangleIndicesShort;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector2F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.List;


public class BufferUtils {


    public static FloatBuffer convertVector3FListToFloatBuffer(List<Vector3F> value){
        Vector3F[] vectorArray = new Vector3F[value.size()];

       for (int i = 0; i<value.size();i++){
           vectorArray[i] = value.get(i);
       }
        return convertVector3FArrayToFloatBuffer(vectorArray);
    }
    public static FloatBuffer convertVector2FListToFloatBuffer(List<Vector2F> value){
        Vector2F[] vectorArray = new Vector2F[value.size()];

        for (int i = 0; i<value.size();i++){
            vectorArray[i] = value.get(i);
        }
        return convertVector2FArrayToFloatBuffer(vectorArray);
    }

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

    public static FloatBuffer convertVector2FArrayToFloatBuffer(Vector2F... value) {
        float[] result = new float[value.length * 2];
        int arrayIncrement = 0;
        for (Vector2F vector : value) {
            result[arrayIncrement] = vector.x;
            arrayIncrement++;
            result[arrayIncrement] = vector.y;
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

    @SuppressWarnings("unchecked")
    public static <T> Buffer convertTrianglesToBuffer(List<T> triangles){

        Buffer buffer;

        if((triangles.get(triangles.size()-1))instanceof TriangleIndicesInt){
            buffer = convertIntArrayToIntBuffer(TriangleIndicesInt.convertToIntArray((List<TriangleIndicesInt>) triangles));
        }else if((triangles.get(triangles.size()-1))instanceof TriangleIndicesShort){
            buffer = convertShortArrayToShortBuffer(TriangleIndicesShort.convertToShortArray((List<TriangleIndicesShort>) triangles));
        }else{
            throw new IllegalArgumentException("Indices data type is not supported!");
        }
        return buffer;
    }


}
