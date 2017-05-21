package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES31;
import android.opengl.GLUtils;

import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Texture;


/**
 * Class for Loading Textures to OpenGL
 */

public class TextureLoaderGL3x {

    /**
     * @param context the Context in which the Method is called
     * @param texture the Texture which should be loaded to OpenGL
     */

    public static void loadTexture(Context context, Texture texture) {
        Bitmap bitmap;

        if(texture.getResourceID()!=0) {
            //if the Texture contains a resource id, take the Bitmap from it
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;   // No pre-scaling
              bitmap = BitmapFactory.decodeResource(context.getResources(), texture.getResourceID(), options);
        }else{
            //if the Texture contains a Bitmap take the Bitmap
            bitmap = texture.getBitmap();
        }

        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D, texture.getTextureID());
        // Set filtering
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_MIN_FILTER, GLES31.GL_NEAREST);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_MAG_FILTER, GLES31.GL_NEAREST);

        GLUtils.texImage2D(GLES31.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D, 0);
    }
}
