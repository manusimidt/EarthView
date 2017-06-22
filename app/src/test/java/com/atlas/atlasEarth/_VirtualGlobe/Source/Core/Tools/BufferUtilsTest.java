package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Tools;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;

import org.junit.Test;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * Created by Jonas on 6/19/2017.
 */
public class BufferUtilsTest {

    @Test
    public void convertVector3FListToFloatBuffer() throws Exception {

        List<Vector3F> list = new ArrayList<>();

        list.add(new Vector3F(1,2,3));
        list.add(new Vector3F(4,5,6));
        list.add(new Vector3F(7,8,9));

        FloatBuffer one = BufferUtils.convertVector3FListToFloatBuffer(list);

        float[] array = new float[]{1,2,3,4,5,6,7,8,9};
        FloatBuffer two = BufferUtils.convertFloatArrayToFloatBuffer(array);

        assertEquals(one,two);


    }

}