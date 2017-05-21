package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer;


import android.graphics.Bitmap;

import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.NamesGL3x.TextureNameGL3x;


/**
 * Class for a Texture for OpenGL, loaded by {@link com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.TextureLoaderGL3x}
 */

public class Texture {

    private int resourceID = 0;
    private int textureID;
    private float shineDamper = 1; //Stride in which the camera can be to catch the reflection vektor
    private float reflectivity = 0; //Length of the reflection vector
    private Bitmap bitmap = null;

    /**
     * Load a texture out of a Resource folder
     * @param resourceID id for the Resource data
     */
    public Texture(int resourceID) {
        this.resourceID = resourceID;
        textureID = new TextureNameGL3x().getTextureID();
    }

    /**
     * Load a {@link Bitmap} Object to OpenGL
     * @param bitmap the Bitmap which should be loaded to OpenGL
     */
    public Texture(Bitmap bitmap){
        this.bitmap = bitmap;
        textureID = new TextureNameGL3x().getTextureID();
    }

    // TODO: 5/16/2017 Load a Texture from the Server to OpenGL for Posts
    @Deprecated
    private Texture(String url){

    }

    public int getResourceID() {
        return resourceID;
    }
    public Bitmap getBitmap(){
        return bitmap;
    }

    public int getTextureID() {
        return textureID;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }


    public float getShineDamper() {
        return shineDamper;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }


}
