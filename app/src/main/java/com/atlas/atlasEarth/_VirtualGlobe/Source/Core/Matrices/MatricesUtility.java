package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Matrices;

import android.content.Context;
import android.renderscript.Matrix4f;

import com.atlas.atlasEarth._VirtualGlobe.EarthView;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Camera;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector4F;


public class MatricesUtility {

    public static Matrix4f createTransformationMatrix(Vector3F transformation, float rx, float ry, float rz, float scale) {

        Matrix4f transformationMatrix = new Matrix4f();
        transformationMatrix.translate(transformation.x, transformation.y, transformation.z);
        transformationMatrix.rotate(rx, 1, 0, 0);//+90
        transformationMatrix.rotate(ry, 0, 1, 0);
        transformationMatrix.rotate(rz, 0, 0, 1);//-90
        transformationMatrix.scale(scale, scale, scale);

        return transformationMatrix;
    }

    public static Matrix4f createProjectionMatrix(Context context) {

        final float FOV = 50;
        final float NEAR_PLANE = 0.1f;
        final float FAR_PLANE = 150;

        float aspectRatio = EarthView.getEarthViewWidth(context) / EarthView.getEarthViewHeight(context);
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        Matrix4f projectionMatrix = new Matrix4f();

        projectionMatrix.set(0, 0, x_scale);
        projectionMatrix.set(1, 1, y_scale);
        projectionMatrix.set(2, 2, -((FAR_PLANE + NEAR_PLANE) / frustum_length));
        projectionMatrix.set(2, 3, -1);
        projectionMatrix.set(3, 2, -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length));
        projectionMatrix.set(3, 3, 0);

        return projectionMatrix;
    }

    public static Matrix4f createViewMatrix(Camera camera) {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.loadIdentity();
        viewMatrix.rotate(camera.getPitch(), 1, 0, 0);
        viewMatrix.rotate(camera.getYaw(), 0, 1, 0);
        //viewMatrix.rotate((float)Math.toRadians(camera.getRoll()), 0, 0, 1);
        Vector3F negativeCameraPos = new Vector3F(-camera.getPosition().x, -camera.getPosition().y, -camera.getPosition().z);

        viewMatrix.translate(negativeCameraPos.x, negativeCameraPos.y, negativeCameraPos.z);
        return viewMatrix;
    }

    public static Vector4F transformVector(Vector4F vector, Matrix4f transformMatrix) {
        float x = transformMatrix.get(0, 0) * vector.x + transformMatrix.get(0, 1) * vector.y + transformMatrix.get(0, 2) * vector.z + transformMatrix.get(0, 3) * vector.w;
        float y = transformMatrix.get(1, 0) * vector.x + transformMatrix.get(1, 1) * vector.y + transformMatrix.get(1, 2) * vector.z + transformMatrix.get(1, 3) * vector.w;
        float z = transformMatrix.get(2, 0) * vector.x + transformMatrix.get(2, 1) * vector.y + transformMatrix.get(2, 2) * vector.z + transformMatrix.get(2, 3) * vector.w;
        float w = transformMatrix.get(3, 0) * vector.x + transformMatrix.get(3, 1) * vector.y + transformMatrix.get(3, 2) * vector.z + transformMatrix.get(3, 3) * vector.w;
        return new Vector4F(x, y, z, w);
    }

}
