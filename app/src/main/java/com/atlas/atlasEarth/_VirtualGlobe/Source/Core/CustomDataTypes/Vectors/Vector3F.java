package com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors;


import java.util.List;

public class Vector3F {

    public float x, y, z;

    public Vector3F(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    public Vector3F add(Vector3F value){
        return new Vector3F(x+value.x, y+value.y,z+value.z);
    }
    public Vector3F multiplyComponents(float value){
        return new Vector3F(x*value, y*value,z*value);
    }
    public Vector3F multiplyComponents(Vector3F scale) {
        return new Vector3F(this.x * scale.x, this.y * scale.y, this.z * scale.z);
    }
    public Vector3F divideComponents(float value) {
        return new Vector3F(x/value, y/value, z/value);
    }
    public Vector3F normalize(){
        return divideComponents(getMagnitude());
    }
    public Vector3F normalize(int length) {
        normalize();
        return multiplyComponents(length);
    }


    public float getMagnitudeSquared() {
        return x * x + y * y + z * z;
    }
    public float getMagnitude(){
        return (float)Math.sqrt(getMagnitudeSquared());
    }


    @Override
    public String toString() {
        return x + ", " + y + ", " + z;
    }


    public static float[] toArray(List<Vector3F> positions) {
        float[] result = new float[positions.size() * 3];
        int arrayIndex = 0;
        for (int listIndex = 0; listIndex < positions.size(); listIndex++) {
            result[arrayIndex] = positions.get(listIndex).x;
            arrayIndex++;
            result[arrayIndex] = positions.get(listIndex).y;
            arrayIndex++;
            result[arrayIndex] = positions.get(listIndex).z;
            arrayIndex++;
        }
        return result;
    }
}
