package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer;

import com.atlas.atlasEarth._VirtualGlobe.AdvancedEarthViewOptions;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * Class representing a point light source
 */

public class Sun {

    private Vector3F position;
    private Vector3F sunlightColor;
    private Vector3F sunNightShininessColor;

    private final float distanceToEarth = 100;
    private float angle = 0;
    private float tilt = -20;


    /**
     * Empty Constructor
     */

    public Sun() {
        this(AdvancedEarthViewOptions.sunlightColor);
    }


    /**
     * @param sunlightColor Color of the sunlight
     */

    private Sun(Vector3F sunlightColor) {
        this(sunlightColor, AdvancedEarthViewOptions.sunNightShininessColor);
    }


    /**
     * @param sunlightColor          Color of the sunlight
     * @param sunNightShininessColor Color of the light, reflected from the moon.
     *                               (Night light-color)
     */

    private Sun(Vector3F sunlightColor, Vector3F sunNightShininessColor) {
        this.position = new Vector3F(distanceToEarth, distanceToEarth, 0);
        this.sunlightColor = sunlightColor;
        this.sunNightShininessColor = sunNightShininessColor;
        calculateCoordinates();
    }

    public void increaseAngle(float angle) {
        this.angle += angle;
        calculateCoordinates();
    }

    public void increasePosition(float x, float y, float z) {
        position.x += x;
        position.y += y;
        position.z += z;
    }

    private void calculateCoordinates() {
        position.x = (float) (distanceToEarth * Math.cos(angle));
        position.z = (float) (distanceToEarth * Math.sin(angle));
        position.y = (float) (distanceToEarth * Math.sin(tilt));
    }

    float incrementPerHour = (float) (Math.PI * 2) / 24;
    float incrementPerSecond = (float) (Math.PI * 2) / 60;

    public void calculateAngleByTime() {
        Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        calendar.get(Calendar.HOUR_OF_DAY);

        angle = incrementPerSecond * calendar.get(Calendar.SECOND);
        //angle = (calendar.get(Calendar.MILLISECOND) * incrementPerSecond/1000);
        calculateCoordinates();
    }

    public void calculateAngle() {
        angle += 0.004;
        calculateCoordinates();
    }


    public Vector3F getSunlightColor() {
        return sunlightColor;
    }


    public Vector3F getPosition() {
        return position;
    }

    public void setPosition(Vector3F position) {
        this.position = position;
    }

    public Vector3F getSunNightShininessColor() {
        return sunNightShininessColor;
    }
}
