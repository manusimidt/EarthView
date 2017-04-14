package com.atlas.atlasEarth._VirtualGlobe.Source.Core;

import android.os.AsyncTask;
import android.util.Log;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Rendables.Renderable;


public class Camera {

    private Vector3F position = new Vector3F(0, 0, 5);
    private float pitch = 0;
    private float yaw = 0;
    private float distanceFromPlayer = 5;
    private float angleAroundPlayer = 0;
    private Renderable earthRenderable;

    public Camera(Renderable earthRenderable) {
        this.earthRenderable = earthRenderable;
    }
    public Camera(Vector3F position, float pitch, float yaw){
        this.position = position;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    /**
     * Zooming
     */
    public void increaseZoom(float time) {
        Log.d("debug", "Time delta: " + time);
        distanceFromPlayer += calculateZoomFactor(distanceFromPlayer) * time / 1000;
        checkZoomLevelForValidity();
        Log.i("WorldSpaceInfo", "Camera:\t Zoom: " + distanceFromPlayer);
    }
    public void decreaseZoom(float time) {
        Log.d("debug", "Time delta: " + time);
        distanceFromPlayer -= calculateZoomFactor(distanceFromPlayer) * time / 1000;
        checkZoomLevelForValidity();
        Log.i("WorldSpaceInfo", "Camera:\t Zoom: " + distanceFromPlayer);
    }

    private float calculateZoomFactor(float x) {
        Log.d("debug", "Current Camera Position: " + position.x);
        Log.d("debug", "CurrentFactor: " + ((float) (Math.pow(x - 1, Math.E) / 2)));
        return (float) (Math.pow(x - 1, Math.E) / 2);
    }
    private void checkZoomLevelForValidity() {
        if (distanceFromPlayer > 7) {
            distanceFromPlayer = 7;
        } else if (distanceFromPlayer < 1.0) {
            distanceFromPlayer = 1.2f;
        }
    }


    /**
     * Camera position Calculation (Satellite view)
     */
    public void increaseViewAngle(float value) {
        angleAroundPlayer += value;
        Log.i("WorldSpaceInfo", "Camera:\t ViewAngle: " + angleAroundPlayer);
    }
    public void increasePitch(float value) {
        value /= 2;
        if ((pitch += value) > 0) {
            pitch = 0;
        } else if ((pitch += value) < -180) {
            pitch = -180;
        } else {
            pitch += value;
        }
        Log.i("WorldSpaceInfo", "Camera:\t Pitch: " + pitch);
    }

    public void calculateCameraPosition() {
        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();
        position.y = earthRenderable.getPosition().y + verticalDistance;

        float fullrotationAngle = earthRenderable.getRotZ() + angleAroundPlayer;
        float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(fullrotationAngle)));
        float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(fullrotationAngle)));
        position.x = earthRenderable.getPosition().x - offsetX;
        position.z = earthRenderable.getPosition().z - offsetZ;


        yaw = 180 - (earthRenderable.getRotY() + angleAroundPlayer);
    }
    private float calculateHorizontalDistance() {
        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }
    private float calculateVerticalDistance() {
        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }


    /**
     * Getter & Setter
     */
    public Vector3F getPosition() {
        return position;
    }
    public float getPitch() {
        return pitch;
    }
    public float getYaw() {
        return yaw;
    }
    public float getDistanceFromPlayer(){
        return distanceFromPlayer;
    }


}
