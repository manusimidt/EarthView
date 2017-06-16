package com.atlas.atlasEarth._VirtualGlobe;


/**
 * Static options for the EarthView
 */

public class EarthViewOptions {

    /**
     * Full Lightning Option for Earth Model
     */
    private static boolean fullLightning = true;
    /**
     * FOV (Field of View) variable of for calculating the frustum.
     * Representing a Camera Lens, usually varies between 30°(quite zoomed in) to 90°(extra wide)
     */
    private static float FoV = 50;

    public static void disableFullLightning(){
        fullLightning = false;
    }

    public static void enableFullLightning(){
        fullLightning = true;
    }

    public static boolean isFullLightning() {
        return fullLightning;
    }

    public static float getFieldOfView() {
        return FoV;
    }

    public static void setFieldOfView(float foV) {
        FoV = foV;
    }
}
