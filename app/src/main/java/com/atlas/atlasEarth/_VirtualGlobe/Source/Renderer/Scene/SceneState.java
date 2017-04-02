package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Scene;

/**
 * Created by Jonas on 2/11/2017.
 */

public class SceneState {

    private float diffuseIntensity;
    private float specularIntensity;
    private float ambientIntensity;
    private float shininess;

    public SceneState() {
        diffuseIntensity = 0.65f;
        specularIntensity = 0.25f;
        ambientIntensity = 0.10f;
        shininess = 12;
    }

    public float getAmbientIntensity() {
        return ambientIntensity;
    }

    public void setAmbientIntensity(float ambientIntensity) {
        this.ambientIntensity = ambientIntensity;
    }

    public float getDiffuseIntensity() {
        return diffuseIntensity;
    }

    public void setDiffuseIntensity(float diffuseIntensity) {
        this.diffuseIntensity = diffuseIntensity;
    }

    public float getShininess() {
        return shininess;
    }

    public void setShininess(float shininess) {
        this.shininess = shininess;
    }

    public float getSpecularIntensity() {
        return specularIntensity;
    }

    public void setSpecularIntensity(float specularIntensity) {
        this.specularIntensity = specularIntensity;
    }
}
