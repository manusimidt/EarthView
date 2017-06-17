package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Matrices;

import android.opengl.Matrix;
import android.renderscript.Matrix4f;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Camera.Camera;

import org.junit.Test;

import static org.junit.Assert.assertEquals;



public class MatricesUtilityTest {

    @Test
    public void lookAt() throws Exception {
        //Setting up the three Vectors
        Vector3F eye = new Vector3F(0,0,1);
        Vector3F target = new Vector3F(0,0,0);
        Vector3F up = new Vector3F(0,1,0);

        //Create the expected Matrix
        Camera camera = new Camera(/*new Vector3F(0,1,0)*/);
        //camera.setPosition(new Vector3F(0,0,1));
        Matrix4f expected = MatricesUtility.createViewMatrix(camera);
//
        float[] outputArray = new float[16];
         Matrix.setLookAtM(outputArray,0,eye.x,eye.y,eye.z, target.x,target.y,target.z, up.x,up.y,up.z);
        Matrix4f output = new Matrix4f(outputArray);

float delta = 0.1f;

        assertEquals(expected.get(0,0), output.get(0,0), delta);
        assertEquals(expected.get(0,1), output.get(0,1), delta);
        assertEquals(expected.get(0,2), output.get(0,2), delta);
        assertEquals(expected.get(0,3), output.get(0,3), delta);

        assertEquals(expected.get(1,0), output.get(1,0), delta);
        assertEquals(expected.get(1,1), output.get(1,1), delta);
        assertEquals(expected.get(1,2), output.get(1,2), delta);
        assertEquals(expected.get(1,3), output.get(1,3), delta);

        assertEquals(expected.get(2,0), output.get(2,0), delta);
        assertEquals(expected.get(2,1), output.get(2,1), delta);
        assertEquals(expected.get(2,2), output.get(2,2), delta);
        assertEquals(expected.get(2,3), output.get(2,3), delta);

        assertEquals(expected.get(3,0), output.get(3,0), delta);
        assertEquals(expected.get(3,1), output.get(3,1), delta);
        assertEquals(expected.get(3,2), output.get(3,2), delta);
        assertEquals(expected.get(3,3), output.get(3,3), delta);
    }

    @Test
    public void createViewMatrix() throws Exception {

    }

}