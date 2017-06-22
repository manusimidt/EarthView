package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES31;
import android.opengl.GLUtils;

import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.CubeMapTexture;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Texture;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


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
        // TODO: 6/19/2017 glActiveTexture ?

        if (texture instanceof CubeMapTexture) {
            GLES31.glActiveTexture(GLES31.GL_TEXTURE0);
            Bitmap[] bitmaps = ((CubeMapTexture) texture).getBitmaps(context);
            GLES31.glBindTexture(GLES31.GL_TEXTURE_CUBE_MAP, texture.getTextureIDGl3x());

            for (int i = 0; i < bitmaps.length; i++) {
                GLUtils.texImage2D(GLES31.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GLES31.GL_RGBA, bitmaps[i], 0);
            }
            GLES31.glTexParameteri(GLES31.GL_TEXTURE_CUBE_MAP, GLES31.GL_TEXTURE_MIN_FILTER, GLES31.GL_LINEAR);
            GLES31.glTexParameteri(GLES31.GL_TEXTURE_CUBE_MAP, GLES31.GL_TEXTURE_MAG_FILTER, GLES31.GL_LINEAR);

            GLES31.glBindTexture(GLES31.GL_TEXTURE_CUBE_MAP, 0);

            ((CubeMapTexture) texture).recycle();

        } else {

            if (texture.getResourceID() != 0) {
                //if the Texture contains a resource id, take the Bitmap from it
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inScaled = false;   // No pre-scaling
                bitmap = BitmapFactory.decodeResource(context.getResources(), texture.getResourceID(), options);

            } else if (!texture.getUrl().isEmpty()) {
                try {
                    URL url = new URL(texture.getUrl());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(input);
                } catch (IOException e) {
                    e.printStackTrace();
                    bitmap = texture.getBitmap();
                }

            } else {
                //if the Texture contains a Bitmap take the Bitmap
                bitmap = texture.getBitmap();
            }

            GLES31.glBindTexture(GLES31.GL_TEXTURE_2D, texture.getTextureIDGl3x());

            // Set filtering
            GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_MIN_FILTER, GLES31.GL_LINEAR);
            GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_MAG_FILTER, GLES31.GL_LINEAR);

            GLUtils.texImage2D(GLES31.GL_TEXTURE_2D, 0, bitmap, 0);
            bitmap.recycle();
            GLES31.glBindTexture(GLES31.GL_TEXTURE_2D, 0);

        }
    }
}
