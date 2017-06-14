package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer;


import android.graphics.Bitmap;

import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.NamesGL3x.TextureNameGL3x;


/**
 * Class for a Texture for OpenGL, loaded by {@link com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.TextureLoaderGL3x}
 */

public class Texture extends TextureNameGL3x{

    private int resourceID = 0;
    private String url = "";
    private float shineDamper = 1; //Stride in which the camera can be to catch the reflection vektor
    private float reflectivity = 0; //Length of the reflection vector
    private Bitmap bitmap = null;


    /**
     * Load a texture out of a Resource folder
     * @param resourceID id for the Resource data
     */
    public Texture(int resourceID) {
        this.resourceID = resourceID;
    }

    /**
     * Load a {@link Bitmap} Object to OpenGL
     * @param bitmap the Bitmap which should be loaded to OpenGL
     */
    public Texture(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    /**
     * @param url the url of the Bitmap which should be loaded to OpenGL
     */
    public Texture(String url){
        this.url = url;
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

    public int getTextureID() {
        return super.getTextureID();
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
