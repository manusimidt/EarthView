package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer;


import android.graphics.Bitmap;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.NamesGL3x.TextureNameGL3x;


/**
 * Class for a Texture for OpenGL, loaded by {@link com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.TextureLoaderGL3x}
 */

public class Texture extends TextureNameGL3x{

    // TODO: 6/19/2017 ShineDamper and Reflectivity?
    private int resourceID = 0;
    private String url = "";
    private Bitmap bitmap = null;
    private byte type = ByteFlags.NULL;


    /**
     * Load a texture out of a Resource folder
     * @param resourceID id for the Resource data
     */
    public Texture(int resourceID, byte type) {
        this.resourceID = resourceID;
        this.type = type;
    }

    /**
     * Load a {@link Bitmap} Object to OpenGL
     * @param bitmap the Bitmap which should be loaded to OpenGL
     */
    public Texture(Bitmap bitmap){
        this.bitmap = bitmap;
        this.type = ByteFlags.GL_TEXTURE_2D;
    }

    /**
     * @param url the url of the Bitmap which should be loaded to OpenGL
     */
    public Texture(String url){
        this.url = url;
        this.type = ByteFlags.GL_TEXTURE_2D;
    }

    public int getResourceID() {
        return resourceID;
    }

    public String getUrl() {
        return url;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public byte getType() {
        return type;
    }

    public int getTextureIDGl3x() {
        return super.getTextureIDGl3x();
    }



}
