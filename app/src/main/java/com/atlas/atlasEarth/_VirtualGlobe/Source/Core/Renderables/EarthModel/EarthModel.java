package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables.EarthModel;


import com.atlas.atlasEarth.R;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Texture;


public class EarthModel extends GeographicGlobe {


    public EarthModel() {
        super(new Vector3F(0, 0, 0), 90,0,0, 1);
        super.setTexture(configureDayTexture(R.drawable.texture1_5));
        super.setTexture(configureNightTexture(R.drawable.texture_night));
    }

    /**
     * Setup
     */

    private Texture configureDayTexture(int path) {
        Texture texture = new Texture(path, ByteFlags.GL_TEXTURE_2D);
        return texture;
    }

    private Texture configureNightTexture(int path) {
        Texture texture = new Texture(path, ByteFlags.GL_TEXTURE_2D);
        return texture;
    }




}
