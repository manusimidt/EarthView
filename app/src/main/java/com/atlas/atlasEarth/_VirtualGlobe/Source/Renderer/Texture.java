package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer;


import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.NamesGL3x.TextureNameGL3x;

public class Texture {

    private int resourceID;
    private int textureID;
    private float shineDamper = 1; //Stride in which the camera can be to catch the reflection vektor
    private float reflectivity = 0; //Length of the reflection vector

    public Texture(int resourceID) {
        this.resourceID = resourceID;
        textureID = new TextureNameGL3x().getTextureID();
    }


    public int getResourceID() {
        return resourceID;
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
