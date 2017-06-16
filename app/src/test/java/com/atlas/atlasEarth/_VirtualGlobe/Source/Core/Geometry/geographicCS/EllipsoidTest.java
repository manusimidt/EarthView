package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.geographicCS;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3D;

import org.junit.Assert;
import org.junit.Test;


/**
 * Created by Jonas on 6/15/2017.
 */
public class EllipsoidTest {

    @Test
    public void toVector3D() throws Exception {
        Ellipsoid ellipsoid = Ellipsoid.ScaledWgs84;
        Vector3D actual = ellipsoid.convertGeodeticToCartesian(new Geodetic3D(1.2486420180870339, 0.66158056951490851, 0));
        Vector3D expected = new Vector3D(0.250129128527503, 0.749378700170899, 0.611024374623194);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void toVector3D1() throws Exception {

    }

    @Test
    public void getGeodeticSurfaceNormal() throws Exception {
        Ellipsoid ellipsoid = Ellipsoid.ScaledWgs84;
        Vector3D actual = ellipsoid.getGeodeticSurfaceNormal(new Geodetic3D(1.2486420180870339, 0.66158056951490851, 0));
        Vector3D expected = new Vector3D(0.24981292131259511, 0.74843135368195979, 0.6143647232475643);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void getGeodeticSurfaceNormal1() throws Exception {
        Ellipsoid ellipsoid = Ellipsoid.ScaledWgs84;
        Vector3D actual = ellipsoid.getGeodeticSurfaceNormal(new Vector3D(0.317553098205724, 0.763824724488825, 0.560016241997427));
        Vector3D expected = new Vector3D(0.317215780015384, 0.763013357900821, 0.563191587804848);
        Assert.assertEquals(actual, expected);
    }

}