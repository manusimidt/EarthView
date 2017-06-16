package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Camera;

import android.util.Log;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.geographicCS.Geographic2D;


public class Camera {

    private Vector3F position;
    private float pitch = 0;
    private float yaw = 0;
    private float distanceFromEarth = 5;
    private float angleAroundEarth = 0;



    public Camera() {
       //Set the default position of the Camera, (on the geocentric vector of the Geographic2D(0,0))
        position = new Vector3F(5,0,0);
    }


    /**
     * Zooming
     */
    public void increaseZoom(float time) {
        Log.d("debug", "Time delta: " + time);
        distanceFromEarth += calculateZoomFactor(distanceFromEarth) * time / 1000;
        checkZoomLevelForValidity();
        Log.i("WorldSpaceInfo", "Camera:\t Zoom: " + distanceFromEarth);
    }
    public void decreaseZoom(float time) {
        Log.d("debug", "Time delta: " + time);
        distanceFromEarth -= calculateZoomFactor(distanceFromEarth) * time / 1000;
        checkZoomLevelForValidity();
        Log.i("WorldSpaceInfo", "Camera:\t Zoom: " + distanceFromEarth);
    }
    public void increaseDistanceToEarth(float x){
        distanceFromEarth += x;
    }
    private float calculateZoomFactor(float x) {
        Log.d("debug", "Current Camera Position: " + position.x);
        Log.d("debug", "CurrentFactor: " + ((float) (Math.pow(x - 1, Math.E) / 2)));
        return (float) (Math.pow(x - 1, Math.E) / 2);
    }
    private void checkZoomLevelForValidity() {
        if (distanceFromEarth > 7) {
            distanceFromEarth = 7;
        } else if (distanceFromEarth < 1.0) {
            distanceFromEarth = 1.2f;
        }
    }




    /**
     * Camera position Calculation (Satellite view)
     */
    public void increaseViewAngle(float value) {
        angleAroundEarth += value;
        Log.i("WorldSpaceInfo", "Camera:\t ViewAngle: " + angleAroundEarth);
    }
    public void increasePitch(float value) {
        value /= 2;
//        if ((pitch += value) > 0) {
 //           pitch = 0;
 //       } else if ((pitch += value) < -180) {
 //           pitch = -180;
//        } else {
            pitch += value;
 //       }
        Log.i("WorldSpaceInfo", "Camera:\t Pitch: " + pitch);
        Log.i("WorldSpaceInfo", "Camera:\t Position: " + position.toString());
    }

    public void lookAt(Geographic2D geographic){
        pitch = (float) geographic.getLatitude();
        angleAroundEarth = (float) geographic.getLongitude();
    }


    public void calculateCameraPosition() {
        Log.d("debug","Position: " + position.toString()+ ", Pitch: " + pitch + ", Yaw: " + yaw + ", AngleAroundEarth: " +angleAroundEarth);
        //Calculate horizontal distance between eye and target in the front view
        float horizontalDistance = (float) (distanceFromEarth * Math.cos(Math.toRadians(pitch)));

        //Calculate vertical distance between eye and target in the front view
        float verticalDistance = (float)(distanceFromEarth * Math.sin(Math.toRadians(pitch)));

        //Y Coordinate is independent from the angleAroundEarth
        position.y = verticalDistance;

        /*
         * Calculate the final x coordinate in the view from above
         * Add ninety to synchronize the angleAroundEarth with the geographic Coordinate System,
         * so that angleAroundEarth = 0, pitch = 0 is corresponding to the Geographic(0,0)
         */
        position.x = (float) (horizontalDistance * Math.sin(Math.toRadians(angleAroundEarth+90)));
        //Calculate the final y coordinate in the view from above
        position.z = (float) (horizontalDistance * Math.cos(Math.toRadians(angleAroundEarth+90)));



        yaw = 360 - (angleAroundEarth+90);
    }




    /**
     * Getter & Setter
     */
    public Vector3F getPosition() {
        return position;
    }
    public void setPosition(Vector3F position) {
        this.position = position;
    }
    public void setPosition(float x, float y, float z){
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }
    public float getPitch() {
        return pitch;
    }
    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
    public float getYaw() {
        return yaw;
    }
    public void setYaw(float yaw) {
        this.yaw = yaw;
    }
    public float getDistanceFromEarth(){
        return distanceFromEarth;
    }
    public float getAngleAroundEarth() {
        return angleAroundEarth;
    }
}
