package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Camera;

import android.renderscript.Matrix4f;
import android.util.Log;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.geographicCS.Geographic2D;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Matrices.MatricesUtility;


/**
 * Class for making the ViewMatrix visual. The calculations assume that the Earth will be rendered in the Origin
 */
public class Camera {

    private static final String TAG = "Camera";

    private Vector3F position = new Vector3F(5, 0, 0);
    private float pitch = 0;
    private float pan = 0;
    private float yaw = 0;
    private float distanceFromEarth = 5;
    private float viewAngle = 0;
    private boolean dirty = true;
    private Matrix4f viewMatrix;
    private CameraChangeListener cameraChangeListener;


    public Camera() {
        //Set the default position of the Camera, (on the geocentric vector of the Geographic2D(0,0))
        viewMatrix = new Matrix4f();
    }


    /**
     * Zooming
     */
    public void increaseZoom(float time) {
        distanceFromEarth += calculateZoomFactor(distanceFromEarth) * time / 1000;
        checkZoomLevelForValidity();
    }

    public void decreaseZoom(float time) {
        distanceFromEarth -= calculateZoomFactor(distanceFromEarth) * time / 1000;
        checkZoomLevelForValidity();
    }

    public void increaseDistanceToEarth(float x) {
        dirty = true;
        distanceFromEarth += x;
    }

    private float calculateZoomFactor(float x) {
        return (float) (Math.pow(x - 1, 1.5) / 2);
    }

    private void checkZoomLevelForValidity() {
        dirty = true;
        if (distanceFromEarth > 7) {
            distanceFromEarth = 7;
        } else if (distanceFromEarth < 9.695723E-4) {
            distanceFromEarth = 0.01f;
        }
    }


    /**
     * Camera position Calculation (Satellite view)
     * ViewAngle increment.
     *
     * @param value View Angle is corresponding to Longitude
     */
    public void increaseViewAngle(float value) {
        dirty = true;
        viewAngle += value;
    }

    /**
     * Pitch increment
     *
     * @param value Pitch is corresponding to Longitude
     */
    public void increasePitch(float value) {
        dirty = true;
        value /= 2;
        pitch += value;
    }

    public void increasePan(float value){
        if(pan+value>30){
            pan = 30;
            return;
        }
        if(pan-value<-30){
            pan = -30;
            return;
        }
        pan += value;
    }

    public void lookAt(Geographic2D geographic) {
        dirty = true;
        pitch = (float) geographic.getLatitude();
        viewAngle = (float) geographic.getLongitude();
    }


    public void calculateCameraPosition() {

        if (dirty) {
            //Calculate horizontal distance between eye and target in the front view
            float horizontalDistance = (float) (distanceFromEarth * Math.cos(Math.toRadians(pitch)));

            //Calculate vertical distance between eye and target in the front view
            float verticalDistance = (float) (distanceFromEarth * Math.sin(Math.toRadians(pitch)));

            //Y Coordinate is independent from the viewAngle
            position.y = verticalDistance;

            /*
             * Calculate the final x coordinate in the view from above
             * Add ninety to synchronize the viewAngle with the geographic Coordinate System,
             * so that viewAngle = 0, pitch = 0 is corresponding to the Geographic(0,0)
             */
            position.x = (float) (horizontalDistance * Math.sin(Math.toRadians(viewAngle + 90)));
            //Calculate the final y coordinate in the view from above
            position.z = (float) (horizontalDistance * Math.cos(Math.toRadians(viewAngle + 90)));

            //Calculate yaw (CCW Angle to X-Axis)
            yaw = 360 - (viewAngle + 90);

            //Calculate the Matrix
            viewMatrix = MatricesUtility.createViewMatrix(this);

            dirty = false;

            //For debugging print logTags!
            Log.i(TAG, "---------------- Updated camera position. Current variables: ----------------");
            Log.i(TAG, "Camera position: \t\t" + position.toString());
            Log.i(TAG, "Input Variables");
            Log.i(TAG, "Camera Pitch (φ): \t" + pitch);
            Log.i(TAG, "ViewAngle (λ): \t\t" + viewAngle);
            Log.i(TAG, "Distance from Earth: \t" + distanceFromEarth);
            Log.i(TAG, "Computed Variables");
            Log.i(TAG, "Camera Yaw :\t\t\t" + yaw);

            if(cameraChangeListener !=null){
                cameraChangeListener.onPositionChanged(position);
            }
        }
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
    public float getPan() {
        return pan;
    }

    public float getViewAngle() {
        return viewAngle;
    }

    public float getYaw() {
        return yaw;
    }

    public float getDistanceFromEarth() {
        return distanceFromEarth;
    }

    public Matrix4f getViewMatrix() {
        if(dirty){
            MatricesUtility.createViewMatrix(this);
        }
        return viewMatrix;
    }

    public void setOnCameraChangeListener(CameraChangeListener listener){
        cameraChangeListener = listener;
    }
    public interface CameraChangeListener {
         void onPositionChanged(Vector3F position);
    }


}
