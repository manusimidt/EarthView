package com.atlas.atlasEarth._VirtualGlobe.Source.Core.TouchHandeling;


import android.content.Context;
import android.renderscript.Matrix4f;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Camera.Camera;

public class TouchHandler {

    private Vector3F currentRay;
    private Matrix4f inverseProjectionMatrix;
    private Matrix4f inverseViewMatrix;
    private Camera camera;
    private Context context;
    private GLTouchEvent staticTouchPoint;
    private GLTouchEvent dynamicTouchPoint;



    public TouchHandler(Matrix4f projectionMatrix, Camera camera, Context context) {
        this.inverseProjectionMatrix = projectionMatrix;
        this.inverseProjectionMatrix.inverse();
        inverseViewMatrix = camera.getViewMatrix();
        inverseViewMatrix.inverse();
        this.camera = camera;
        this.context = context;
        staticTouchPoint = new GLTouchEvent(inverseProjectionMatrix, inverseViewMatrix, context);
    }

    public void setUp(float touchX, float touchY, Camera camera){
        inverseViewMatrix = camera.getViewMatrix();
        inverseViewMatrix.inverse();
        staticTouchPoint.updateTouchEvent(inverseViewMatrix, touchX, touchY);
        dynamicTouchPoint = GLTouchEvent.copyEvent(context, staticTouchPoint);
    }

    public void update(float touchX, float touchY, Camera camera) {
        inverseViewMatrix = camera.getViewMatrix();
        inverseViewMatrix.inverse();
        dynamicTouchPoint.updateTouchEvent(inverseViewMatrix, touchX, touchY);
        calculateCameraRotation();
    }
    public void reset(){

    }

    private void calculateCameraRotation(){
        float rotX = (float)Math.atan(calculateDeltaXInEyeCoords()/camera.getDistanceFromEarth());
        float rotY = (float)Math.atan(calculateDeltaYInEyeCoords()/camera.getDistanceFromEarth());
        camera.increaseViewAngle(rotX*4);
        camera.increasePitch(rotY*2);
    }



    private float calculateDeltaXInEyeCoords(){
        return staticTouchPoint.getEyeCoords().x - dynamicTouchPoint.getEyeCoords().x;
    }
    private float calculateDeltaYInEyeCoords(){
        return staticTouchPoint.getEyeCoords().y - dynamicTouchPoint.getEyeCoords().y;
    }

}
