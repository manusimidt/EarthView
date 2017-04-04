package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Rendables;


import com.atlas.atlasEarth.R;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.cartesianCS.Tessellation.SubdivisionSphereTessellator;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Texture;


public class EarthModel extends GeographicGlobe {


    public static EarthModel getInstance() {
        return new EarthModel(90, 0, 180, 1);
    }



    private EarthModel(float rotX, float rotY, float rotZ, float scale) {
        super(new Vector3F(0, 0, 0), rotX, rotY, rotZ, scale);
        super.setTexture(configureDayTexture(R.drawable.texture1_5));
        super.setTexture(configureNightTexture(R.drawable.texture_night));

        mesh = SubdivisionSphereTessellator.compute(5);
    }

    /**
     * Setup
     */

    private Texture configureDayTexture(int path) {
        Texture texture = new Texture(path);
        texture.setReflectivity(0.05f);
        texture.setShineDamper(6f);
        return texture;
    }
    private Texture configureNightTexture(int path) {
        Texture texture = new Texture(path);
        texture.setReflectivity(0.05f);
        texture.setShineDamper(6f);
        return texture;
    }


    @Override
    public void increaseRotation(float dx, float dy, float dz) {
        if (super.getRotX() + dx > 360) {
            dx -= 360;
        }
        if (super.getRotY() + dy > 360) {
            dy -= 360;
        }
        if (super.getRotZ() + dz > 360) {
            dz -= 360;
        }
        if (super.getRotX() + dx < -360) {
            dx += 360;
        }
        if (super.getRotY() + dy < -360) {
            dy += 360;
        }
        if (super.getRotZ() + dz < -360) {
            dz += 360;
        }
        if (super.getRotX() + dz >= 180 && dx > 0) {
            dx = 0;
            super.setRotX(180);
        }
        if (super.getRotX() + dx < 0 && dx < 0) {
            dx = 0;
            super.setRotX(0);
        }
        super.increaseRotation(dx, dy, dz);
    }




}
