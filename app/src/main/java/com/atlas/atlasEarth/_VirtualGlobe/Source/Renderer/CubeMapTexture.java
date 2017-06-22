package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags;


/**
 * Created by Jonas on 6/19/2017.
 */

public class CubeMapTexture extends Texture {


    private int[] resourceIDs;
    private Bitmap[] bitmaps;

    /**
     * @param resourceIDs Resource ID's of the Bitmaps. Must be in the following order!:
     *                    right (x+), left (x-), top (y+), bottom (y-), back (z+), front(z-)
     */
    public CubeMapTexture(int[] resourceIDs) {
        super(ByteFlags.NULL, ByteFlags.GL_TEXTURE_CUBE_MAP);
        this.resourceIDs = resourceIDs;
    }


    public Bitmap[] getBitmaps(Context context) {
        if (bitmaps == null) {
            bitmaps = new Bitmap[resourceIDs.length];

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;   // No pre-scaling

            for (int i = 0; i < resourceIDs.length; i++) {
                bitmaps[i] = BitmapFactory.decodeResource(context.getResources(), resourceIDs[i], options);
            }
        }
        return bitmaps;
    }
    public void recycle(){
        for(Bitmap bitmap : bitmaps){
            bitmap.recycle();
        }
    }

}
